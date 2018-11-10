# Ch17_Master_Detail
```kotlin
@LayoutRes
protected fun getLayoutResId() : Int {
    return R.layout./activity_fragment/
}
```

* **@LayoutRes** - 안드로이드 스튜디오에게 이 메서드를 오버라이드 시 반드시 레이아웃 리소스 ID를 반환해야 함을 알려줌


## *alias resource*
또 다른 리소스를 참조하는 리소스
`res/values/` 에 위치하며 
`ref.xml` 파일에 정의함

```xml
<resources>
    <item name="activity_masterdetail" type="layout">@layout/activity_fragment</item>
</resources>
```


안드로이드는 리소스의 ID로 리소스를 참조한다.
빌드 도구 **aapt** (android asset package tool)에의 자동 생성되는 R.java 파일에 정의된다.
안드로이드 스튜디오는 우리 앱을 빌드 할 때 자동으로 R.java 파일을 생성한다.


**Smallest width 600dp 앨리어스**
* 지정된 화면 크기보다 작은 장치에서는 activity_fragments.xml을 사용한다.
* 지정된 화면 크기 이상의 장치에서는 activity_twopane.xml을 사용한다.

`sw600dp/refs.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <item name="activity_masterdetail" type="layout">@layout/activity_twopane</item>
</resources>
```

-sw600dp 구성 수식자에서 sw는 smallest width를 의미하며 화면의 최소 크기를 나타낸다.



## 프래그먼트 콜백
프래그먼트를 특정 액티비티의 FragmentManager 를 통해 추가하는 식으로 하면 더 이상 프래그먼트가 독자적이지 않고 액티비티에 종속된다.
프래그먼트를 조립 구성이 가능한 단위로 만들기 위해선 콜백 인터페이스를 사용해야 한다.

콜백 인터페이스는 프래그먼트의 보스인 호스팅 액티비티가 처리할 필요가 있는 일을 정의한다. -> 어떤 액티비티가 호스팅하는지 프래그먼트가 알 필요 없이 자신을 호스팅하는 액티비티의 메서드들을 호출할 수 있다.






#android/실무에바로적용하는안드로이드