# Ch24_Looper_Handler
## main Thread와 소통하기
백그라운드 스레드가 메인 스레드와 통신하기 위해선 백이 메인에게 줄 메시지를 메시지함에 보관해둬야하고 메인은 그 메시지함을 계속 확인한다.

### 메시지 큐
안드로이드에선 스레드가 사용하는 메시지 수신함

### 메시지 루프
메시지 큐를 사용하여 동작하는 스레드. 자신의 큐에서 새로운 메시지를 찾기 위해 반복해서 루프를 실행한다.

**메시지 루프는 하나의 스레드와 하나의 루퍼로 구성됨**

### Looper
스레드의 메시지 큐를 관리하는 객체
main스레드는 루퍼를 갖는 메시지 루프다. Main 스레드가 하는 모든 일은 그것의 루퍼에 의해 수행된다.

## 백그라운드 스레드 만들기

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
	thumbnailDownloader = ThumbnailDownloader()
	thumbnailDownloader?.start()
	thumbnailDownloader?.looper
}

override fun onDestroy() {
    super.onDestroy()
    thumbnailDownloader?.quit()
    Log.i(TAG, "Background thread destroyed")
}

class ThumbnailDownloader<T> : HandlerThread(TAG) {
    companion object {
        val TAG: String = ThumbnailDownloader::class.java.name
}

    fun queueThumbnail(target: T, url: String) {
        Log.i(TAG, "Got a URL: $url")
    }
}
```

**스레드 사용시 안전성 고려할 것 두가지**
1. ThumbnailDownloader의 start()를 호출하여 스레드로 시작시킨 후에 getLooper(0를 호출한다. 이것이 스레드가 준비되었는지 확인하는 방법이다.
getLooper()를 호출하기 전까지는 onLooperPrepared()가 호출되었다는 보장이 없다. 따라서 queueThumbnail()을 호출할 때 null Handler가 되어 실행이 중단될 가능성이 있다.

2. onDestroy() 메서드 내부에서 quit()를 호출하여 스레드를 중단시킨다.  중단시키지 않을 경우 HandlerThread가 죽지 않고 계속 살아있어 메모리 누수가 발생한다.


## 메시지와 메시지 핸들러
### 메시지 구조
메시지는 Message의 인스턴스며,  다음과 같은 필드를 갖는게 유용하다.
* **what** - 메시지를 나타내는 사용자 정의 정수값
* **obj** - 메시지와 함께 전달되는 사용자 지정 객체
* **target** - 메시지를 처리할 Handler


### 핸들러 구조
메시지를 사용해서 실제 작업을 하려면 제일 먼저 Handler의 인스턴스가 필요함
Handler는 Message를 처리하는 대상일 뿐만 아니라 Message를 생성하고 게시하는 인터페이스의 역할도 함.

Message는 **반드시 Looper로부터 게시되고 사용되어야 한다.** -> Looper는 Message 객체들의 메시지 수신함을 소유하기 때문임. 
따라서 Handler는 항상 자신의 동료인 Looper의 참조를 가진다.

* Handler는 정확히 하나의 Looper에 연결됨
* Message는 정확히 하나의 목표 Handler에 연결됨
* Looper는 큐 전체의 Message들을 갖는다.
* 다수의 Message들이 동일한 대상 Handler를 참조할 수 있다.
* 다수의 Handler들이 하나의 Looper에 연결될 수 있다.

### Handler 사용하기
* 일반적으로 Handler는 직접 설정 X
* 메시지는 `Handler.obtainMessage(...)`를 호출하여 생성하는 것이 좋다.
이때 다른 메시지 필드들을 이 메서드 인자로 전달하면 이 메서드가 호출된 Handler 객체를 대상 핸들러로 설정해준다.

* `Handler.obtainMessage(...)` 는 매번 새로운 Message 객체의 생성을 피하기 위해 공유되는 재활용 풀에서 Message 객체를 가져다 사용하므로 재생성 비용이 없음.

*  Message 객체를 얻으면 그 Message를 그것의 Handler에 전달하기 위해서 sendToTarget()를 호출한다. -> Handler는 그 Message를 Looper의 메시지 큐 제일 끝에 넣는다.


## AsyncTask vs Threads.
* AsyncTask - 짧은 시간에 처리되면서 많이 반복되지 않는 작업에 적합
	* Android 3.2부터 하나의 백그라운 스레드에서 모든 AsyncTask 인스턴스의 백그라운드 작업을 실행함. -> 오래 실행되는 AsyncTask 인스턴스는 다른 AsyncTask 인스턴스가 CPU 시간을 얻지 못하게 방해함







#android/실무에바로적용하는안드로이드