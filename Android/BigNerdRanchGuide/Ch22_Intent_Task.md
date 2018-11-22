# Ch22_Intent_Task
## 암시적 인텐트
암시적 인텐트를 통해 론칭 가능한 메인 액티비티들을 조회할 수 있다.
론칭 가능한 메인 액티비티는 Main 액션과 Launcher 카테고리를 포함하는 인텐트 필터를 갖는 액티비티다.
AndroidManifest에 다음과 같이 정의된다.

```xml
<intent-filter>
    <action android:name="android.intent.action.MAIN"/>

    <category android:name="android.intent.category.LAUNCHER"/>
</intent-filter>
```


암시적 인텐트를 생성하고 이 인텐트와 일치하는 액티비티 리스트를 PackageManager로부터 얻는다.

```kotlin
val startupIntent = Intent(Intent.ACTION_MAIN)
startupIntent.addCategory(Intent.CATEGORY_LAUNCHER)

val pm = activity?.packageManager
val activities = pm?.queryIntentActivities(startupIntent, 0)

Log.i(TAG, "Found ${activities?.size} + $activities")

```

`startActivity(Intent)` - “이 암시적 인텐트와 일치하는 액티비티를 시작시켜라.”를 의미하는 것이 아닌 “이 암시적 인텐트와 일치하는 **디폴트** 액티비티를 시작시켜라” 를 의미함.

암시적 인텐트를 startActivity() or startActivityForResult()를 통해 전달할 경우 안드로이드 운영체제는 내부적으로 Intent.CATEGORY_DEFAULT 카테고리를 인텐트에 추가한다.


## 런타임 시에 명시적 인텐트 생성하기
명시적 인텐트를 생성하려면 **ResolveInfo**로부터 **해당 액티비티의 패키지 이름과 클래스 이름**을 가져와아 한다. 이 데이터는 ResolveInfo의 필드로 포함된 ActivityInfo 내부 클래스 인스턴스로부터 얻을 수 있다.


`public Intent(Context packageContext, Class<?> cls`
생성자에서는 인텐트 꼭 필요한 ComponentName을 인자로 받는다. ComponentName은 패키지 이름과 클래스 이름이 합쳐진 것이다. 인텐트를 생성하기 위해 Activity와 Class를 인자로 전달하면 이 생성자에서는 Context 인자로 전달된 Activity로부터 전체 경로의 패키지 이름을 결정한다.


##  태스크와 Back 스택
안드로이드는 실행 중인 각 애플리케이션 내부에 사용자의 상태(어떤 앱을 사용 중인지)를 기록 유지하기 위해 태스크를 사용한다. 안드로이드의 기본 론처 앱으로 열린 각 애플리케이션은 자신만의 태스크를 갖는다. 이것은 매우 바람직한 일로써 향후 개발자가 디버깅하거나 특정 에러를 직면하였을 때에 훌륭한 단서와 솔루션을 제공해줄 것이다.

### 태스크 (Task)
태스크는 사용자와 관련되는 액티비티들을 갖는 스택이다. 스택의 제일 아래에 있는 액티비티를 기본 액티비티(base activity)라고 한다. 
 제일 위에 있는 액티비티가 사용자가 현재 화면으로 보고 있는 액티비티가 된다. 
사용자가 장치의 Back 버튼을 누르면  -> 제일 위의 액티비티가 스택에서 제거됨
만일 사용자가 기본 액티비티에서 back 버튼 클릭 -> 홈 화면으로 돌아감.

### 태스크 전환하기
오버뷰 화면을 사용하면 각 태스크의 상태에 영향을 주지 않고 태스크 간 전환을 할 수 있다. 
Ex) 새로운 연락처를 입력하다가 트위터 피드의 확인으로 전환하면 -> 두 개의 태스크가 시작된 것 -> 다시 연락처 입력으로 돌아오면 -> 두 태스크 모두 스택에 저장됨

* **오버뷰(Overview)** - 태스크 매니저, 최근 화면, 최근 앱 화면, 최근 태스크 리스트 등 다양한 이름으로 불림

오버뷰에서 각 항목을 닫아서 해당 앱의 태스크 삭제 -> 해당 애플리케이션의 Back 스택에 있는 애플리케이션의 모든 액티비티 제거됨

## 새로운 태스크 시작하기
Intent를 생성할 때 플래그를 추가해주면 기존 태스크가 아닌 새로운 태스크에서 액티비티를 시작할 수 있다.

```kotlin
val activityInfo = resolveInfo?.activityInfo ?: return
val intent = Intent(Intent./ACTION_MAIN/)
        .setClassName(activityInfo.applicationInfo.packageName,
                activityInfo.name)
        .addFlags(Intent./FLAG_ACTIVITY_NEW_TASK/)
startActivity(intent)
```


## NerdLauncher를 홈 화면으로 사용하기
AndroidManifest에서  메인 인텐트 필터에 추가하면 된다.

```xml
<intent-filter>
    <action android:name="android.intent.action.MAIN"/>

    <category android:name="android.intent.category.LAUNCHER"/>
    <category android:name="android.intent.category.HOME"/>
    <category android:name="android.intent.category.DEFAULT"/>
</intent-filter>

```



## 프로세스 vs 태스크
* 안드로이드의 **프로세스** - 애플리케이션의 객체들이 메모리에 존재하면서 실행될 수 있도록 운영체제가 생성하는 것
	* 운영체제에 의해 관리되는 자신의 리소스를 가질 수 있다. Ex) 메모리, 네트워크 소켓, 파일
	* 프로세스는 최소 하나 이상의 스레드 가짐
	* 모든 애플리케이션의 컴포넌트는 정확하게 하나의 프로세스와 연관됨
	* 애플리케이션은 자신의 프로세스로 생성됨 -> 이 프로세스가 애플리케이션에 있는 모든 컴포넌트의 디폴트 프로세스가 됨.
	
### 모든 액티비티 인스턴스는 정확히 하나의 프로세스에 존재하며, 하나의 태스크로 참조된다.
* **태스크**는 액티비티만 포함하며, 때로는 서로 다른 애플리케이션 프로세스에 존재하는 액티비티들로 구성된다.
* **프로세스**는 한 애플리케이션의 모든 실행 코드와 객체들을 포함함


## 동시 문서 (Concurrent Document)
동시 문서에서는 런타임 시에 앱의 태스크를 몇 개라도 동적으로 생성할 수 있다. 롤리팝 이전에는 사전 정의된 태스크들만 가질 수 있었으며, 그것들의 이름이 매니페스트에 정의되어야만 했었다.

### 동시문서 사용 방법
1. Flag 사용 - `Intent.FLAG_ACTIVITY_NEW_DOCUMENT` 를 인텐트에 추가한다

2. 매니페스트에 추가
```xml
<activity 
	android:name=".CrimePagerActivity"
	android:label="@string/app_name"
	android:parentActivityName=".CrimeListActivity"
	android:documentLaunchMode="intoExisting" />
```

단, 위의 1번은 한 문서당 하나의 태스크만 생성됨
추가로 더 태스크를 생성하고 싶으면
위의 플래그와 `Intent.FLAG_ACTIVITY_MULTIPLE_TASK` 플래그를 추가하면 된다.





#android/실무에바로적용하는안드로이드