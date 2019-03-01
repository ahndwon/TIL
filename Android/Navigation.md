# Navigation
새롭게 추가된 네비게이션 컴포넌트는 안드로이드 스튜디오 3.2 이상부터 사용 가능
3.3은 기본적으로 추가되어 있지만 3.2는 설정에서 적용을 해줘야함.


## Navigation Component
네비게이션은 3가지의 핵심 부분으로 이루어져 있다.

1. Navigation Graph (새로운 XML 리소스
2. NavHostFragment (Layout XML View)
3. NavController (Kotlin/Java Object)



## Navigation Graph
**Destinations**
앱에서 네비게이션으로 갈 수 있는 모든 곳.
주로 프래그먼트나 액티비티이다.
필요할 경우 커스텀 destination type을 만들 수 있다.

**Navigation Graph**
유저가 네비게이션을 통해 접근할 수 있는 모든 것을 보여주는 새로운 리소스 타입
시각적으로 출발지에서 목적지를 보여준다.

Navigation XML file을 보면
<navigation>태그가 있다

```xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:app="http://schemas.android.com/apk/res-auto"
            xmlns:tools="http://schemas.android.com/tools"
    app:startDestination="@+id/home_dest">

    <!-- ...tags for fragments and activities here -->

</navigation>
```

* <navigation>은 navigation graph의 root node이다
* <navigation>은 <activity>나 <fragment>인 하나 이상의 목적지를 가진다
* app:startDestination 속성은 유저가 앱을 처음 켯을때 목적지를 명시한다.

* android:id - xml이나 코드에서 레퍼런스에 접근할 때 사용하는 id
* android:name - 프래그먼트의 완전한 클래스명을 명시하여 네비게이션시 인스턴스화할 프래그먼트를 명시
* tools:layout 속성으로 레이아웃을 명시해야 에디터에 프래그먼트가 표시된다.


### Navigation Graph를 이용한 네비게이트
![](Navigation/564FA72B-3F81-4BA8-B60C-C4DEDACB28ED.png)

![NavHostFragment](https://codelabs.developers.google.com/codelabs/android-navigation/img/fd47b155b24e2e9b.png)
**NavHostFragment**
액티비티는 하단탭과 같은 글로벌한 네비게이션을 가진다. Fragment들은 실제 목적지 레이아웃이다
유저가 네비게이션을 하면 NavHostFragment는 프래그먼트 목적지들을 바꿔치기한다.

```xml
<fragment
    android:id="@+id/my_nav_host_fragment"
    android:name="androidx.navigation.fragment.NavHostFragment"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:defaultNavHost="true"
    app:navGraph="@navigation/mobile_navigation" />

```

* `android:name="androidx.navigation.fragment.NavHostFragment"` 과 `app:defaultNavHost="true"` 는 시스템 백버튼을 NavHostFragment와 연결한다.
* `app:navGraph="@navigation/mobile_navigation"` 는 NavHostFragment를 navigation graph와 연결한다.


### NavController
유저가 레이아웃과 상호작요을 할 때 네비게이션 커맨드가 실행되어야 한다.
NavController라는 클래스가 그 명령을 실행한다.

```java
// Command to navigate to flow_step_one_dest
findNavController().navigate(R.id.flow_step_one_dest)
```

**findNavController**
코틀린 사용시 NavController를 NavHostFragment에서 다음과 같이 접근할 수 있다.
명령 사용 위치에 따라 선택해서 사용하면 된다.
* `Fragment.findNavController()`
* `View.findNavController()`
* `Activity.findNavController(viewId: Int)`


### Actions
![](Navigation/20364318-062A-499C-A064-3C78C54BDB7E.png)
![Actions](https://codelabs.developers.google.com/codelabs/android-navigation/img/82f00bfa471bd0ff.png)

Navigation by actions는 Navigations by destination보다 다음과 같은 장점이 있다.
* 앱의 네비게이션을 시각화 할 수 있다.
* 액션은 추가적인 속성을 부여할 수 있다. ex) 트랜지션 애니메이션, argument values, 백스택 behavior
* plugin safe args로 navigate 가능

```xml
<fragment
    android:id="@+id/flow_step_one_dest"
    android:name="com.example.android.codelabs.navigation.FlowStepFragment">

    <argument
        .../>

    <action
        android:id="@+id/next_action"
        app:destination="@+id/flow_step_two_dest">
    </action>
</fragment>
```

* 액션들은 destination에 중첩된다. - 액션을 실행할 곳
* `app:destination`은 navigate의 종착지이다
* 액션의 id는 “next_action”이다

![](Navigation/154DFEE2-C7B8-4F18-82CC-612B442EABD1.png)
![next action](https://codelabs.developers.google.com/codelabs/android-navigation/img/75de314564fea9b3.png)

```xml
<fragment
    android:id="@+id/home_dest"
    android:name="com.example.android.codelabs.navigation.HomeFragment"
    .../>

<fragment
    android:id="@+id/flow_step_two_dest"
    android:name="com.example.android.codelabs.navigation.FlowStepFragment">

    <argument
        .../>

    <action
        android:id="@+id/next_action"
        app:popUpTo="@id/home_dest">
    </action>
</fragment>
```

* 아까 전과 같이 액션의 ID 가 “next_action”이다. 이것은 액션들을 추상화할 수 있도록 해주며 컨텍스트에 따라 네비게이트를 할 수 있게 해준다.
* `app:popUpTo` 속성은 home_dest 프래그먼트로 접근할 때까지 백스택에서 프래그먼트들을 pop 한다.




### Using safe args for navigation
**Safe Args**
Navigation Component는 **safe args** 라는 Gradle Plugin을 가진다. 이것은 destinations와 actions들을 type-safe 하게 접근하기 위한 간단한 object와 빌더 클래스들을 제공한다.

Safe Args는 다음과 같은 보일러 플레이트를 제거해준다
`val username = arguments?.getString("usernameKey")`
위의 코드를 아래와 같이 작성하도록 해준다. **setter와 getter는 safeargs를 통해 생성된다.**
`val username = args.username`

**장점**
기존의 방법은 전달받은 argument의 값이 Nullable이다.
Safe Args를 사용시 타입은 Nullable이 아니다.


#### Safe Args를 통한 값 전달
1. 다음을 `app/build.gradle`에 추가해야 한다.
`apply plugin: 'androidx.navigation.safeargs'`


2. mobile_navigation.xml에 argument 를 추가
```xml
<fragment
    android:id="@+id/flow_step_one_dest"
    android:name="com.example.android.codelabs.navigation.FlowStepFragment"
    tools:layout="@layout/flow_step_one_fragment">
    <argument
        android:name="flowStepNumber"
        app:argType="integer"
        android:defaultValue="1"/>

    <action...>
    </action>
</fragment>
```
<argument> 태그를 사용함으로써 safeargs는 **FlowStepFragmentArgs**라는 클래스를 생성한다.

![](Navigation/36E591B5-DD39-4C7A-A582-2D976AA0BA1A.png)
![FlowStepFragmentArgs](https://codelabs.developers.google.com/codelabs/android-navigation/img/e79412ae551bf451.png)

XML에 `flowStepNumber`이라는 argument가 포함되어 있으므로 FlowStepFragmentArgs는 다음과 같이 flowStepNumbe이라는 변수가 getter와 setter를 포함한 채로 생성된다.

![](Navigation/6264D155-4743-40A2-A7C2-3652DB217326.png)
![flowStepNumber](https://codelabs.developers.google.com/codelabs/android-navigation/img/c0306a5023f6a7e1.png)

3. FlowStepFragment.kt를 연다
4. 기존 코드를 safeArgs로 생성된 게터와 세터로 변경한다.
```kotlin
// 기존 코드
// val flowStepNumber = arguments?.getInt("flowStepNumber")

// 새 코드
val safeArgs = FlowStepFragmentArgs.fromBundle(arguments)
val flowStepNumber = safeArgs.flowStepNumber
```


#### Safe Args Direction Classes
Safe args 를 통해 argument가 있든 없든 type safe한 방법으로 네비게이트 할 수 있다. 그러기 위해선 생성된 Directions classes들을 사용하면 된다.
![](Navigation/8C5601F9-18FE-4107-B0D5-444628BA544F.png)
![Direction Classes](https://codelabs.developers.google.com/codelabs/android-navigation/img/e79412ae551bf451.png)

 Direction Class들은 액션을 가진 각각의 destination에 모두 생성된다. Direction Class는 destination이 가진 모든 액션에 대해 메소드를 포함한다.

```kotlin
view.findViewById<Button>(R.id.navigate_action_button)?.setOnClickListener{
    val action = HomeFragmentDirections.nextAction()
    action.setFlowStepNumber(1)
    findNavController().navigate(action)
}
```


## NavigationUI
NavigationUI는 menu item과 동일한 ID를 가진 destination을 현재 그래프에서 찾아서 메뉴아이템이 그 destination으로 navigate되도록 한다.

**Using NavigationUI with an Options menu**
NavigationUi를 사용하는 가장 쉬운 방법은 option menu setup을 간단화 할 때 사용하는 것이다. NavigationUI는 특히 `onOptionsItemSelected` 콜백을 간단화한다.

1. Open MainActivity.kt
`onCreateOptionsMenu`에서 menu를 inflate 한다.
2. Open `res/menu/overflow_menu.xml`
3. 메뉴를 포함하도록 xml 설정
4. MainActivitiy.kt에서 `onOptionsItemSelected`를`onNavDestinationSelected`헬퍼 메소드를 통해 구현.  만약 menu item이 navigate용도가 아니라면 `super.onOptionsItemSelected`로 다루어야 한다.
```kotlin
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return item.onNavDestinationSelected(findNavController(R.id.my_nav_host_fragment))
            || super.onOptionsItemSelected(item)
}
```




##  Deep Linking to a destination
### Deep Links and Navigation
Navigation Component는 deep link를 지원한다. 
Deep Links는 앱의 네비게이션 한 중간에 들어가게 해준다. 그럼으로써 사용자가 제대로된 destination에서 올바른 back stack을 가지 상태로 앱을 이용하게 해준다. 특히 widgent이나 알림 혹은 웹 링크 사용시 중요하다.

Deep Link는 빌더를 통해 생성한 PendingIntent로 구현한다.
```kotlin
val args = Bundle()
args.putString("myarg", "From Widget");
val pendingIntent = NavDeepLinkBuilder(context)
        .setGraph(R.navigation.mobile_navigation)
        .setDestination(R.id.deeplink_dest)
        .setArguments(args)
        .createPendingIntent()

remoteViews.setOnClickPendingIntent(R.id.deep_link_button, pendingIntent)
```


**DeepLink Backstack**
Deep link의 백스택은 navigation graph에 따라 결정된다. 설정한 액티비티가 부모 액티비티를 가질 경우 그 부모 액티비티가 백스택에 포함된다.


## Associating a web link with a destination
**<deepLink>** 
기존엔 intent-filter와 url을 통해 웹링크로 앱의 액티비티를 열 수 있었다.
Navigation library를 사용하면 훨씬 쉽고 간단하게 구현 할 수 있다.


* scheme이 별도로 정해지지 않은 URI는 http나 https로 시작하도록 간주한다.
* placeholder를 사용하여 같은 이름을 갖는 값을 번들에 인자로 전달할 수 있다.
Ex). `http://www.example.com/users/{id}` will match `http://www.example.com/users/4`.


#### 사용법
1. Navigation. graph에 추가
```xml
<fragment
    android:id="@+id/deeplink_dest"
    android:name="com.example.android.codelabs.navigation.DeepLinkFragment"
    android:label="@string/deeplink"
    tools:layout="@layout/deeplink_fragment">

    <argument
        android:name="myarg"
        android:defaultValue="Android!"/>

    <deepLink app:uri="www.example.com/{myarg}" />
</fragment>
```

2. AndroidManifest에 nav-graph 태그를 추가하여 적절한 intent filter가 생성되도록 보장
```xml
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>

    <nav-graph android:value="@navigation/mobile_navigation" />
</activity>
```
3. 테스트
Adb로 테스트 가능하다.
`adb shell am start -a android.intent.action.VIEW -d "http://www.example.com/urlTest"`







출처 : [Navigation Codelab](https://codelabs.developers.google.com/codelabs/android-navigation/#0)






#android/ui