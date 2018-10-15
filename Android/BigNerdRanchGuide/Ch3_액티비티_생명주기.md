# Ch3_액티비티_생명주기
액티비티의 모든 인스턴스는 생명주기를 갖는다. 그리고 생명주기 동안에 액티비티는 세 가지 상태 running, paused, stopped 로 상호 전환된다.

![](Ch3_%E1%84%8B%E1%85%A2%E1%86%A8%E1%84%90%E1%85%B5%E1%84%87%E1%85%B5%E1%84%90%E1%85%B5_%E1%84%89%E1%85%A2%E1%86%BC%E1%84%86%E1%85%A7%E1%86%BC%E1%84%8C%E1%85%AE%E1%84%80%E1%85%B5/B7CE683B-C29E-4EB9-B517-57D7E8ACC44B.png)
![activity life cycle](http://img1.daumcdn.net/thumb/R1920x0/?fname=http%3A%2F%2Fcfile30.uf.tistory.com%2Fimage%2F2139F134566EBC6B087D62)

###  onCreate() 
액티비티 인스턴스가 생성되고 화면에 나타나기 전에 안드로이드 런타임이 이 메서드를 
호출함
- 위젯을 인플레이트하여 뷰 객체로 생성한 후 화면에 보여준다 (setContentView(int)에서 호출)
- 인플레이트된 위젯의 객체 참조를 얻는다
- 사용자와의 상호 동작을 처리하기 위해 위젯에 리스너를 설정한다.
- 외부의 모델 데이터를 연결한다.

## 액티비티 생명주기 로깅하기
### 로그 메시지
`public static int d(String tag, String msg)`
 d는 디버그를 의미하며, 로그 메시지의 레벨을 나타낸다.
첫번째 매개변수는 메시지의 소스
두번째 매개변수는 메시지의 내용 나타냄

###  onCreate()의 super class
 onCreate() 안에서 super 클래스의 구현 메서드를 호출하는 메소드는 맨 앞에 있어야 한다. 다른 메서드는 호출 순서가 중요하지 않음


## 장치 회전과 액티비티 생명주기 
장치를 회전시킬 경우엔 액티비티가 파괴되었다가 다시 생성된다.
onDestroy() -> onCreate() -> onStart() -> onResume()

### 가로 방향 레이아웃 생성
Res 디렉터리에 ( Orientation - landscape ) qualifier를 적용한 디렉터리를 생성한다
그러면 qualifier가 적용되어 이름 뒤에 -land 접미사가 붙는다.
다른 구성 수식자들은 링크에서 확인 가능
[Qualifier](http://developer.android.com/guide/topics/resources/providing-resources.html)


액티비티 화면 전환시 레이아웃을 결정하는 리소스 파일을 변경해야 하므로 안드로이드는 액티비티를 리소르를 변경해서 다시 생성한다.

## 장치 회전 시 데이터 저장하기
회전시 액티비티가 소멸되고 재생성되므로 기존의 데이터를 저장할 필요가 있다.
그러기 위해선 `onSaveInstanceState(Bundle bundle)`를 오버라이드 해야 한다. 

`onSaveInstanceState(Bundle bundle)`는 onPause()와  onStop() , onDestroy()가 호출되기 전에 시스템에서 호출된다.
그러므로 저장할 정보가 있으면 여기서 번들에 넣어주면 된다.

```java
@Override
public void onSaveInstanceState(Bundle savedInstanceState) {
	super.onSaveInstanceState(savedInstanceState);
	Logi(TAG, "onSaveInstanceState");

	// 저장할 데이터 저장
	savedInstace.putInt(KEY_INDX, mCurrentIndex);
}

@Override
protected void onCreate(Bundle savedInstanceState) {
	...
	// 저장한 데이터가 있을 경우 불러옴
	if (savedInstanceState != null) {
		mCurretIndex = savedInstanceState.getInt(KEY_INDEX, 0);
	}
}
```

## 액티비티 생명주기 다시 알아보기
사용자가 일정 시간 동안 장치를 사용하지 않거나 안드로이드가 메모리를 회수해야 할 경우 액티비티가 소멸된다. 이때 액티비티가 실행 중이면 소멸되지 않고 paused나 stopped일때만 소멸된다. 소멸 될 경우 오버라이드한 `onSaveInstanceState()`가  호출된다 -> 액티비티가 보존됨 (Stashed)

액티비티가 보존되면 액티비티 객체는 존재하지 않는다. 그러나 액티비티 레코드 객체는 안드로이드 운영체제에 살아있다. 필요하다면 안드로이드 운영체제는 그 액티비티 레코드를 사용해서 액티비티를 되살릴 수 있다.

액티비티가 보존 상태일 때는 onDestroy()가 호출되지 않을 수 있다. onPause()와 onSaveInstanceState()가 호출되는 것에 의존해서 코드를 작성하며 안된다.

액티비티 레코드는 사용자가 백버튼을 누르면 완전하게 소멸된다. 혹은 재부팅시, 아니면 오랫동안 애플리케이션을 사용하지 않아도 폐기된다.

## 로깅 레벨
![](Ch3_%E1%84%8B%E1%85%A2%E1%86%A8%E1%84%90%E1%85%B5%E1%84%87%E1%85%B5%E1%84%90%E1%85%B5_%E1%84%89%E1%85%A2%E1%86%BC%E1%84%86%E1%85%A7%E1%86%BC%E1%84%8C%E1%85%AE%E1%84%80%E1%85%B5/CCF7A485-D164-4EEF-A2A2-1436412A4D89.png)
![android log level](https://www.safaribooksonline.com/library/view/android-programming-the/9780134171517/tfActivityLifecycle/Log_Levels.png)


#android/실무에바로적용하는안드로이드