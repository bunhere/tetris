1. 기본 화면 만들기(11:08~11:26)

- activity_main.xml 에서 TextView 제거(어차피 아래서 제거됨)
- canvas 만들기
  - android java canvas tutorial 를 검색해서 링크들을 확인
  - https://www.javatpoint.com/android-simple-graphics-example 를 보니 DemoView를 넣는 방법이 제일 간단해 보임
  - MainActivity.java 에 비슷하게 코딩. 내가 만드는건 Domo가 아니니까 MainView라고 하자.
  - java coding convention은 기억이 안나는데 개인코드니까 내맘대로 mHelloWorld 형태로 
  - 그나저나, 이렇게 되면 activity_main.xml이 아예 필요 없겠네;;

(점심식사)

2. 화면 색칠하기 (12:30~12:45)

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

3. 테트리스 객체 만들어 화면에 뿌려보기 (12:45~13:24)

- 예제 사이트를 보니 간단한 그림 그리는 API 들 간단해서 바로 객체 만들기 시작
- 이름은 Block으로 정하자. 긴것부터 종류별로 담으려면 4*4 공간이 필요할 것 같음
- java array를 쓰는 법이 기억이 안나니까 java array를 검색 https://www.w3schools.com/java/java_arrays.asp 보고 참고
- 생각해보니 한개 저장하는데 4*4 2차원이 필요. 이런 애가 종류별로 있어야 하니까 차원 하나 더 증가
- 회전도 감안해야 하는데 회전하는 것도 차원을 높여 저장하는 방법과 그냥 회전해서 쓰는 방법이 있을듯
- 일단 회전해서 써보자. 회전 못하는 상황이 왔을때 고민이 필요할듯

- 길쭉한거부터 아래처럼 만들면 될것 같다.
0100
0100
0100
0100

0110
0100
0100
0000

0110
0010
0010
0000

0010
0110
0100
0000

0100
0110
0010
0000

0000
0110
0110
0000

0100
1110
0000
0000

- 배열로 옮겨보면
int [][][] mBlock = {{{0,1,0,0},{0,1,0,0}, ....
- 첫번쨰로 포기하고 싶어짐. 인덴테이션 이상한데 대충 내가 보기 좋게 노가다로 맞춤
- 뭐라도 찍어보게 흰색으로 첫번쨰 블럭만 화면 임의 위치에 찍어볼 예정
- 크기는 대충 20*20이 네모 하나가 되게 해보자.
- 사각형 찍는건 첫번째 예제 참고 
- 예제에 좌표가 4개인데 뭔지 몰르겠다. android canvas 검색해서 https://developer.android.com/reference/android/graphics/Canvas drawRect 확인
- left, top, right, bottom 이네
- 루프 두번 돌리는데 i, j 헷갈리니까 row, col 로 잡자.
- 0이면 그리지 말고, 1일떄만 그리게

4. 크기를 적당히 키우고 보드를 잡자.(13:24~13:41)
- 20 으로 하니까 너무 작음. (노트 9 기준)
- 화면을 고려하게 하면 더 좋겠지만, 일단 10배로 해도 될것 같음. 200은 너무 큼. 규격을 알면 좋겠는데 대충 50정도로 해야겠음
- 이제 보드로 사용할 영역을 고려해야 할듯. 한칸이 50이면 10개면 500 높이는 20개쯤 잡으면 1000
- 벌써 지겹다;;쿨럭

5. 블럭이 내려오게 해보자(14:06~14:25)
- 타이머 기능이 필요할것 같다. android timer canvas 를 검색해서 이것저것 확인 CountTimer는 내가 원하는게 아닌것 같음.
- https://stackoverflow.com/questions/27388569/how-to-make-the-android-canvas-re-draw-every-5-seconds/27388797 를 참고하기로

6. 더이상 안내려오게 해보자(14:43~15:14)
- 블럭을 멈추게 하려면 processNext 안에 로직들이 많이 들어가야 할것 같다.
- row가 20이었는데 BOARD_WIDTH, BOARD_HEIGHT 에 20을 곱해놔서 안곱한걸로 바꾸고 그보다 크면 더 안커지게 바꾸자.
- 생각해보니, 이 방법에 큰 문제가 있다. 첫번째는 현재 블럭(4*4)가 내려와서 멈추는 것에 대한 문제가 있고 나중에 그 자리에 블럭이 있는건 어떻게 알게 할까?
- 한가지 방법은 보드크기만큼의 2차원 배열을 만들고 그 안을 채우는 방법이 있을 것 같다.
- 이 때, 우리가 정한 보드에 벽을 만들어서 1로 세팅해두는 방법이 있다. 아니 2로 세팅하자. 2는 벽, 1은 블럭
- 동적 크기 2차원 배열은 어떻게 만들지?
- 대충 짰는데 한칸 더 내려가는 버전이 나왔다.
- 수정. 또 사소한 실수;
