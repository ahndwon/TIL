# Ch7_프래그먼트
## UI 유연성의 필요
 리스트와 그에대한 디테일을 폰에서 보여주기 위해선 각각 별도의 액티비티를 통해 생성하여 보여주면 된다. 하지만 더 큰 화면을 가진 태블릿에선 한 화면에 다 보여주는 것이 더 효율적이므로 이런 것들을 고려하여 UI를 유연한게 할 필요가 있는데 액티비티는 특정 화면과 강하게 결속하기에 이런 유연함을 제공하지 못한다.


## 프래그먼트 개요
액티비티에서 하나 이상의 프래그먼트로 앱의 UI를 관리하면 안드로이드의 기본 원칙(하나의 액티비티가 하나의 뷰와 결속됨)을 피해서 유연성을 더 좋게 할 수 있다.

프래그먼트는 액티비티의 작업 수행을 대행할 수 있는 컨트롤러 객체다. 

사용자 인터페이스를 관리하는 프래그먼트를 UI 프래그먼트라 한다. UI 프래그먼트는 자신의 뷰를 하나 가지며, 그 뷰는 레이아웃 팔로부터 인플레이트된다.

액티비티의 뷰는 하나 또는 여러 개의 프래그먼트 뷰들을 가질 수 있다.
프래그먼트는 또한 액티비티의 생명 주기에 결속된다.

UI의 유연성을 성취하려면 비용이 든다. -> 코드가 더 복잡해지고 많아진다.


## 지원 라이브러리
프래그먼트는 안드로이드 태블릿과 함께 API 11 에서 등장했지만 `Support library`를 사용하면  API 레벨 4 부터 사용할 수 있다.

라이브러리를 추가하기 위해선 앱 수준의 build.gradle에 `implementation 'android.support:support-v4:23.4.0'` 를 추가해주면 된다. 

의존성 문자열은  groupId:artifactId:version 으로 구성된 메이븐 형식을 사용한다.
메이븐은 의존성 관리 도구이다.


## UI 프래그먼트의 호스팅
* 액티비티 자신의 레이아웃에 프래그먼트의 뷰를 넣을 위치를 정의해야 한다.
* 프래그먼트 인스턴스의 생명주기를 관리해야 한다.

### 프래그먼트 생명주기
액티비티의 생명주기와 유사하다.
중단, 일시 중지, 실행 상태를 갖는다.
![](Ch7_%E1%84%91%E1%85%B3%E1%84%85%E1%85%A2%E1%84%80%E1%85%B3%E1%84%86%E1%85%A5%E1%86%AB%E1%84%90%E1%85%B3/B87D995B-EB5E-4277-94AD-5CA6E41ED4DE.png)
![fragment lifecycle](https://i.stack.imgur.com/fRxIQ.png)

### 호스팅의 두 가지 방법
* 프래그먼트를 액티비티의 레이아웃에 정적으로 추가한다.
* 프래그먼트를 액티비티의 코드에 동적으로 추가한다.

첫번째 방법은 프래그먼트의 뷰가 액티빝의 뷰에 고정되어서 교체가 불가능하다.
동적으로 프래그먼트를 추가해야 런타임에 프래그먼트를 제어할 수 있다.


## 프래그먼트 생명주기 메서드 구현
### onCreateView(…)
```java
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View v = inflater.inflate(R.layout.fragment_crime, container, false);
	return v;
}
```
내부에서 레이아웃 리소스 ID를 인자로 전달하여 LayoutInflater.inflate(...)를 호출함으로써 프래그먼트의 뷰를 명시적으로 인플레이트 해줘야한다. 두 번째 인자는 위젯들을 올바르게 구성하기 위해 필요한 뷰의 부모다. 세 번째 인자는 인플레이트된 뷰를 뷰의 부모에게 추가할 것인지를 LayoutInflater에 알려준다.

프래그먼트에서 뷰의 객체 참조를 얻기 위해선 액티비티와 달리 View.findViewById(int)를 호출해야 한다. Activity.findViewById(int) 메서드는 내부적으로 View.findViewById(int)를 호출하는 Convenience 메서드이다.
`mTitleField = (EditText) v.findViewById(R.id.crime_title);`


## UI Fragment를 FragmentManager에 추가하기
**FragmentManager** - 프래그먼트를 관리하고 그것의 뷰를 액티비티의 뷰 계층에 추가하는 책임을 갖는다.








#android/실무에바로적용하는안드로이드