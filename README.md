> 예전에는 책을 구매 하기 전에 보통은 서점을 가서 책을 보고 구매를 진행 하였지만, 최근에는 도서관에 책을 희망 자료로 요청 하고 한달 정도 있다가 빌려서 본 후 구매를 진행한다. 
한달에 총 25권까지 신청 할 수 있는데, 이를 하나하나 손으로 등록하기엔 너무 많은 시간과 노력이 필요하다. 이에 도서 목록을 가지고 자동으로 등록하는 프로그램을 만든다.


<div markdown="0"><a href="https://github.com/lahuman/request-book-apply" class="btn btn-warning">Source 바로가기</a></div>

<div markdown="0"><a href="http://lahuman.jabsiri.co.kr/147" class="btn btn-warning">Selenide (UI 자동 테스트 툴) 설명</a></div>
   
## 프로그램 설계

부천 시립 도서관에서 한달에 요청 할 수 있는 도서는 다음과 같다.

* 상동, 심곡, 꿈빛, 책마루, 송내도서관 : 1인당 월 5권
* 원미, 북부, 한울빛, 꿈여울도서관 : 1인당 월 20권

이에 도서 목록을 다음과 같이 TXT 파일로 작성하연 자동으로 등록 하도록 한다.

#### 도서목록 파일 내용 샘플
{% highlight txt %}
도서관코드|도서명
AA|즐거운하루
AB|자동책등록프로그램
{% endhighlight %}

#### 도서관 코드

| 도서관코드 | 도서관명 | 비고 |
|:--------:|:-------:|:--------:|
| AA      | 상동   |  월5권   |
| AB      | 원미   | 월20권   |
| AC      | 삼곡   | 월5권  |
| AD      | 북부   | 월20권  |
| AE      | 꿈빛   | 월5권   |
| AF      | 책마루 | 월5권   |
| AG      | 한울빛 | 월20권  |
| AH      | 꿈여울 | 월20권   |
| AI      | 송내   | 월5권   |
| AK      | 도당   | 월20권  |


## 프로세스 순서도

![프로세스 순서도](https://lahuman.github.io/assets/project/bookapply/process_flowchart.png)


## Notice
* <a href="http://lahuman.jabsiri.co.kr/147">Selenide (UI 자동 테스트 툴)<a>에 대한 기초 지식 필요
* JRE 1.8 이상이 설치 되어 있어야 합니다.
* JRE_HOME 환경 변수가 설정 되어 있어야 합니다.
    * 다음이 path에 추가 되어야 함 
		* $JRE_HOME$\bin

## 실행 명령어

## License

희망 도서 자동 등록 프로그램은 open source 프로그램으로 MIT 라이선스를 따릅니다.

This Request book apply is free and open source software, distributed under the MIT License. So feel free to use this program on your project without linking back to me or including a disclaimer.
