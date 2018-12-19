# Ch26_Background_Service
## IntentService
서비스의 인텐트 - 커맨드
IntentService는 queue의 커맨드들을 서비스함.
서비스는 액티비티처럼 인텐트에 응답하므로 반드시 AndroidManifest.xml에 선언되어야 한다.


## AlarmManager를 사용한 지연 실행
서비스를 백그라운드에서 제대로 사용하려면 실행 중인 액티비티들이 전혀 없을 때도 가능하도록 하는 방법이 필요하다.

Handler 를 통해서 Handler.sendMessageDelayed(), Handler.postDelayed()를 호출해서 해결 할 수 있지만 사용자가 액티비티를 벗어나면 실패한다. -> 프로세스가  셧다운되면 Handler 메세지도 덩달아 없어지기 때문이다.

**AlarmManager**는 인텐트를 전달할 수 있는 시스템 서비스다.

### PendingIntent 구성
* 	 PendingIntent.getService()를 호출함. (내부적으로 Context.startService(Intent)를 실행함.
* 전달 인자 - 인텐트를 전달할 Context, 이 PendingIntent를 구별하기 위한 Request Code, 전달할 Intent 객체, 플래그(PendingIntent 생성방법)


### 시간 기준 옵션
AlarmManager.ELAPSED_REALTIME, AlarmManager.RTC

AlarmManager.ELAPSED_REALTIME은 마지막으로 장치를 부팅한 이래로 경과된 시간을 시간 간격 산출의 기준으로 사용한다. -> 안드로이드 문서에선 가능하면 RTC 대신  ELAPSED_REALTIME을 권장한다.

AlarmManager.RTC는 UTC 개념의 ‘벽시계 시간’을 사용한다. 로케일을 배려하지 않기 때문에 사용자가 직접 로케일 처리를 구현해야 하는게 단점이다.

장치를 깨워야 한다면 AlarmManager.RTC_WAKEUP, AlarmManager.ELAPSED_REALTIME_WAKEUP을 사용하면 된다. 하지만 이것은 배터리를 소모하므로 가급적 사용을 권하지 않는다.

### PendingIntent
토큰 객체
**PendingIntent.getService()**를 호출하면
“나는 이 인텐트를 startService(Intent)에 전달하고 싶으니 기억해주세요” 와 같은 의미다.
나중에 PendingIntent 토큰의 send()를 호출하면 우리가 요청했던 것과 똑같은 방법으로 래핑된 인텐트를 안드로이드 운영체제가 우리에게 전달한다.

**같은 인텐트를 갖는 PendingIntent**
같은 인텐트를 갖는 PendingIntent를 두번 요청하면 동일한 PendingIntent 객체를 얻을 수 있다. 이것을 사용해서 PendingIntent 객체가 이미 존재하는지 여부를 검사하거나 이전에 요청했던 PendingIntent를 취소할 수 있다.
PendingIntent.FLAG_NO_CREATE 플래그를 전달하면 PendingIntent가 존재하지 않는다면 그것을 생성하지 않고 대신 NULL을 반환하라는 것이 해당 플래그의 의미다.


## 통지(알림)
### Notification
* 티커 텍스트 (ticker text) - 안드로이드 5.0 롤리팝 이전 버전이 실행되는 장치에서 통지가 최초로 보여질 때 상태 바에 나타난다. (롤리팝부터는 티커 텍스트가 상태 바에 나타나지 않는다.
* 아이콘 - 상태 바에 나타난다
* 뷰  -  통지를 통지함에 보여준다.
* PendingIntent - 사용자가 통지함의 통지를 눌렀을 때 발생되는 인텐트다.


## 서비스
### 서비스가 하는 것과 하지 못하는 것
서비스는 액티비티처럼 생명주기 콜백 메서드들을 제공하는 애플리케이션 컴포넌트다. 그리고 그 콜백들은 Main Ui 스레드에서 실행된다. 
서비스는 어떤 코드도 백그라운드 스레드에서 실행하지 않는다.  -> IntentService를 사용하면 자동으로 관리해준다. 대부분의 중요한 서비스에서는 일종의 백그라운드 스레드를 필요로 하는데, IntentService는 알아서 자동으로 관리해준다.

### 서비스의 생명 주기
서비스는 `onStartServce(Intent)`로 시작되며, 그것의 생명주기는 매우 간단하다. 다음의 콜백 메서드 3가지가 있다.

* `onCreate()` : 서비스가 생성될때 호출된다.
* `onStartCommand(Intent,Int,Int)` : startService() 사용해서 컴포넌트가 서비스를 시작시킬 때마다  한 번씩 호출된다. 두 개의 정수 인자들은 플래그와 시작 ID다. 플래그는 이 인텐트를 다시 전달하려는 것인지 등을 나타내는데 사용된다. 시작ID는 onStartCommand(Intent, Int, Int)의 모든 호출마다 달라진다 .따라서 이 인텐트를 다른 것과 구분하기 위해 사용할 수 있다.
* `onDestroy()`: 서비스가 더 이상 살아있을 필요가 없을 때 호출된다. 서비스가 중단된 후에 이 상태가 된다.

## Non-sticky 서비스
IntentService는 non-sticky 서비스다. Non-sticky 서비스는 자신의 일이 끝나면 중단된다. 우리 서비스를 non-sticky로 만들려면, 우리 서비스 클래스에서 오버라이드한 onStartCommad() 메서드에서 START_NOT_STICKY 나 START_REDELIVER_INTENT 중 하나를 반환하면 된다.

서비스가 할일이 끝나면 stopSelf(), stopSelf(Int)를 통해 안드로이드 운영체제에 서비스가 끝났음을 알려줌.

**START_NOT_STICKY, START_REDELIVER_INTENT 반환의 차이**
	서비스가 일을 끝내기 전에 시스템이 셧다운될 필요가 있을 경우에 서비스가 동작하는 방법이 달라짐 
	1. START_NOT_STICKY 는 서비스가 죽고 없어짐
	2. START_REDELIVER_INTENT 서비스는 나중에 그 서비스를 다시 시작시키려고 시도함


## Sticky 서비스
서비스 외부에서 Context.stopService(Intent)를 호출하여 중단시키지 않는 한 sticky는 항상 시작된다. 우리 서비스를 sticky 서비스로 만들려면 START_STICKY를 반환화면 된다.

Sticky 서비스가 시작되면 컴포넌트에서 Context.stopService(Intent)를 호출할 때까지 해당 서비스는 계속 On 상태이다. 만일 서비스가 죽어야되면 null인텐트를 onStartCommand에 전달하여 다시 시작시키면 된다.


## Bound 서비스
`bindService(Intent, ServiceConnection, Int)` 메서드를 사용해서 서비스에 바인딩할 수 있다. 이렇게 하면 서비스의 메서드를 직접 호출할 수 있다. 여기서 인자로 전달되는 ServiceConnection은 서비스 바인딩을 나타내는 객체이며, 모든 바인딩 콜백 호출을 갖는다.

**바인딩에 필요한 두 개의 생명주기 콜백 메서드**
* `onBind(Intent)` - 서비스가 바인딩될 때마다 호출된다. `ServiceConnection.onServiceConnected(ComponentName, IBinder)`의 인자로 받은 IBinder 객체를 반환한다.
* `onUnbind(Intent)` - 서비스의 바인딩이 종료될 때 호출된다.


### 로컬 서비스 바인딩
```kotlin
private class MyBinder : IBinder {
	fun getService() : MyService {
		return MyService.this
	}
}

override fun onBind(intent : Intent) {
	return MyBinder()
}
```

하나의 안드로이드 컴포넌트가 다른 것과 직접 통신할 수 있게 해준다.
그러나 효율성이 떨어져 권장 X
서비스들은 싱글톤으로 생성되는데 이런 방식은 싱글톤의 장접을 살리지 못함

### 원격 서비스 바인딩
바인딩은 원격 서비스(remote service)의 경우에 더 유용하다 왜냐하면 다른 프로세스들의 애플리케이션에서 서비스의 메서드들을 호출 할 수 있기 때문.

안드로이드 문서에서 AIDL(Android Interface Description Language) 또는 Messenger 클래스에 자세히 나온다.

## JobScheduler와 JobServices
JobScheduler를 사용하면 특정 작업을 실행하다록 정의한 후 특정 상황에서만 실행되도록 할 수 있다.

**JobScheduler 사용 방법**
1. 작업을 처리할 서비스를 생성함 - 이 때 서비스를 JobService의 서브 클래스로 지정한다.

2. 메서드 오버라이드
	* onStartJob(JobParameters)
	* onStopJob(JobParameters)

3. 매니페스트에 등록
	* 	퍼미션과 exported를 지정해줘야 한다.
	* JobScheduler만이 서비스를 실행할 수 있도록 BIND_JOB_SERVICE 퍼미션을 지정한다.
```xml
<service
	android:name=".PollService"
	android:permission="android.permission.BIND_JOB_SERVICE"
	android:exported="true" />
```


## Sync Adapter (동기 어댑터)
동기 어댑터의 유일한 목적은 데이터를 데이터 소스와 동기화하는 것이다. (업로딩, 다운로딩 혹은 모두에서).
JobScheduler와는 다르게 동기 어댑터는 종전부터 계속 사용가능하다.

동기 어댑터는 별도로 플래그를 설정하지 않아도 여러 애플리케이션의 동기화가 함께 구성된다.

장치의 재부팅에 따른 동기화 알람을 다시 설정하지 않아도 된다. -> 알아서 동기 어댑터가 해줌

안드로이드 운영체제가 제공하는 사용자 인터페이스와 연계 가능

단점 : PendingIntent 관련 코드가 없으나 훨씬 코드가 많아진다.

1. 동기 어댑터는 어떤 웹 요청도 수행하지 않는다.
2. 원격 서버의 계정을 나타내는 데이터와 계정 및 인증 클래스들을 래핑하기 위해 콘텐트 제공자(**Content Provider**)의 구현을 필요로 한다.( 서버에서 인증을 요구하지 않아도 구현해야됨). 
3. 또한 동기 어댑터와 동기 서비스의 구현도 필요하다

**동기 어댑터를 사용할 때** 
앱에서 데이트 계층으로 이미 콘텐트 제공자를 사용하고 있고 계정 인증일 필요로 할 때





#android/실무에바로적용하는안드로이드