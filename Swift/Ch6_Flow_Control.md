# Ch6_Flow_Control
스위프트의 흐름 제어 구문에서는 소괄호 ‘()’를 대부분 생략 가능
중괄호 ‘{}’ 는 생략 불가

## 6.1 조건문
### 6.1.1 if 구문
If, else 사용 가능
스위프트의 if 구문은 조건의 값이 **꼭 Bool 타입**이어야 한다.

**If 구문 기본 구현**
```swift
let first: Int = 5
let second: Int = 7

if first > second {
	print("first > second")
} else if first < second {
	print("first < second")
} else {
	print("first == second")
}

// 결과 "first < second"

```



### 6.1.2 switch 구문
Break 사용이 선택 사항.
-> Case 내부의 코드를 모두 실행하면 break 없이도 switch 구문이 종료됨

만약 switch 구문의 case 연속 실행하고 싶은 경우 -> `fallthrough` 키워드를 사용
C랑 다르게 case 조건으로 정수 말고 다른 타입들도 허용된다.
조건으로 **범위**도 사용 가능
부동소수 타입도 사용 가능

**switch 구문 기본 구현**
```swift
let integerValue: Int = 5

switch integerValue {
case 0:
	print("Value == zero")
case 1...10:
	print("Value == 1~10")
	fallthrough
case Int.min..<0, 101..<Int.max:
	print("Value < 0 or Value > 100")
	break
default:
	print("10 < Value <= 100")
}

// 결과
// Value == 1~10
// Value < 0 or Value > 100
```

case 조건문 다음엔 무조건 실행 가능한 코드가 위치해야 한다
없다면 오류 발생함.
`fallthrough` 를 통해 해결 가능

case 조건문엔 와일드카드 식별자 ‘_’ 도 사용 가능

**와일드카드 식별자를 사용한 튜플 switch case**
```swift
typealias NameAge = (name: String, age: Int)

let tupleValue: NameAge = ("yagom", 99)

switch tupleValue {
	case ("yagom", 50):
		print("정확히 맞췄습니다!")
	case ("yagom", _):
		print("이름만 맞았습니다. 나이는 \(tupleValue.age)입니다.")
	case (_, 99):
		print("나이만 맞았습니다. 이름은 \(tupleValue.name)입니다.")
	default:
		print("누굴 찾나요?")
}

// 이름만 맞았습니다.
```


**값 바인딩을 사용한 튜플 switch case**
```swift
typealias NameAge = (name: String, age: Int)

let tupleValue: NameAge = ("yagom", 99)

switch tupleValue {
	case ("yagom", 50):
		print("정확히 맞췄습니다!")
	case ("yagom", let age):
		print("이름만 맞았습니다. 나이는 \(age)입니다.")
	case (let name, 99):
		print("나이만 맞았습니다. 이름은 \(name)입니다.")
	default:
		print("누굴 찾나요?")
}

// 이름만 맞았습니다.
```


**`where` 키워드로 case의 조건을 확장 가능**
```swift
let 직급: String = "사원"
let 연차: Int = 1
let 인턴인가: Bool = false

switch 직급 {
	case "사원" where 인턴인가 == true:
		print("인턴입니다.")
	case "사원" where 연차 < 2 && 인턴인가 == false:
		print("신입사원입니다.")
	case "사원" where 연차 > 5:
		print("연식 좀 된 사원입니다.")
	case "사원":
		print("사원입니다.")
	case "대리":
		print("대리입니다.")
	default:
		print("사장입니까?")
}

// 신입사원입니다.
```


**열거형을 입력 값으로 받는 switch 구문**
```swift
enum School {
	case primary, elementary, middle, high, college, university, graduate
}

let 최종학력: School = School.university

switch 최종학력 {
case. primary:
	print("최종학력은 유치원입니다.")
case .elementary:
	print("최종학력은 초등학교입니다.")
case .middle:
	print("최종학력은 중학교입니다.")
case .high:
	print("최종학력은 고등학교입니다.")
case .college, .university:
	print("최종학력은 대학(교)입니다.")
case .graduate:
	print("최종학력은 대학원입니다.")
}

// 최종학력은 대학(교)입니다.

```


## 6.2 반복문
swift에선 전통적인 C 스타일의 for 구문이 사라졌고
조건에 괄호를 생략할 수 있다.

### 6.2.1 for-in 구문
Continue, break 제어 키워드 사용 가능
```swift
for 임시 상수 in 시퀀스 아이템 {
	실행 코드
}
```


### 6.2.2 while 구문
Continue, break 제어 키워드 사용 가능

```swift
var names: [String] = ["Joker", "Jenny", "Nova", "yagom"]

while names.isEmpty == false {
	print("Good bye \(names.removeFirst())")
}

// Good bye Joker
// Good bye Jenny
// Good bye Nova
// Good bye yagom
```


### 6.2.3 repeat-while 구문
다른 프로그래밍 언어의 do-while 구문과 비슷
Repeat 블록의 코드를 최초 1회 실행 후
While 다음의 조건이 성립하면 블록 내부의 코드 반복 실행

```swift
var names: [String] = ["Joker", "Jenny", "Nova", "yagom"]

repeat {
	print("Good bye \(names.removeFirst())")
} while names.isEmpty == false 

// Good bye Joker
// Good bye Jenny
// Good bye Nova
// Good bye yagom
```


## 6.3 구문 이름표
지정된 구문을 제어하고자 할 때 제어 키워드와 구문 이름을 함께 써주면 된다.

```swift
var numbers: [Int] = [3, 2342, 6, 3252]

numbersLoop: for num in numbers {
	if num > 5 || num < 1 {
		continue numbersLoop
	}
	
	var count: Int = 0
	
	printLoop: while true {
		print(num)
		count += 1

		if count == num {
			break printLoop
		}
	}

	removeLoop: while true {
		if numbers.first != num {
			break numbersLoop
		}
		numbers.removeFirst()
	}
}

// 3
// 3
// 3
// numbers에는 [2342, 6, 3252]가 남는다.
```












#Swift/Swift_Programming