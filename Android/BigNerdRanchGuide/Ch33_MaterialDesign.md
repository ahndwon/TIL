# Ch33_MaterialDesign
## 머티리얼 디자인
안드로이드 5.0 롤리팝에서 소개됨

### 머티리일 디자인의 개념 3가지
1. 머티리얼은 메타포(metaphor)다.
	-  앱의 구성 요소가 실제 사물이나 소재처럼 동작해야 한다.
2. 선명하고 생생하며 의도가 반영되어야 한다. 
	- 앱 디자인의 결과물은 잘 디자인된 잡지나 책에 있는 것처럼 사실감 있게 느껴져야 한다.
3. 움직임이 의미를 부여한다
	- 사용자의 액션에 대한 응답으로 앱이 생동감 있게 움직여야 한다.


## 머티리얼 서피스
* 사물이나 소재의 표면을 의미
* 개발자가 머티리얼 디자인을 이해하는 데 필요한 가장 중요한 개념.
* 디자이너는 1dp 두께의 얇은 종이 소재로 생각함.
* 서로 다른 머티리얼 서피스는 서로를 통과할 수 없다.


### 엘리베이션과 z값
인터페이스에 입체감을 주기 위해선 뷰에 엘리베이션을 주어 안드로이드가 그림자를 그리도록 하면 된다.

엘리베이션은 Z 면을 긱준으로 하기 때문에 XML 속성을 사용하는 것이 좋다.

Z 속성 : elevation + translationZ

## 상태 리스트 애니메이터
상태 리스트 애니메이터는 상태 리스트 drawable과 다른 방법으로 동작한다.
하나의 drawable을 다른 것으로 변경하는 대신에 해당 뷰를 특정 상태로 애니메이션함.

**버튼을 누르면 솟아 오르는 애니메이션**
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
	<item android:state_pressed="true">
		<objectAnimator android:propertyName="translationZ"
						 android:duration="100"
						 android:valueTo="6dp"
						 android:valueType="floatType"
						 />
	</item>
	<item android:state_pressed="false">
		<objectAnimator android:propertyName="translationZ"
						 android:duraiton="100"
						 android:valueTo="0dp"
						 android:valueType="floatType"
						 />
	</item>
</selector>
```
속성(property) 애니메이션을 사용해야 할 때는 이 방법이 좋다.
하지만 장면(scene)과 전환(transition)을 사용하는 프레임 애니메이션을 사용할 때는 **애니메이트 상태 리스트 drawable**을 사용해야 한다.


### 차이점
* 상태 리스트 애니메이터 - 각 상태의 이미지를 정의할 수 있다
* 애니메이트 상태 리스트 drawable -  각 상태의 이미지와 프레임 애니메이션을 정의할 수 있다.


**애니메이트 상태 리스트 drawable**
```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
	<item android:id="@+id/pressed"
		   android:drawable="@drawable/button_beat_box_pressed"
		   android:state_pressed="true" />
	<item android:id="@+id/released"
		   android:drawable="@drawable/button_beat_box_normal" />
	<transition
			android:fromId="@id/released"
			android:toId="@id/pressed">
	
		<animation-list>
			<item android:duration="10" android:drawable="@drawable/button_frame_1" />
			<item android:duration="10" android:drawable="@drawable/button_frame_2" />
			<item android:duration="10" android:drawable="@drawable/button_frame_3" />
		</animation-list>
	</transition>
<animated-selector>
```



## 애니메이션 도구
### 원형 노출
Circular Reveal Animation
사용자가 클릭한 곳에서 잉크가 번지는 것처럼 보인다.

![](Ch33_MaterialDesign/CEFEA9CD-5028-43EC-8FAF-FFCB242F4472.png)

![circular reveal](https://cdn-images-1.medium.com/max/1600/1*ZBwSTSkzwUFApLXL9LMkyw.gif)

**클릭 리스너에서 화면 클릭 좌표 알아내는 법**
```
@Override
public void onClick(View clickSource) {
	int[] clickCoords = new int[2];
	
	// 화면 상의 clickSource의 위치를 찾는다.
	clickSource.getLocationOnScreen(clickCoords);
	// 뷰의 모서리가 아닌 중심을 찾기 위해 위치 값을 변경한다.
	clickCoords[0] += clickSource.getWidth() / 2;
	clickCoords[1] += clickSource.getHeight() / 2;

	performRevealAnimation(mViewToReveal, clickCoords[0], clickCoords[1]);
}
```

**애니메이션을 생성하고 실행하는 메서드**
```java
private void performRevealAnimation(View view, int screenCenterX, int screenCenterY) {
	int[] animatingViewCoords = new int[2];
	view.getLocationOnScreen(animatingViewCoords);
	int centerX = screenCenterX - animationViewCoords[0];
	int centerY = screenCenterY - animationViewCoords[1];

	// 최대 반경 값을 찾는다.
	Point size = new Point();
getActivity().getWindowManager().getDefaultDisplay().getSize(size);
	int maxRadius = size.y;

	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		ViewAnimationUtils.createCircularReveal(view, centerX, centerY, 0, maxRadius).start();
	}
}
```



## 공유 요소 전환
(Shared Element Transition)

**액티비티 전환시 공유 요소 구현 세 단계**
1. 액티비티 전환을 사용할 수 있게 요청한다. (AppcompatActivity는 머티리얼 테마를 사용하므로 생략 가능)
2. 각 공유 요소 뷰의 전환 이름을 설정한다.
3. 전환을 시작시킬 ActivityOptions를 갖는 액티비티를 시작시킨다.



### 1.  액티비티 전환을 사용할 수 있게 요청한다
**코드에서 설정하기**
```java
@Override
public void onCreate(Bundle savedInstanceState) {		getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
	super.onCreate(savedInstanceState);
}
```

**스타일에서 설정하기**
```xml
<resources>
	<style name="TransparentTheme"
		parent="@android:style/Theme.Translucent.NoTitleBar">
		<item name="android:windowActivityTransitions">true</item>
	</style>
</resources>
```


### 2. 각 공유 요소 뷰의 전환 이름 설정
애니메이션 전환할 뷰의 속성에 transitionName을 설정해준다
`android:transitionName="image"`

그 다음 애니메이션을 수행할 뷰에 전환 이름을 설정한다
`ViewCompat.setTransitionName(sourceView, "image"); `















#android/실무에바로적용하는안드로이드