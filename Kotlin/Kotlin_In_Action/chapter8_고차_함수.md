ㄴㄴ8장 고차 함수
## 8.1 고차 함수 정의
**고차 함수**
다른 함수를 인자로 받거나 함수를 반환하는 함수
-> 람다나 함수 참조를 인자로 넘기거나 반환하는 함수

### 8.1.1 함수 타입
**함수 타입 문법**
```java
//파라미터 타입    반환 타입
(Int, String) -> Unit
```
그냥 함수를 정의할 땐 Unit 반환 타입을 생략해도 되지만
함수 타입을 선언할 때는 반드시 명시해야 한다.

**Nullable  표시**
```java
// 반환하는 타입이 Nullable인 경우
var canReturnNull: (Int, Int) -> Int? = { x, y => null }

// 함수 타입 전체가 Nullable인 경우
var funOrNull: ((Int, Int) -> Int)? = null
```

### 8.1.2 인자로 받은 함수 호출
**간단한 고차 함수**
```java
fun twoAndThree(operation: (Int, Int) -> Int) {
	val result = operation(2, 3)
	println("The result is $result")
}

>>> twoAndThree { a, b -> a + b }
The result is 5
>>> twoAndThree { a, b -> a * b }
the result is 6
```

### 8.1.3 자바에서 코틀린 함수 타입 사용
함수 타입은 컴파일되면 FunctionN 인터페이스로 구현된다.
인자의 개수에 따라 Function0<R> (인자가 없는 함수), Function1<P1, R> 등의 인터페이스가 제공된다.
각 인터페이스에는 invoke 메소드가 들어있다.

- 자바8 람다를 넘기면 함수 타입 사용하는 코틀린 함수를 자바에서도 호출 가능
```java
fun processTheAnswer(f: (Int) -> Int) {
	println(f(42))
}

// 자바
>>> processTheAnswer(number -> number + 1);
43
```

자바 8 이전은 invoke() 메소드를 구현하는 익명 클래스를 넘겨야 한다.

Unit 타입은 반환값이 존재하므로 자바에서는 그 값을 명시적으로 반환해줘야 한다.
반환 타입이 Unit인 함수를 void를 반환하게 하면 안된다.

### 8.1.4 디폴트 값을 지정한 함수 타입 파라미터나 널이 될 수 있는 함수 타입 파라미터
 함수 타입도 디폴트 값을 정할 수 있다

**함수 타입 파라미터 디폴트 값**
```java
fun <T> Collection<T>.joinToString(
	transform: (T) -> String = { it.toString() } // 디폴트 선언
): String {
	val result = StringBuilder()
	for ((index, element) in this.withIndex()) {
		if (index > 0) result.append(separator)
		result.append(transform(
	}
}
```

Nullable 함수 타입인 경우 함수를 직접 호출할 수 없다
null 여부를 검사해야함

### 8.1.5 함수를 함수에서 반환
프로그램의 상태나 다른 조건에 따라 달라질 수 있는 중복되는 로직은 함수가 함수를 반환함으로써 해결할 수 있다.

**함수를 반환하는 함수**
```java
enum class Delivery { STANDARD, EXPEDITED }

class Order(val itemCount: Int)

fun getShippingCostCalculator(
	delivery: Delivery): (Order) -> Double {
	if (delivery == Delivery.EXPEDITED) {
		return { order -> 6 + 2.1 * order.itemCount } // 람다 반환
	}
	return { order -> 1.2 * order.itemCount } // 람다 반환
}
```

### 8.1.6 람다를 활용한 중복 제거
```java
fun List<SiteVisit>.averageDurationFor(predicate: (SiteVisit) -> Boolean) = 
	filter(predicate).map(SiteVisit::duration).average()

>>> println(log.averageDurationFor {
		it.os in setOf(OS.ANDROID, OS.IOS) })
12.15
>>> println(log.averageDurationFor {
		it.os == OS.IOS && it.path == "/signup" })
8.0
```
람다를 사용하여 중복을 없앨 수 있다.

## 8.2 인라인 함수: 람다의 부가 비용 없애기
코틀린은 보통 람다를 익명 클래스로 컴파일하지만 항상 새로운 클래스가 만들어지지는 않는다. 람다가 변수를 포획할 경우 실행디는 시점마다 새로운 익명 클래스가 생성된다.
이런 경우 부가 비용이 든다.
-> 이런 경우 inline 변경자를 통해 함수가 함수 본문에 포함되는 바이트코드로 바꿔서 해결할 수 있다.

### 8.2.1 인라이닝이 작동하는 방식
```java
inline fun <T> synchronized(lock: Lock, action: () -> T) : T {
	lock.lock()
	try {
		return action()
	}
	finally {
		lock.unlock()
	}
}

val l = Lock()
synchronized(l) {
	// ...
}
```
synchronized 함수의 본문 뿐만 아니라 전달된 람다의 본문도 함께 인라이닝된다.

### 8.2.2 인라인 함수의 한계
람다를 사용하는 모든 함수를 인라이닝 할 수는 없다. 파라미터로 받은 람다를 다른 변수에 저장하고 나중에 그 변수를 사용한다면 람다를 표현하는 객체가 어딘가는 존재해야 하기 때문에 람다를 인라이닝할 수 없다.
단, 인라인 함수의 본문에서 람다 식을 바로 호출하거나 람다 식을 인자로 받아 바로 호출하는 경우에는 인라이닝 가능하다.

**인라인 불가능한 경우**
```java
fun <T, R> Sequence<T>.map(transform: (T) -> R): Sequence<R> {
	return TransformingSequence(this, transform)
}
```
위는 전달받은 함수 값을 호출하지 않고 다른 생성자에게 값을 넘기고 있기 때문에 익명 클래스로 작동한다.(인라이닝 불가능)


noinline 변경자로 인라인을 금지할 수 있다.
```java
inline fun foo(inlined: (0 -> Unit, noinline notinlined: (()> Unit) {
	// ...
}
```

### 8.2.3 컬렉션 연산 인라이닝
중간 연산에서 asSequence로 리스트 대신 시퀀스를 사용할 경우 중간 리스토로 인한 부가 비용은 줄어든다. 하지만 중간 시퀀스는 람다를 필드에 저장하므로 시퀀스는 람다를 인라인하지 않는다. 따라서 지연 계산을 통해 성능을 향상시키려는 이유로 모든 컬렉션 연산에 asSequence를 붙이면 안된다. 컬렉션 크기가 큰 경우에만 시퀀스를 통해 성능 시켜야 한다.


### 8.2.4 함수를 인라인으로 선언해야 하는 경우
inline 키워드는 람다를 인자로 받는 함수만 성능이 좋아질 가능성이 높다.

**람다를 인자로 받는 함수를 인라이닝 할때의 이익**
1. 부가 비용을 없앨 수 있다
	- 함수 호출 비용 하락
	- 람다를 표현하는 클래스와 람다 인스턴스에 해당하는 객체 만들 필요 X
2. 현재의 JVM은 함수 호출과 람다를 인라이닝 해주지 못한다
3. 인라이닝을 통해 일반 람다에서는 사용할 수 없는 기능 사용 가능
	- 예 ) 넌 로컬(non-local)

### 8.2.5 자원 관리를 위해 인라인된 람다 사용
자바의 try-with-resource문은 코틀린의 use 함수로 대체할 수 있다.

**use함수**
- 닫을 수 있는 자원에 대한 확장 함수
- 람다를 인자로 받음
- 예외가 발생할 경우에도 활실히 자원을 닫아줌
- 인라인 함수이다.
```java
fun readFirstLineFromFile(path: String): String {
	BufferedReader(FileReader(path)).use { 
		br -> return br. readLine()
	}
}
```

## 8.3 고차 함수 안에서 흐름 제어
### 8.3.1 람다 안의 return문: 람다를 둘러싼 함수로부터 반환
**non-local return**
람다 안에서 return을 사용시 그 람다로부터만 바노한되는 게 아닐 ㅏ그 람다를 호출하는 함수가 실행을 끝내고 반환된다.
-> 자신을 둘러싸고 있는 블록보다 더 바깥에 있는 블록을 반환 : non-local return

non-local return은 인라인 함수에서만 사용 가능
인라이닝되지 않는 함수는 람다 안에서 return을 사용할 수 없다.


### 8.3.2 람다로부터 반환: 레이블을 사용한 return
label을 사용하여 람다 식에서도 local return 사용 가능
람다 안에서 local return은 for loop의 break와 비슷하다.

**레이블 통해 로컬 리턴 사용**
```java
fun lookForAlice(people: List<Person>) {
	people.forEach label@{ 
		if (it.name == "Alice") return@label // 앞의 레이블 참조
	}
	println("Alice might be somewhere")
}

// 레이블 대신 인라인 함수 이름 사용
fun lookForAlice(people: List<Person>) {
	people.forEach { 
		if (it.name == "Alice") return@forEach
	}
	println("Alice might be somewhere")
}
```


**레이블이 붙은 this**
람다 안에서 this는 묵시적인 컨텍스트 객체(수신 객체)를 가리킨다.
수신 객체 지정 람다 앞에 레이블을 붙인 경우 this 뒤에 그 레이블을 붙여서 묵시적인 컨텍스트 객체를 지정할 수 있다.

```java
>>> println(StringBuilder().apply sb@{
		listOf(1, 2, 3).apply {
			this@sb.append(this.toString()) // 레이블로 바깥 수신객체
		}									  // 접근
	})
[1, 2, 3]
```


**non-local return 단점**
- 장황하다
- 람다 안의 여러 위치에 return 식이 들어가야될 경우 불편
-> 해법 : 무명 함수


### 8.3.3 무명 함수: 기본적으로 로컬 return
**무명 함수**
- 무명 함수는 일반 함수와 달리 함수 이름과 파라미터 타입을 생략할 수 있다.
- 블록이 본문인 무명 함수는 반환타입 명시해야됨
- 식이 본문인 경우 생략 가능
- 람다 식에 대한 문법적 편의일 뿐
```java
fun lookForAlice(people : List<Person>) {
	people.forEach(fun (person) { // 람다 식 대신 무명 함수 사용
		if (person.name == "Alice") return // return은 무명 함수가리킴
		println("${person.name} is not Alice")
	})
)
```

**식이 본문인 무명 함수**
`people.filter(fun (person) = person.age < 30`

**return 규칙**
- return은 fun 키워드를 사용해 정의된 가장 안쪽 함수를 반환시킴
	- 람다는 fun을 사용하지 않으므로 바깥의 fun을 반환함


## 8.4 요약
- 함수 타입을 사용해 함수에 대한 참조를 담는 변수나 파라미터나 반환 값을 만들 수 있다.
- 고차 함수는 다른 함수를 인자로 받거나 함수를 반환한다. 함수의 파라미터 타입이나 반환 타입으로 함수 타입을 사용하면 고차 함수를 선언할 수 있디.
- 인라인 함수를 컴파일할 때 컴파일러는 그 함수의 본문과 그 함수에게 전달된 람다의 본문은 컴파일한 바이트코드를 모든 함수 호출 지점에 삽입해준다. 이렇게 만들어지는 바이트코드는 람다를 활용한 인라인 함수코드를 풀어서 직접 쓴 경우와 비교할 때 아무 부가 비용이 들지 않는다.
- 고차 함수를 사용하면 컴포넌트를 이루는 각 부분의 코드를 더 잘 재사용할 수 있다. 또 고차 함수를 활용해 강력한 제네릭 라이브러리를 만들 수 있다.
- 인라인 함수에서는 람다 안에 있는 return문이 바깥쪽 함수를 반환시키는 넌로컬 return을 사용할 수 있다.
- 무명 함수는 람다 식을 대신할 수 있으며 return식을 처리하는 규칙이 일반 람다 식과는 다르다. 본문 여러 곳에서 return해야 하는 코드 블록을 만들어야한다면 람다 대신 무명 함수를 쓸 수 있다.







#코틀린/Kotlin in Action#