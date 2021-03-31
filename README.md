1. 기본 화면 만들기(11:08~11:26)

- activity_main.xml 에서 TextView 제거(어차피 아래서 제거됨)
- canvas 만들기
  - android java canvas tutorial 를 검색해서 링크들을 확인
  - https://www.javatpoint.com/android-simple-graphics-example 를 보니 DemoView를 넣는 방법이 제일 간단해 보임
  - MainActivity.java 에 비슷하게 코딩. 내가 만드는건 Domo가 아니니까 MainView라고 하자.
  - java coding convention은 기억이 안나는데 개인코드니까 내맘대로 mHelloWorld 형태로 
  - 그나저나, 이렇게 되면 activity_main.xml이 아예 필요 없겠네;;

(점심식사)

2. 화면 색칠하기 (12:30~)

- 위 링크 예제를 보고 계속 진행중
- 에러 남;
```
> Task :app:compileDebugJavaWithJavac FAILED
/home/user/workspace/projects/tetris/app/src/main/java/com/ryuan/tetris/MainActivity.java:32: error: cannot find symbol
            paint.setSyle(Paint.Style.FILL);
                 ^
  symbol:   method setSyle(Style)
  location: variable paint of type Paint
/home/user/workspace/projects/tetris/app/src/main/java/com/ryuan/tetris/MainActivity.java:33: error: cannot find symbol
            paint.setColor(Color.BLUE);
                           ^
  symbol:   variable Color
  location: class MainActivity.MainView
2 errors
```
- google에서 android paint검색해서 https://developer.android.com/reference/android/graphics/Paint 확인
  - setStyle 있는데; --> setSyle t 가 빠졌네;
  - 아래줄은 new Paint() <- 세미콜론이 빠졌던거였네;
- 자동으로 완성이 안되길래 왜인지 몰랐는데 안드로이드 스튜디오를 오랜만에 써서 그런가 했는데, FILE > Power Saver Mode 때문이었; 끄고 나니 자동완성도 되고 에러도 나옴
