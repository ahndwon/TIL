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

## 인텐트로 통신하기
**인텐트**는 컴포넌트가 운영체제와 통신하기 위해 사용할 수 있는ㄱ ㅐㄱ체다. 지금까지 우리가 보았던 컴포넌트는 액티비티뿐이다. 그러나 컴포넌트에는 서비스, 브로드캐스트 수신자, 콘텐트 제공자 등 여러 가지가 있다.

`public Intent(Context packageContext, Class<?> cls)`

**인텐트로 액티비티 시작**
```java
Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
startActivity(intent);
```
액티비티를 시작시키기에 앞서 ActivityManager는 시작시킬 액티비티 클래스로 지정된 이름이 그 패키지의 매니페스트에 선언되어 있는지를 확인한다.


## 명시적 인텐트와 암시적 인텐트

- 명시적 인텐트 - Context와 Class 객체를 갖는 Intent 객체를 생성하는 것은 명시적(explicit) 인텐트를 생성하는 것이다.  애플리케이션 내부에 있는 액티비티를 시작시키기 위해 사용됨
- 암시적 인텐트 - 한 애플리케이션의 액티비티에서 다른 애플리케이션의 액티비티를 시작시키고자 할 때는 암시적(implicit) 인텐트를 생성한다.


## 인텐트 엑스트라 사용하기
**엑스트라**
- 호출하는 액티비티가 인텐트에 포함시킬 수 있는 임의의 데이터. 요청된 인텐트는 안드로이드 운영체제가 받아서 수신 액티비티에 전달한다. 그 수신 액티비티는 인텐트의 엑스트라에 액세스하여 그것의 데이터를 사용할 수 있다.
- 키와 값이 한 쌍으로 된 구조

**사용법**
```java

// 발송 액티비티
Boolean isAnswer = true;
Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
intent.putExtra(EXTRA_ANSWER_IS_TRUE, isAnswer);
startActivity(intent);

// 수신 액티비티
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
```

## 자식 액티비티로부터 결과 돌려받기
`public void startActivityForResult(Intent intent, int requestCode)`

요청코드 - 사용자가 정의한 정수, 자식 액티비티에 전달되었다가 부모 액티비티가 다시 돌려받는다. 그리고 부모 액티비티가 여러 타입의 자식 액티비티들을 시작시킬 경우에 어떤 자식 액티비티가 겨로가를 돌려주는 알고자 할 때 사용됨.



#android/실무에바로적용하는안드로이드