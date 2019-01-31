# Ch4_Data_Type_Advanced
## 4.1 데이터 타입 안심
스위프트의 안정성이 가장 뚜렷하게 나타나는 부분
스위프트는 타입에 굉장히 민감하고 엄격함
**서로 다른 타입 끼리의 데이터 교환은 꼭 타입캐스팅을 거쳐야함**

스위프트에서 값 타입의 데이터 교환은 타입캐스팅이 아닌 **새로운 인스턴스를 생성**하여 할당하는 것


### 4.1.1 데이터 타입 안심
스위프트는 데이터 타입을 안심하고 사용할 수 있는 Type Safe 언어
컴파일 시 타입을 확인해줌


## 4.1.2 타입 추론
```swift
// 타입을 지정하지 않았으나 타입 추론을 통하여 name은 String 타입으로 선언됨
let name = "Kwanhee"

// 앞서 타입 추론에 의해 name은 String 타입의 변수로 지정되었기 때문에
// 정수를 할당하려고 시도하면 오류가 발생한다.
name = 100
```


## 4.2 타입 별칭
이미 존재하는 데이터 타입에 임의로 다른 이름을 부여할 수 있다

Typealias MyInt = Int
-> MyInt는 Int의 또 다른 이름


## 4.3 튜플
**튜플** - 타입의 이름이 따로 지정되어 있지 않은 프로그래머 마음대로 만드는 타입, **지정된 데이터의 묶음**
C 언어의 원시 구조체의 형태와 가까움

스위프트의 튜플은 파이썬의 튜플과 유사함. 
튜플은 타입 이름이 따로 없으므로 일정 타입의 나열만으로 튜플 타입을 생성해줄 수 있다.

긴 튜플 타입을 모두 쓰는게 귀찮으면 Type alias로 단축 가능


## 4.4 컬렉션 타입
컬렉셔 타입 - 배열, 딕셔너리, 세트 등

### 4.4.1 배열
같은 타입의 데이터를 일렬로 나열한 후 순서대로 저장하는 형태의 컬렉션 타입
* let 키워드를 사용해 상수로 선언하면 변경할 수 없는 배열이 된다.
* var 키워드로 선언해주면 변경 가능한 배열이 된다.
* 스위프트의 Array는 C언어의 배열처럼 버퍼. 단 C언어처럼 한 번 선언하면 크기가 고정되는 버퍼가 아니라 필요에 따라 자동으로 버퍼의 크기를 조절해줌. -> Java의. ArrayList와 비슷

### 4.4.2 딕셔너리
키와 쌍의 값으로 구성되는 컬렉션

```swift
// typealias를 통해 더 단순하게 표현 가능
typealias StringIntDictionary = [String: Int]

// 키는 String, 값은 Int 타입인 빈 딕셔너리를 생성한다ㅣ
var numberForName: Dictionary<String, Int> = Dictionary<String, Int>()

// 위와 같은 식
var numberForName: [String:Int] = [String:Int]()

// 위와 같은 식
var numberForName: StringINtDictionary = StringIntDictionary()

// 위와 같은 식
var numberForName: [String: Int] = [:]

// 위와 같은 식
var numberForName: [String: Int] = ["yagom": 100, "chulsoo": 200, "jenny": 300]
```


### 4.4.3 세트
같은 타입의 데이터를 순서 없이 하나의 묶음으로 저장하는 형태의 컬렉션 타입
* **순서가 중요하지 않거나 각 요소가 유일한 값이어야 하는 경우 사용**
* **해시 가능한 값이 들어와야됨**
* 배열과 달리 줄여서 표현하는 축약형 X

```swift
let englishClassStudents: Set<String> = ["john", "chulsoo", "yagom"]
```


## 4.5 열거형
연관된 항목들을 묶어서 표현할 수 있는 타입
스위프트의 열거형은 각 열거형이 고유의 타입으로 인정됨. (C 언어처럼 정수형 X)
연관 값(Associated Value)을 사용하여 다른 언어에서 공용체라고 불리는 값의 묶음으로 구현 가능

**사용처**
* 제한된 선택지를 주고 싶을 떄
* 정해진 값 외에는 입력받고 싶지 않을 때
* 예상된 입력 값이 한정되어 있을 때


```swift
enum School {
	case primar, elementary, middle, high, college, university, graduate
}

var hightestEducationLevel: School = School.university

// 위 코드와 정확히 같은 표현
var highestEducationLevel: School = .university

// 같은 타입인 School 내부의 항목으로만 highestEducationLevel의 값을 변경해줄 수 있다.
highestEducationLevel = .graduate

```


### 4.5.2 원시 값
열거형의 각 항목은 자체로도 하나의 값이지만 항목의 **원시 값(Raw Value)**도 가질 수 있음

```swift
enum WeekDays: Character {
	case mon = "월", tue = "화", wed = "수", thu = "목", fri = "금", sat = "토", sun = "일"
}
```

일부만 원시 값을 주면 나머지는 Swift가 알아서 자동으로 원시값을 할당


### 4.5.3 연관 값
열거형 각 항목이 연관 값을 가지게 되면, 기존 프로그래밍 언어의 공용체 형태를 띌 수 있다.
다른 항목이 연관 값을 갖는다고 모든 항목이 연관 갑을 가질 필요는 없다.


### 4.5.4 순환 열거형
순환 열거형은 열거형 항목의 연관 값이 자신의 값이고자 할 때 사용함. 
순환 열거형을 명식하고 싶으면 indirect 키워드를 사용

```swift
enum ArithmeticExpression {
	case number(Int)
	indirect case addition(ArithmeticExpression, ArithmeticExpression)
	indirect case multiplication(ArithmeticExpression, ArithmeticExpression)
}
```













#Swift