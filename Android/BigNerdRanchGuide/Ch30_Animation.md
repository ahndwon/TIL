# Ch30_Animation
```kotlin
private fun startAnimation() {
  val sunYStart = mSunView.top
	val sunYEnd = mSkyView.height

	val heightAnimator =            				ObjectAnimator.ofFloat(mSunView,
                    "y",
                    sunYStart.toFloat(),
                    sunYEnd.toFloat())
                    .setDuration(3000)

  heightAnimator.start()
}
```

**인터폴레이션(Interpolation)** - 시작과 끝 지점 사이의 값을 찾는 것

## 뷰 변형 속성
### 뷰 회전 속성
* rotation,
* pivotX
* pivotY


### 뷰 크기 속성
* scaleX
* scaleY

### 뷰 이동 속성
* translationX
* translationY

## 인터폴레이터 사용하기
애니메이션에 가속력을 주기 위해선 **AccelerateInterpolator**을 사용하면 된다
```kotlin
private fun startAnimation() {
  val sunYStart = mSunView.top
	val sunYEnd = mSkyView.height

	val heightAnimator =            				ObjectAnimator.ofFloat(mSunView,
                    "y",
                    sunYStart.toFloat(),
                    sunYEnd.toFloat())
                    .setDuration(3000)

	heightAnimator.interpolator = AccelerateInterpolator()
  heightAnimator.start()
}
```

## 여러 애니메이션 같이 사용하기
첫번째 애니메이션이 끝나면 두번째 애니메이션이 시작되도록 리스너를 달 수 있으나 코드가 복잡해진다. 그러므로 **AnimatorSet**을 사용하여 쉽게 처리하는것이 좋다.

```kotlin
val animatorSet = AnimatorSet()
animatorSet.play(heightAnimator)
        .with(sunsetSkyAnimator)
        .before(nightSkyAnimator)
animatorSet.start()

```



## 다른 애니메이션 API
### 기존의 애니메이션 도구
android.view.animation 패키지에는 기존의 애니메이션 도구가 있다. 허니콤에서 소개된 더 새로운 애니메이션 도구는 android.animation 패키지에 있다.

기존의 애니메이션 프레임워크는 클래스명에 animation이 꼭 들어가는데 무시해도 좋다.

### 전환
안드로이드 4.4 킷캣에서 새로운 애니메이션이 추가되었다. 이것을 통해 뷰 계층 구조 간에 멋진 전환을 수행할 수 있다.

전환 프레임워크의 기본적인 개념 - **장면(scene)**과 장면 간의 전환을 정의할 수 있다.

**장면(scene)** - 특정 시점에서 뷰 계층 구조의 상태를 나타냄. 

장면은 XML 레이아웃 파일로 정의하며, 전환은 애니메이션 XML파일로 나타낼 수 있다.


## 애니메이션 프레임워크 결정요인
* 애니메이션 액티비티가 이미 실행 중이면서 정해진 레이아웃이 화면이 나와 있을 경우 -> 속성 애니메이션 프레임워크 사용
* 앞으로 화면에 나타날 레이아웃을 애니메이션 할 때 -> 전환 프레임워크




#android/실무에바로적용하는안드로이드