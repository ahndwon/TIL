# Ch13_Toolbar
툴바는 안드로이드 5.0 (롤리팝)에서 새로 추가되었다. 그 전엔 액션 바(ActionBar) 사용
Toolbar와 ActionBar는 비슷하지만 Toolbar가 더 유연한 사용자 인터페이스를 제공한다.

안드로이드 서포트 라이브러리를 사용할시 API7까지 사용가능하다.


## AppCompatActivity 클래스 사용하기
AppCompat 라이브러리를 사용하기 위해선 Activity가 AppCompatActivity를 상속하도록 해야 한다.


## 메뉴
툴바의 제일 오른쪽에 메뉴를 둘 수 있다. 메뉴는 Action Item으로 구성된다. (메뉴 항목이라고도 함)

액션 항목의 명칭은 문자열 리소스로 만들어야 한다.

## XML로 메뉴 정의하기
메뉴는 레이아웃과 유사한 리소스 이므로 XML 파일로 생성하여 프로젝트의 resmenu 디렉터리에 둬야함

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http:schemas.android.comapkresandroid" 
      xmlns:app="http:schemas.android.comapkres-auto">
    <item
            android:id="@+idmenu_item_new_crime"
            android:icon="@android:drawableic_menu_add"
            android:title="@stringnew_crime"
            app:showAsAction="ifRoom|withText">
<menu>
```
* **showAsAction 속성**  - 액션 항목이 툴바에 나타날 것인지, 아니면 오버플로 메뉴에 나올 것인지를 나타냄
	* **withText** - 아이콘과 함께 텍스트가 표시됨
	* **always** -  항상 메뉴를 표시함 (권장 X)
	* **ifRoom** - 화면의 공간에 여유가 있는지 운영체제가 판단해서 보여줌 (권장함)
	* **never** - 자주 사용하지 않는 액션의 경우에 사용해서 오버플로 메뉴에 두는 것이 좋다.


### 앱 네임스페이스
showAsAction 속성을 이용하기 위해선 Android 네임스페이스가 아닌 App  네임스페이스가 필요하다.

app과 같이 특이한 네임스페이스는 AppCompat 라이브러리와 관련해서 필요하다.


### 메뉴 생성하기
메뉴는 Activity 클래스의 콜백 메서드들에 의해 관리된다.
콜백 (특정 상황에서 시스팀이 호출하는 메서드)
Activity 에선 메뉴를 생성하기 위해 `onCreateOptionsMenu(Menu)`를 사용하지만
Fragment에선 onCreateOptionsMenu(Menu, MenuInflater) 를 사용한다.

```kotlin
override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {	
	  // super를 호출함으로써 혹시나 필요한 부모 클래스의 기능이 동작하도록 한다.
    super.onCreateOptionsMenu(menu, inflater)
    inflater.inflate(R.menu.fragment_crime_list, menu)
}

```


FragmentManager에게 이 프래그먼트가 메뉴를 생성해야한다는 걸 명시적으로 알려주기 위해 다음을 호출한다.
```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
}
```

### 메뉴 생성에 응답하기
메뉴는 주로 하나 이상이므로 어떤 액션 항목이 선택되었는지 Id로 구분해줘야 한다.
그리고 그 항목에 적합한 응답을 구현해주면 된다.

```kotlin
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.menu_item_new_crime-> {
            val crime = Crime()
            CrimeLab.crimes.add(crime)
            activity?.let{
val intent = CrimePagerActivity.newIntent(it, crime.mId)
                startActivity(intent)
                return true
            }
}
    }
    return super.onOptionsItemSelected(item)
}
```

**Boolean을 반환하는 이유**
선택된 MenuItem을 처리하고 나면 더 이상의 처리가 필요 없다는 것을 나타내기 위해 true를 반환함. 처리를 구현하지 않았을 경우 슈퍼 클래스의 메서드를 호출한다.

### 계층적 내비게이션 활성화하기
* **Temporal Navigation(일시적 내비게이션)** 
이동하는 화면 간의 관계가 일시적이며 , Back 버튼을 누르면 직전에 있던 화믄으로 이동함

* **Hierarchical Navigation(계층적 내비게이션)**
일시적 내비게이션과 달리 앱 계층의 위로 이동함 (Ancestral Navigation이라고도 함)
툴바 왼쪽의 Up 버튼을 눌러서 앱 화면을 이동시킨다.

**계층적 내비게이션 사용법**
AndroidManifest 파일에서 액티비티의 부모 액티비티를 명시해줘야 한다.
```xml
<activity
        android:name=".controllers.CrimePagerActivity"
		android:parentActivityName=".controllers.CrimeListActivity">

</activity>
```

### 계층적 내비게이션의 동작 방법
 겉으로 보기엔 Back 버튼 클릭시와 Up 버튼 클릭시 동일한 결과가 나타나지만 내부적으로 다르게 처리된다.
Up버튼을 클릭시 해당 액티비티에서 인텐트를 생성하여 플래그를 설정하고 부모 액티비티를 실행시키고 finish()를 통해 종료한다.

```kotlin	
val intent = Intent(this, CrimeListActivity.class)
intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
startActivity(intent)
finish()
```

**FLAG_ACTIVITY_CLEAR_TOP**
설정시 액티비티 스택에서 부모 액티비티의 기존 인스턴스를 찾되, 만일 기존것이 존재한다면 그 액티비티 인스턴스가 스택의 제일 위로 올라오도록 모든 다른 액티비티를 스택에서 꺼내게 한다.


## 툴바 vs 액션 바
* Toolbar 
	* 시각적인 디자인이 변경된 것이 가장 큰 차이점
	* 툴바에서는 제일 왼쪽에 아이콘이 나타나지 않음
	* 오른쪽의 액션 항목 간의 간격이 줄어듬
	* Up 버튼이 더 잘 보임
	* 액션바보다 유연성이 좋음
	* 액션바와 달리 항상 화면 위에 있을 필요 X, 여러개 사용 가능, 크기도 조절 가능
	* 내부에 뷰를 둘 수 있다

* ActionBar
	* 항상 화면 위에 있어야함
	* 하나만 사용 가능
	* 크기 고정






#android실무에바로적용하는안드로이드