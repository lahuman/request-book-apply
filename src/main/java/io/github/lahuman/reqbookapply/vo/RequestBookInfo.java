package io.github.lahuman.reqbookapply.vo;

import com.sun.jna.Library;

public class RequestBookInfo {

  private String libraryCode;
  private String bookTitle;

  private boolean isApply = false;

  public RequestBookInfo(String libraryCode, String bookTitle) {
    this.libraryCode = libraryCode;
    this.bookTitle = bookTitle;
  }

  public boolean isApply() {
    return isApply;
  }

  public void setApply(boolean isApply) {
    this.isApply = isApply;
  }

  public String getLibraryCode() {
    return libraryCode;
  }

  public String getBookTitle() {
    return bookTitle;
  }

  @Override
  public String toString() {
    return "{IS APPLY:" + isApply + ", 'LIBRARY CODE':'" + libraryCode + "', 'BOOK TITLE':'"
        + bookTitle + "'}";
  }
}
