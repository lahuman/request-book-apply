package io.github.lahuman.reqbookapply;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.lahuman.reqbookapply.vo.RequestBookInfo;

/**
 * Hello world!
 *
 */
public class App {
  static WebDriver driver;
  static Wait<WebDriver> wait;
  static JavascriptExecutor js = null;

  static String parentWindowHandler = null;
  static String subWindowHandler = null;

  public static void main(String[] args) {
    if (args == null || args.length > 2) {
      System.out.println("Run with ID/PW");
      System.exit(1);
    }

    System.setProperty("webdriver.gecko.driver", "/home/daniel/DEV/geckodriver");
    driver = new FirefoxDriver();
    wait = new WebDriverWait(driver, 30);
    js = (JavascriptExecutor) driver;

    new App().run(args);

    driver.close();
  }

  private void run(String[] args) {
    // // P1. LOGIN
    driver.get("https://www.bcl.go.kr/site/member/login?menuid=001009");
    parentWindowHandler = driver.getWindowHandle();

    driver.findElement(By.name("userid")).sendKeys(args[0]);
    driver.findElement(By.name("userpw")).sendKeys(args[1]);
    driver.findElement(By.className("loginBtn")).click();

    wait.until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver webDriver) {
        System.out.println("Login...");
        return webDriver.findElement(By.className("sub")) != null;// PASS 변경 진행 페이 지
      }
    });

    if (driver.findElement(By.className("sub")) != null) {
      System.out.println("Main 페이지 이동");
      js.executeScript("fSubmit()");
    }

    wait.until(new ExpectedCondition<Boolean>() {
      public Boolean apply(WebDriver webDriver) {
        System.out.println("Main Page");
        System.out.println(webDriver.findElement(By.className("login")).getText());
        return webDriver.findElement(By.className("login")).getText().equals("로그아웃");
      }
    });

    // P2. 도서 신청 목록 조회
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("booklist.txt").getFile());
    List<RequestBookInfo> requestBookList = new ArrayList();
    try (Scanner scanner = new Scanner(file)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if ("".equals(line) || line.split("\\|").length != 2)
          continue;

        requestBookList.add(new RequestBookInfo(line.split("\\|")[0], line.split("\\|")[1]));
      }
      scanner.close();
    } catch (IOException e) {
      e.printStackTrace();
    }


    // P4. 도서 신청 진행
    requestBookList.stream().filter(x -> !x.getLibraryCode().equals("도서관코드")).forEach(bookInfo -> {
      // P3. 도서 신청 페이지 이동
      driver.get(
          "http://search.bcl.go.kr/index.php?agent=web&mod=wdDataSearch&act=hopeDataRequestForm&host=www");

      new Select(driver.findElement(By.name("manageCode")))
          .selectByValue(bookInfo.getLibraryCode());
      driver.findElement(By.name("title")).sendKeys(bookInfo.getBookTitle().trim());


      js.executeScript("wjSearchBook1()");

      wait.until(new ExpectedCondition<Boolean>() {
        public Boolean apply(WebDriver webDriver) {
          System.out.println("도서 검색 Popup");
          Set<String> handles = webDriver.getWindowHandles(); // get all window handles
          Iterator<String> iterator = handles.iterator();
          while (iterator.hasNext()) {
            subWindowHandler = iterator.next();
          }
          webDriver.switchTo().window(subWindowHandler);
          return webDriver.findElement(By.className("sg_paging")) != null;
        }
      });

      driver.switchTo().window(subWindowHandler);
      if (driver.findElement(By.tagName("body")).findElements(By.cssSelector(".btn_basket"))
          .size() > 0) {
        System.out.println(driver.findElement(By.tagName("body")).findElements(By.cssSelector(".btn_basket")).get(0).findElement(By.tagName("a")));
        driver.findElement(By.tagName("body")).findElements(By.cssSelector(".btn_basket")).get(0).findElement(By.tagName("a"))
            .click();
        wait.until(new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver webDriver) {
            try {
              driver.switchTo().alert();
              return true;
            } catch (NoAlertPresentException e) {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e1) {
                e1.printStackTrace();
              }
              return false;
            }
          }
        });



        Alert alert = driver.switchTo().alert();
        System.out.println(alert.getText());
        alert.accept();
        driver.switchTo().window(parentWindowHandler);
        System.out.println("검색된 도서에서 1번 선택");
        wait.until(new ExpectedCondition<Boolean>() {
          public Boolean apply(WebDriver webDriver) {
            System.out.println("도서 선택 완료 : " + webDriver.findElement(By.name("price")).getAttribute("value"));
            webDriver.switchTo().window(parentWindowHandler);
            return !"".equals(webDriver.findElement(By.name("price")).getAttribute("value"));
          }
        });
        
        if(!"".equals(driver.findElement(By.name("price")).getAttribute("value"))){
          driver.findElement(By.id("agree")).click();
          js.executeScript("dataRequestForm.submit();");
          bookInfo.setApply(true);
        }
      }
      driver.switchTo().window(parentWindowHandler);
    });


    // P5. 결과 출력
    requestBookList.stream().forEach(System.out::println);


  }
}
