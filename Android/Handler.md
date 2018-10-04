# Handler
## Thread & Handler
### 1.1 Thread
하나의 프로세스 내에서 실행되는 작업의 단위이다.
멀티 테스킹: 하나의 OS에서 여러 개의 프로세스가 동시에 실행되는 환경
멀티 스레딩: 하나의 프로세스 내에서 다수의 스레드가 동시에 수행되는 환경

### 1.2 Handler
AsyncTask 부분에서 설명했듯이 안드로이드에서는메인 스레드를 통해서만 UI 변경이 가능하다.
핸들러는 메인 스레드에 접근하기 위한 수단이다.
다른 객체가 보낸 메시지를 수신, 처리하는 객체이다.


## Using Handler
기본 생성자를 통해 Handler를 생성하면, 생성되는 Handler는
해당 Handler를 호출한 스레드의 MessageQueue와 Looper에 자동 연결된다.

### 2.1 MessageQueue

![MessageQueue](https://camo.githubusercontent.com/5b50df09bcab586a035072f0202f55c62f4fd7cd/687474703a2f2f6366696c65372e75662e746973746f72792e636f6d2f696d6167652f32373044303534433537393730413541323342453834)

`android.os.Looper` : UI 스레드에 하나가 존재하며 서브 스레드와 연관된 메시지 발송자

`android.os.MessageQueue` : 서브 스레드에서 처리할 메시지들이 담긴 무제한 연결 리스트 (FIFO)

`android.os.Message` : 서브 스레드에서 실행하는 메시지

`android.os.Handler` : 큐에 메시지를 삽입, Looper 객체는 많은 Handler를 갖지만 모두 같은 큐에 삽입

### 2.2 Handler의 실행 방식
메시지 처리방식

```java
Handler.sendEmptyMessage(int what);
Message what(ID)를 사용할 경우 사용하는 메소드

Handler.sendMessage(Message msg);
Message what, arg1, obj 등 ID와 정보 등을 같이 사용하는 메소드
핸들러를 정의한다.
TeamViewerHandler.sendEmptyMessage(0); // 팀뷰어 설치 유무 검사 핸들러 실행

TeamViewerHandler.sendEmptyMessage(1); // 팀뷰어 설치 되었는지 확인하는 핸들러 루프 종료

Handler TeamViewerHandler = new Handler()
  {
      @Override
      public void handleMessage (Message msg)
      {
          switch (msg.what)
          {
              case 0:
                  String packageName = "com.teamviewer.quicksupport.market";
                  boolean isExist = getPackageList(packageName);

                  if (isExist == true)
                  {
                      OpenRemoteControl(packageName);
                  }

                  this.sendEmptyMessageDelayed(0, 1000);
                  // 메시지를 딜레이를 주어 반복적으로 실행 가능
                  break;
              case 1:
                  this.removeMessages(0);
                  // removeMessages를 통해 메시지 큐의 메시지를 모두 없앨 수 있다.
                  break;
          }
      }
  };
스레드를 생성한다.
Thread thread1 = new Thread(new Runnable() {
  public void run() {
      try {
          for (int i = 0; i < 20 && isRunning; i++) {
              Thread.sleep(1000);

              Message msg = handler.obtainMessage();
              handler.sendMessage(msg);
          }
      } catch (Exception ex) {
          Log.e("SampleThreadActivity", "Exception in processing message.", ex);
      }
  }
});
스레드를 실행한다. (UI를 핸들러 내에서 처리, 스레드에서 핸들러 메시지를 처리)
Runnable 객체 구현방식

Runnable 인터페이스를 implements하여 Handler를 구현한다.
public class ProgressRunnable implements Runnable {

public void run() {

    bar.incrementProgressBy(5);

    if (bar.getProgress() == bar.getMax()) {
       textView01.setText("Runnable Done");
    } else {
       textView01.setText("Runnable Working ..." + bar.getProgress());
    }
  }
}
스레드를 구현한다.
Thread thread1 = new Thread(new Runnable() {
public void run() {
    try {
       for (int i = 0; i < 20 && isRunning; i++) {
          Thread.sleep(1000);

          handler.post(runnable);
       }
    } catch (Exception ex) {
       Log.e("SampleThreadActivity", "Exception in processing message.", ex);
    }
 }
 });
```
똑같이 스레드를 start()로 실행시키면 run()이 호출되고 핸들러 객체의 post()에서 Runnable의 run()이 수행된다.

3. 응용 예제
Handler와 AsyncTask를 이용하여 순차적으로 APK 파일을 다운로드 하는 예제 구현

- [ ] [소스 코드 링크](https://github.com/LDYWO/TIL/blob/master/Android/Source%20Code/Handler.java)

특정 APK 파일이 있는지 여부를 패키지의 경로로 검사
없다면 APK 파일을 FTP 서버에서 가져오는 Task 수행
APK 파일이 설치되어 있는지 확인하는 Loop Handler를 수행
APK 파일 설치가 완료되면 다시 메소드를 재귀적으로 실행
추가적인 파일이 설치되어 있는지 검사
만약 없다면 3초 뒤에 추가 파일 다운로드를 수행하는 Handler 실행
전부 다 다운로드 된 상태라면 바로 다음 액티비티로 넘김
4. 참고
[스레드 통신](http://androidyongyong.tistory.com/6)
[안드로이드 Handler 사용법](http://itmining.tistory.com/16)
#android