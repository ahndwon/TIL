# 2장 - 코틀린 기초 + 추가 정리
## 2.1 기본 요소: 함수와 변수
### 2.1.1 Hello World

```java
fun main(args : Array<String>) {
	println("Hello World!")
}
```

- 코틀린은 함수를 선언할 때 fun키워드를 사용
- 파라미터 이름 뒤에 그 타입을 쓴다.
- 함수를 최상위 수준에 정의 가능 -> 자바처럼 꼭 클래스 안에 넣을 필요 X
- System.out.prinln -> println
- 세미콜론  X

### 2.1.2 함수
```java
fun max(a: Int, b: Int): Int {
	return if (a > b) a else b
}
```

**문과 식의 구분**
- 문(statement) - 반환 하는 값이 없다.
- 식(expression) - 반환하는 값이 있다.

자바에선 모든 제어 구조가 문.
코틀린은 루프를 제외한 대부분의 제어 구조는 식.

**식이 본문인 함수**
```java
fun max(a: Int, b: Int): Int = if (a > b) a else b
```
식이 본문인 경우 반환 타입을 명시하지 않아도 컴파일러가 알아서 추론.

### 2.1.3 변수
- val (값을 뜻하는 value) - 변경 불가능한(immutable) 참조를 저장하는 변수 -> 자바의 final
- var (변수를 뜻하는 variable) - 변경 가능한(mutable) 참조 -> 자바의 일반 변수

기본적으로 모든 변수를 val로 불변 변수로 선언하다가 필요할 경우 var 로 바꿔서 사용하는 것이 이상적.

### 2.1.4 문자열 템플릿

```java
fun main (args: Array<String>) {
	val name = if (args.size > 0) args[0] else “Kotlin”
	println(“Hello, $name!”)
	//System.out.println("Hello, " + name + "!");
}
```

복잡한 식은 중괄호({})로 둘러싸서 사용
```java
fun main(args: Array<String>) {
	if (args.size > 0) {
		println("Hello, ${args[0]}!")
	}
}
```
 
코틀린은 변수명에 한글을 사용할 수 있으므로 문자열 템플릿을 한글과 같이 사용할 경우 문제가 발생할 수 있음 -> 항상 변수를 중활호로 감싸는것이 좋다.

## 2.2 클래스와 프로퍼티
값 객체 (value object) : 코드가 없이 데이터만 저장하는 클래스

### 2.2.1 프로퍼티
프로퍼티 : 필드 + 접근자
val로 선언한 프로퍼티는 읽기 전용 (불변) 
var 선언한 프로퍼티는 변경 가능

### 2.2.2 커스텀 접근자

```java
class Rectangle(val height: Int, val width: Int) {
	val isSquare: Boolean
		get() {
			return height == width
	}
}
```

### 2.2.3 코틀린 소스코드 구조: 디렉터리와 패키지
java는 패키지와 디렉터리가 일치해야함
kotlin은 상관없다. 하지만 일치시키는게 좋다.

## 2.3 enum, when
### 2.3.1 enum 클래스 정의

```java
enum class Color {
	RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}
```

enum은 코틀린에서 soft keyword이다.
enum 클래스 안에도 프로퍼티나 메소드 정의 가능
상수 선언이 끝나면 반드시 세미콜론 사용해야함.
유일하게 코틀린에서 세미콜론이 필수인 부분


### 2.3.2 when
자바의 switch를 대치함
```java
fun getMnemonic (color: Color) {
	when (color) {
		Color.RED -> "Richard"
		Color.ORANGE -> "Of"
		Color.YELLOW -> "York"
		Color.GREEN -> "Gave"
		Color.BLUE -> "Battle"
		Color.INDIGO -> "In"
		Color.VIOLET -> "Vain"
	}
}
```

break가 필요없음
분기로 여러 값 사용 가능

### 2.3.3 when과 임의의 객체 사용
코틀린의  when은 자바의 switch문과 달리 상수 뿐만 아니라 객체를 분기 조건으로 사용 가능

```java
fun mix(c1: Color, c2: Color) =
	when (setOf(c1, c2)) {
		setOf(RED, YELLOW) -> ORANGE
		setOf(YELLOW, BLUE) -> GREEN
		setOf(BLUE, VIOLET) -> INDIGO
		else -> throw Exception("Dirty color")
}
```


### 2.3.4 인자 없는 when 사용

인자 없이 when을 사용하기 위해선 분기의 조건이 불리언 결과를 계산하는 식이어야 한다.


### 2.3.5 스마트 캐스트: 타입 검사와 타입 캐스트를 조합

- is - 변수를 원하는 타입으로 선언된 것처럼 사용하게 해줌
- java의 instanceof와 비슷하지만 명시적으로 변수 타입을 캐스팅 할 필요 없다.
- 프로퍼티는 반드시 val이어야 한다.

```java
int eval(Expr e) {
	if (e instanceof Num) {
		Num n = (Num) e;
		return n.getValue;
	}
}
```


```java
fun eval (e: Expr): Int {
	if (e is Num) {
		// val n = e as Num
		val n = e
		return n.value
	}
}
```

- as - 명시적으로 타입 캐스팅 하기 위한 키워드


### 2.3.6 리팩토링: if를 when으로 변경
**if**
```java
fun eval(e: Expr) : Int = 
	if (e is Num) {
		e.value
	} else if (e is Sum) {
		eval(e.right) + eval(e.left)
	} else {
		throw IllegalArgumentException("Unknown expression")
	}
}
```

**when**
```java
fun eval (e: Expr) : Int = 
	when (e) {		
		is Num ->
			e.value
		is Sum ->
			eval(e.right) + eval(e.left)
		else ->
			throw IllegalArgumentException("Unknown expression")
	}
}
```

## 2.4 while, for loop
### 2.4.1 while loop
- while
- do-while

### 2.4.2 for loop
- range -  폐구간 , 양끝을 포함하는 구간
- downTo - 역방향 수열

### 2.4.3 맵에 대한 이터레이션

```java
HashMap<Char, String> binaryReps = new HashMap<Char, String>();

ArrayList<Char> keys = new ArrayList<>(binaryReps.keySet());

for (Char key : keys) {
	System.out.println(key + "=" + binaryReps.get(key));
}
```


```java
// 코틀린
val binaryReps = TreeMap<Char, String> ()

for ((letter, binary) in binaryReps) {
	println("$letter = $binary")
}
```

### 2.4.4 in
- in - 어떤 값이 범위에 속하는지 검사

##  코틀린의 예외 처리
- try, catch, finally
- 자바와 차이점 : throws절이 코드에 없다. 자바는 체크 예외를 명시적으로 처리해야 되기 때문
- 하지만 코틀린은 체크 예외와 언체크 예외를 구별하지 않는다.

- 코틀린은 try-with-resource는 없다.

- try도 식으로 이용 가능


## 2.6 요약
- 함수를 정의 할 땐 fun키워드 사용,  val과 var는 각각 읽기 전용 변수와 변경 가능한 변수 선언할 때 사용.
- 문자열 템플릿을 사용하면 문자열을 연결하지 않아도 되므로 코드가 간결해짐
- if는 코틀린에서 식이다. -> 값을 만들어냄
- 어떤 변수의 타입을 검사하고 나면 명시적으로 캐스팅하지아 않아도 검사한 타입의 변수처럼 사용 가능 -> 스마트 캐스트
- 코틀린의 예외 처리는 java와 다르게 함수가 던질 수 있는 예외를 선언하지 않아도 된다.



## 코틀린 추가 정리
### 코틀린 숫자 비트 크기
![](2%E1%84%8C%E1%85%A1%E1%86%BC%20-%20%E1%84%8F%E1%85%A9%E1%84%90%E1%85%B3%E1%86%AF%E1%84%85%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%8E%E1%85%A9%20+%20%E1%84%8E%E1%85%AE%E1%84%80%E1%85%A1%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5/EE044644-D469-4CDB-BDB7-6C14392F80C7.png)


### 언더바로 큰 숫자 표현
```java
val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010
```

프린트하면 언더바 없이 출력된다

![](2%E1%84%8C%E1%85%A1%E1%86%BC%20-%20%E1%84%8F%E1%85%A9%E1%84%90%E1%85%B3%E1%86%AF%E1%84%85%E1%85%B5%E1%86%AB%20%E1%84%80%E1%85%B5%E1%84%8E%E1%85%A9%20+%20%E1%84%8E%E1%85%AE%E1%84%80%E1%85%A1%20%E1%84%8C%E1%85%A5%E1%86%BC%E1%84%85%E1%85%B5/1788A339-307A-400A-8AAE-46CE472E8105.png)


### 코틀린 원시타입
코틀린은 원시타입과 wrapper type을 구분하지 않는다
자바처럼 Integer와 int로 구분되지 않고
코틀린은 Int 하나만 존재

-> 코틀린의 타입은 컴피일시 자바의 primitive 또는 wrapper 타입으로 자동 변환함

Generic이나 Collection에서는 Integer로 변경되고 나머지 부분에서는 Int로 변경됨

**null이 가능한 타입은 primitive type으로 변환 불가**
-> Integer type으로 변환됨

**숫자 변환**
코틀린은 자동으로 숫자 변환을 해주지 않는다.
ex) Int값을 Long에 그냥 대입하면 컴파일 에러 발생
-> 변환 함수 사용 : toByte(), toShort(), toChar(), toLong(), toInt()..등

### Any, Any? 최상위 타입
코틀린에는 Object 타입이 없고 Any, Any? typeㅣ 존재
(Any 타입은 컴파일시 Object로 변환됨)
또한 자바와 달리 Int, Boolean 등 원시타입의 상위 객체이기도 함

null이 들어가는 값은 Any? 타입을 사용

자바의 Object는 toString(), equals(), hashCode() 이외에 wait(), notify()를 지원 하지만 Any는 wait()와 notify()는 지원X

### Unit Type
자바의 void 와 같은 역할
차이점 : void는 리턴값이 없음을 나타내지만 Unit type은 인자로도 사용 가능

```java
interface ShapeArea<T> {}
	fun calc() : T
}

calss NoResultProcessor : ShapeArea<Unit> {
	override fun calc() {
		println("36")
	}
}
```

Generic Type 으로 Unit을 지정하면 return을 명시적으로 넣지 않아도 된다.  자바는 Void 사용 가능 (안드로이드의 AsyncTask)하지만 return을 반드시 넣어야 된다.

### Nothing Type
함수가 정상적으로 끝나지 않는다라는걸 명시적으로 표현하는 함수

중간에 예외를 던지는 함수에 반환 타입을 Nothing으로 해두면 컴파일러가 정상종료 되지 않는 함수임을 미리 알 수 있다.

```java
fun fail(message: String): Nothing {
	throw IllegalStateException(message)
}

fun main(args: Array) {
	fail("Error occured")
}
```

Nothing은 return 타입이나, 인자로만 쓸 수 있다.
Nothing은 아무값도 저장할 수 없으므로 변수로 사용이 불가

사용법
```java
fun getCompany(person: Person) {
	val comp = Person.company ?: fail("No company info.")
	return comp.name
}
```
company 정보가 없으면 throw가 발생함.
-> 컴파일러는 comp.name 라인에서 null check없이 값을 호출하도록 용인해 줌.

- Any는 모든 타입의 슈퍼클래스 <=> Nothing은 모든 타입의 서브클래스 


#코틀린/Kotlin in Action#