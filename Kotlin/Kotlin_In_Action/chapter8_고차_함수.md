# 8장 고차 함수
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

noinline 변경자로 인라인을 금지할 수 있다.
```java
inline fun foo(inlined: (0 -> Unit, noinline notinlined: (()> Unit) {
	// ...
}
```


#코틀린/Kotlin in Action#