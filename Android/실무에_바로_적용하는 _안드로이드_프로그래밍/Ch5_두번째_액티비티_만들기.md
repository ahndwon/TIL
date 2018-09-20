# Ch5_두번째_액티비티_만들기
**액티비티를 실행** - 액티비티의 인스턴스를 생성하고 onCreate(Bundle)메서드를 호출하도록 운영체제에 요청한다는 것을 의미한다


`tools:text` - 디자인 Preview에선 텍스트가 나타나지만 앱을 실행하면 나타나지 않는다.

### Manifest
매니페스트는 XML 파일로, 안드로이드 운영체제에 애플리케이션을 설명하는 메타데이터를 표함한다. 파일 이름은 AndroidManifest.xml이며, 프로젝트의 app/manifests 디렉터리에 위치한다.

액티비티를 생성 후 매니페스트에 추가해야한다.
`<activity android:name=".CheatActivity" />`
.은 액티비티 클래스가 앞의 package 속성에 지정된 패키지에 위치한다는 것을 안드로이드 운영체제에 알려준다.

### 액티비티 추가시 필수 요소
	- 액티비티 클래스 생성
	- 액티비티 레이아웃 xml 생성
	- Android Manifest에 액티비티 추가

### 액티비티 시작시키기
`public void startActivity(Intent intent)`

![](Ch5_%E1%84%83%E1%85%AE%E1%84%87%E1%85%A5%E1%86%AB%E1%84%8D%E1%85%A2_%E1%84%8B%E1%85%A2%E1%86%A8%E1%84%90%E1%85%B5%E1%84%87%E1%85%B5%E1%84%90%E1%85%B5_%E1%84%86%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%80%E1%85%B5/6E34669F-D656-4124-9F87-B6B49CE22A02.png)
![Intent](https://t1.daumcdn.net/cfile/tistory/272FC83A53EA47590D)







#android/실무에바로적용하는안드로이드