# Ch15_Implicit_Intent
**암시적 인텐트(Implicit intent)**
* 장치의 다른 애플리케이션에 있는 액티비티를 실행 가능

**명시적 인텐트(explicit intent)**
* 시작시킬 액티비티 클래스를 직접 지정해주면 안드로이드 운영체제가 그 액티비티를 시작시킨다.



앱이 이미 설치되어 기존 데이터베이스에 새로 추가된 용의자 열이 없으면 그 열을 추가하기 위한 `onCreate(SQLiteDatabase)` 메서드도 실행되지 않는다. 
-> 기존 데이터베이스를 삭제하고 새것으로 교체하는 것이 좋다

데이터베이스를 삭제하는 방법 : 앱 삭제

## 포맷 문자열
`<string name="crime_report">%1$s! 이 범죄가 발견된 날짜는 %2$s. %3$s, 그리고 %4$s</string> `


## 암시적 인텐트 사용하기
인텐트는 우리가 하고자 하는 것을 안드로이드 운영체제에 알려주는 객체다. 그런 작업을 할 수 있다고 자신을 알린 액티비티를 안드로이드 운영체제가 찾아서 시작시킨다. 단, 여러 개를 찾았을 경우 사용자가 선택할 수 있게 해준다.

```kotlin
val intent = Intent(getActivity(), CrimePagerActivity.class)
intent.putExtra(EXTRA_CRIME_ID, crimeId)
startActivity(intent)
```


### 암시적 인텐트의 구성 요소
**우리가 수행하려는 액션(Action)**
* Intent 클래스의 상수들. 만일 웹 URL를 보기 원한다면 Intent.ACTION_VIEW를 액션으로 사용하고 어떤 것을 전송할떈 Intent.ACTION_SEND

**데이터의 위치**
* 장치 외부의 데이터. 웹페이지의 URL 혹은 파일에 대한 URI 또는 ContentProvider의 데이터베이스 레코드를 가리키는 콘텐트 URI

**액션에서 필요한 데이터의 타입**
* text_html이나 audio_mpeg3과 같은 MIME 타입. 
* 만일 인텐트가 데이터의 위치를 포함하고 있으면 그 데이터로부터 타입 유추 가능

**선택적으로 사용 가능한 카테고리**
* 액티비티를 어디서, 언제, 어떻게 사용할지를 나타냄
* 액티비티가 최상위 수준의 앱으로 론처에 보여야 하면 -> android.intent.category.LAUNCHER 카테고리를 사용









#android/실무에바로적용하는안드로이드