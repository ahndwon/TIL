# Ch1_첫_애플리케이션
## GeoQuiz 앱
하나의 액티비티와 하나의 레이아웃으로 구성됨
- **액티비티**는  안드로이드 SDK 클래스인 Activity의 인스턴스로, 화면을 통해서 사용자가 작업할 수 있게 해준다. -> 주로 Activity의 서브 클래스를 만들어서 사용
- **레이아웃** 은 사용자 인터페이스 객체들과 그것들의 위치를 정의한다.


**Package name** - 안드로이드 생태계 전체에서 우리 앱을 고유하게 식별하는데 사용된다.  안드로이드 표준을 따르며, Company Domain을 거꾸로 하고 그 뒤에 애플리케이션 이름이 붙어서 자동 생성됨


**Project 뷰** - 프로젝트를 구성하는 디렉터리와 파일을 컴퓨터 파일 시스템에 따라 저장된 형태로 볼 수 있다.

**Android 뷰** - 사용자가 주로 사용하는 파일이나 디렉터리만 그룹으로 분류하여 보여줘서 편리하다.


## 사용자 인터페이스 레이아웃 만들기
**위젯** - 사용자 인터페이스를 만드는 데 사용되는 구성 요소, -> 텍스트나 그래픽을 보여줄 수 있고, 사용자와 상호 동작할 수 있으며, 화면에 다른 위젯을 배치할 수 도 있다.
	모든 위젯은 View 클래스의 인스턴스이거나 View의 서브 클래스 중 하나의 인스턴스이다.

## 뷰 계층구조
위젯들은 View 객체들의 계층구조에 존재한다.

ViewGroup - 다른 위젯들을 포함하고 배열하는 위젯

## 위젯 속성
- match_parent - 자신의 부모만큼의 크기가 된다
- wrap_content - 자신이 갖는 콘텐츠에 필요한 크기가 된다.
- fill_parent - match_parent와 같음 하지만 deprecated

## 위젯의 참조 얻기
```java
// View 객체가 반환되기 떄문에 해당하는 View의 Subclass로 캐스팅 해줘야 한다.
public View findViewById(int id)

public class QuizActivity extends AppCompatActivity {
	private Button mTrueButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstaceState);
		setContentView(R.layout.activity_quiz);
		
		mTrueButton = (Button) findViewById(R.id.true_button);
	}
}
```

## 리스너 설정하기
안드로이드 애플리케이션은 이벤트 기반(event-driven)으로 구동된다. 
-> 애플리케이션이 시작된 후 이벤트 발생을 기다린다.
애플리케이션에서 특정 이벤트를 기다리는 것을 가리켜 그 이벤트를 ‘리스닝한다’고 한다. 이런 이벤트에 응답하기 위해 생성하는 객체를 리스너라한다. 

```java
// 자주 사용하는 버튼에 리스너 설정하기

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstaceStaet);
		setContentView(R.layout.activity_quiz);
		
		mTrueButton = (Button) 	findViewById(R.id.true_button);
		mTrueButton.setOnClickListner(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 이벤트 처리...
			}
		});
	}
}
```


## 토스트
- 사용자에게 뭔가를 알려주지만, 어떤 입력이나 액션도 요구하지 않는 짤막한 메시지

`Toast.makeText(context, "string", duration);`


## 안드로이드 빌드
![](Ch1_%E1%84%8E%E1%85%A5%E1%86%BA_%E1%84%8B%E1%85%A2%E1%84%91%E1%85%B3%E1%86%AF%E1%84%85%E1%85%B5%E1%84%8F%E1%85%A6%E1%84%8B%E1%85%B5%E1%84%89%E1%85%A7%E1%86%AB/470A0D49-90AF-4655-8C3B-B7DB1B96BBB4.png)
![Android Build Process](https://t1.daumcdn.net/cfile/tistory/2445333552801EA131)





#android/실무에바로적용하는안드로이드