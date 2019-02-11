# Ch7_Function
스위프트에서 함수는 일급 객체 -> 함수형 프로그래밍

## 함수와 메서드
함수가 위치하거나 사용되는 범위 등에 따라 호칭이 달라지지만 함수라는 것 자체엔 변함이 없음

* **메서드** - 구조체, 클래스, 열거형 등 특정 타입에 연관되어 사용하는 함수
* **함수** - 모듈 전체에서 전역적으로 사용할 수 있는 함수


## 함수의 정의와 호출
조건문, 반복문과 달리 소활호 ‘()’는 생략 불가
스위프트의 함수는 오버라이드, 오버로드 모두 지원함


**기본적인 함수 형태**
```swift
func 함수 이름( 매개변수 ) -> 반환 타입 {
	실행 구문
	return 반환값
}
```


```swift
func hello(name: String) -> String {
	return "Hello \(name)!"
}

let helloJenny: String = hello(name: "Jenny")
print(helloJenny)		// Hello Jenny!
```



### 매개변수 이름과 전달인자 레이블
전달인자 레이블을 별도로 지정하면 함수 외부에서 매개변수의 역할을 좀 더 명확히 할 수 있다.
전달인자 레이블은 함수 정의에서 매개변수 이름 앞에 한 칸을 띄운 후 전달인자 레이블 지정

**전달인자 예**
```swift
func sayHello(from myName:String, to name:String) -> String {
	return "Hello \(name)! I'm \(myName)"
}

print(sayHello(from: "yagom", to: "Jenny")) // Hello Jenny! I'm yagom
```


전달인자 레이블을 변경하면 함수의 이름 자체가 변경되므로 매개변수 이름과 타입이 같은 함수를 전달인자 레이블만 바꿔서 중복 정의 가능

```swift
func sayHello(to name:String, _ times: Int) -> String {
	// 실행 코드
}

func sayHello(from myName:String, repeatCount times: Int) -> String {
	// 실행 코드
}

print(sayHello(to: "yagom", 2))
print(sayHello(to: "yagom", repeatCount: 2))
```



### 매개변수 기본값
매개변수마다 기본값을 지정할 수 있다. -> 매개변수가 전달되지 않으면 기본값을 사용
기본값이 없는 매개변수는 대체로 중요도가 높기 때문에 기본값이 있는 매개변수 보다 앞에 배치.

**print 함수의 매개변수 기본값이 정해져있음**
`public func print(_ items: Swift.Any..., separator: String = default, terminator: String = default)`


### 가변 매개변수와 입출력 매개변수
**가변 매개변수** 
* 0개 이상(0개 포함)의 값을 받아올수 있고,
*  들어온 인자값은 배열처럼 사용 가능
* 함수마다 가변 매개변수 하나만 가질 수 있음.


```swift
func sayHelloToFirends(me: String, friends names: String...) -> String {
	var result: String = ""
	
	for friend in names {
		result += "Hello \(friend)!" + "
	}
	
	result += "I'm " + me + "!"
	return result
}

print(sayHelloToFriends(me: "yagom", friends: "Johansson", "Jay", "Wizplan"))
// Hello Johansson! Hello Jay! Hello Wizplan! I'm yagom!

print(sayHelloToFriends(me: "yagom"))
// I'm yagom!
```


함수의 전달인자로 값을 전달할 때는 보통 값을 복사해서 전달함.
값이 아닌 참조를 전달하려면 입출력 매개변수를 사용. -> 사이드 이펙트를 지양하는 함수형 프로그래밍 패러다임에선 지양함.

**입출력 매개변수의 전달 순서**
	1. 함수를 호출할 때, 전달인자의 값을 복사
	2. 해당 전달인자의 값을 변경하면 1에서 복사한 것을 함수내부에서 변경
	3. 함수를 반환하는 시점에 2에서 변경된 값을 원래의 매개변수에 할당함.

참조는 inout 매개변수로 전달될 변수 or 상수 앞에 앰퍼샌드(&)을 붙여서 표현

**inout**
```swift
var numbers: [Int] = [1, 2, 3]

func nonReferenceParameter(_ arr: [Int]) {
	var copiedArr: [Int] = arr
	copiedArr[1] = 1
}

func referenceParameter(_ arr: inout [Int]) {
	arr[1] = 1
}

nonReferenceParameter(numbers)
print(numbers[1])		// 2

referenceParameter(&numbers)
print(numers[1])		// 1
```

**입출력 매개변수 특성**
* 기본값 X
* 가변 매개변수로 사용 X
* 상수는 변경불가이므로 전달인자로 사용 X



## 함수 반환 타입
함수는 특정 연산을 실행한 후 결괏값 반환
반환 값이 없으면 반환 타임을 Void로 하거나 생략하여 반환 타입이 없음을 표시


### 데이터 타입으로서의 함수
스위프트의 함수는 일급 객체이므로 하나의 데이터 타입으로 사용 가능

**함수의 데이터 타입 형식**
`(매개변수 타입의 나열) -> 반환 타입`

`(Void) -> Void`의 다른 표현 형태
* `() -> Void`
* `() -> ()`

전달인자 레이블은 함수 타입의 구성요소가 아니므로 함수 타입을 작성할 때는 전달인자 레이블을 써줄 수 없다.


## 중첩 함수
함수 안의 함수로 구현된 중첩 함수는 상위 함수의 몸통 블록 내부에서만 사용가능.
중첩 함수를 담은 함수가 중첩 함수를 반환하면 밖에서도 사용 가능

```swift
typealis MoveFunc = (Int) -> Int

func functionForMove(_ shouldGoLeft: Bool) -> MoveFunc {
	func goRight(_ currentPosition: Int) -> Int {
		return currentPosition + 1
	}
	
	func goLeft(_ currentPosition: Int) -> Int {
		return currentPosition - 1
	}
	
	return shouldGoLeft ? goLeft : goRight
}

```


## 종료되지 않는 함수
* 종료되지 않는다 -> 정상적으로 끝나지 않는 함수
* 비반환 함수 or 메서드라고 함
 (Nonreturning Function)
* 비반환 함수를 실행하면 프로세스 동작은 끝났다고 볼 수 있다
* 비반환 함수는 오류를 던진다던가, 중대한 시스템의 오류를 보고하고 프로세스를 종료함
* 비반환 함수는 어디서든 호출이 가능하고 guard 구문의 else 블록에서도 호출 가능
* 비반호나 메서드는 재정의는 가능하지만 타입 변경은 불가
* 비반환 함수는 반환타임을 Never로 명시


## 반환값 무시 가능한 함수
@discardableResult 선언 속성을 사용하여 반환 값을 무시해도 됨을 명시

```swift
@discardableResult func discardableResultSay(_ something: String) -> String {
	print(something)
	return something
}

```










#Swift/Swift_Programming