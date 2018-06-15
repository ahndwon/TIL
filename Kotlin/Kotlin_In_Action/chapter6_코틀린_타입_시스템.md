#  6장 코틀린 타입 시스템
## 6.1 널 가능성(Nullability)
NullPointerException을 피할 수 있게 돕기 위한 코틀린 타입 시스템의 특성

### 6.1.1 널이 될 수 있는 타입
널이 될 수 있는 변수는  NullPointerException이 발생할 수 있으므로 안전하지 않다.
코틀린은 그런 메소드 호출을 금지함으로써 오류를 방지함

```java
// NullPointerException이 발생할 수 있는 불안전한 자바 함수
int strLen(String s) {
	return s.length(); // s가 null일 경우 오류 발생
}

// NullPointerException에 대해 안전한 코틀린의 메소드
fun strLen(s: String) = s.length
```

**?(Nullable)**
코틀린은 변수에 Null을 넣기 위해선 Nullable 타입을 사용해야한다. 타입명 뒤에 **?**를 붙이면 된다.

단점
- `변수.메소드()` 처럼 메소드를 직접 호출 할 수 없다.
- 널이 될 수 있는 값을 널이 될 수 없는 타입의 변수에 대입 할 수 없다.
- 널이 될 수 있는 타입의 값을 널이 될 수 없는 타입의 파리미터를 받는 함수에 전달 할 수 없다.

### 6.1.2 타입의 의미
자바의 타입 시스템은 널을 제대로 다루지 못한다.
-> 변수에 null이 들어있는 경우 변수의 타입 클래스에 정의된 메소드들을 활용할 수 없다.
(애노테이션으로 극복 가능)

자바는 null check를 위한 Optional 타입을 사용시 코드가 지저분해지고 실행 시점에 성능이 저하된다.

코틀린은 Nullable 타입이나 Not-Nullable 타입이나 모두 같은 객체이기 때문에 성능의 저하가 없다.

### 6.1.3 안전한 호출 연산자: ?.
- ?. - null 검사와 메소드 호출을 한 번의 연산으로 수행한다.
```java
// 위와 아래는 같다
s?.toUpperCase()
if (s != null) s.toUpperCase() else null
```

### 6.1.4 엘비스 연산자: ?:
- 좌항이 null이면 우항을 반환함
```java
fun foo(s: String?) {
	val t: String = s ?: "" // s가 null이면 결과는 빈 문자열
}
```

코틀린은 return이나 throw 등의 연산도 식이다.
-> 우항에 return, throw 등의 연산을 넣을 수 있다.

### 6.1.5 안전한 캐스트: as?
코틀린 타입 캐스트 연산자 : as
- 코틀린도 자바와 마찬가지로 as로 지정한 타입을 바꿀 수 없으면 ClassCastException이 발생한다.
- is를 통해 미리 as로 변환 가능한 타입인지 검사해볼 수 있지만  as?로 더 간결하고 안전하게 해결 가능

- as? : 어떤 값을 지정한 타입으로 캐스트함. 변환 할 수 없는 경우 null을 반환함.


### 6.1.6 널 아님 단언: !!
- 널 아님 단언(not-null assertion)  **!!** - 어떤 값이든 널이 될 수 없는 타입으로 (강제로) 바꾼다.
- 널이 발생시 NullPointerException이 발생한다.
- 널포인터익셉션 발생시 널값을 사용하는 코드 부분이 아닌 단언문이 위치하는 곳을 가리킨다.
- 가능하면 단언문을 피하는 것이 좋다.
- 단, 다른 함수에서 널이 아닌값을 전달받는다는 것이 분명하고 불필요한 널검사가 계속들어가는 환경에선 단언문이 효과적이다.
- !! 단언문을 한 줄에 함께 쓰는 것은 좋지 않다.
	- stack trace는 어떤 식에서 예외가 발생했는지 알려주지 않는다.


### 6.1.7 let 함수
- Nullable한 값을 Non-nullable만 인자로 받는 함수에 넘길 때 사용.
- let은 자신의 수신 객체를 인자로 전달받은 람다에게 넘긴다.
- let은 널이 아닐 때에만 호출된다.
```java
fun sendEmailTo(email: String) {
	println("Sending email to $email")
}

>>> var email: String? = "yole@example.com"
>>> email?.let { sendEmailTo(it) }
```

- 여러 값이 null일 경우 let을 중첩시켜서 해결할 수 있으나 if를 사용하는 것이 더 깔끔하고 낫다.
```java
variable?.let { //... }  // mutable 프로퍼티에 대해 안전하다

or

if (variable != null) { //... }  // mutable 프로퍼티에 대해 안전하지 못하다
```

### 6.1.8 나중에 초기화할 프로퍼티
- lateinit : Nullable을 사용하지 않고 나중에 프로퍼티를 초기화하기 위해 사용
		-  프로퍼티는 항상 var 여야 한다.
		- val 프로퍼티는 final 필드로 컴파일되며, 생성자 안에서 반드시 초기화해야 한다.
		- 초기화하지 않은 상태로 접근하면 lateinit property not initialized  예외가 발생한다.

### 6.1.9 널이 될 수 있는 타입 확장
- 널이 될 수 있는 타입에 대한 확장 함수를 정의하면 변수가 null이 없다고 보장하는 대신, 직접 변수에 대해 메소드를 호출해도 확장 함수인 메소드가 알아서 널을 처리해준다.
- 코틀린에서는 널이 될 수 있는 타입의 확장 함수 안에서는 this가 널이 될 수 있다는 점이 자바와 다르다.

### 6.1.10 타입 파라미터와 널 가능성
- 코틀린에서 함수나 클래스의 모든 타입 파라미터는 기본적으로 널이 될 수 있다. 
- 널이 될수 있는 타입을 포함하는 어떤 타입이라도 타입 파라미터를 대신할 수 있다.
- 따라서 타입 파라미터 T를 클래스나 함수 안에서 타입 이름으로 사용하면 이름 끝에 물음표가 없더라도 T가 널이 될 수 있는 타입이다.

```java
fun <T> printHashCode(t: T) {
	println(t?.hashCode()) // t가 널이 될 수 있으므로 안전한 호출해야함
}

>>> printHashCode(null) // "T"의 타입은 "Any?"로 추론됨
null

fun <T: Any> printHasHCode(t: T) { // T는 널이 될 수 없다
	println(t.hashCode())
}

>>> printHashCode(null)
Error: Type parameter bound for 'T' is not satisfied
```

### 6.1.11 널 가능성과 자바
* 자바 @Nullable == 코틀린 ?
- @Nullabe String == String?
- @NotNull String == String

**플랫폼 타입**
- 널 가능성 애노테이션이 없는 자바의 소스코드는 타입이 코틀린의 플랫폼 타입이 된다.
- 코틀린이 널 관련 정보를 알 수 없는 타입
- 그 타입을 Nullable, Non-nullable로 처리할지는 개발자 마음이다.
- 코틀린은 보통 널이 될 수 없는 타입의 값에 대해 널 안전성을 검사하는 연산을 수행하면 경고를 표시하지만 플랫폼 타입의 값이 널이 될 수도 있음을 알고 있다면 아무 경고도 표시하지 않는다.

! 표기는 타입이 널 가능성에 대해 아무 정보도 없다는 뜻.

**상속**
코틀린에서 자바 메소드를 오버라이드할 때 그 메소드의 파라미터와 반환 타입을 널이 될 수 있는 타입으로 선언할지 널이 될 수 없는 타입으로 결정해야 한다.

```java
// 자바 인터페이스
interface StringProcessor {
	void process(String value);
}

// 코틀린(Non-nullable)
class StringPrinter : StringProcessor {
	override fun process(value: String) {
		println(value)
	}
}

// 코틀린(Nullable)
class NullableStringPrinter: StringProcessor {
	override fun process(value: String?) {
		if (value != null) {
			println(value)
		}
	}
}
```


## 6.2 코틀린의 원시 타입
### 6.2.1 원시 타입: Int, Boolean 등
- 원시 타입 - 변수에 값이 직접 들어간다
- 참조 타입 - 메모리상의 객체 위치가 들어간다. 

- 코틀린은 모든 것이 객체이다
- 하지만 숫자 타입은 실행 시점에 가장 효율적인 방식으로 컴파일된다.

### 6.2.2 널이 될 수 있는 원시 타입: Int?, Boolean? 등
- null 참조는 참조 타입의 변수에만 대입 가능하기 떄문에 nullable은 자바의 래퍼 타입으로 컴파일된다.
- 제네릭 클래스의 경우 래퍼타입 사용


### 6.2.3 숫자 변환
코틀린은 한 타입의 숫자를 다른 타입의 숫자로 자동변환 X

```java
val i = 1
val l: Long = i // 컴파일 오류 발생

val i = 1
val l: Long = i.toLong()
```

- Long 타입 - L접미사
- 표준 부동소수점 표기법 Double
- Float 타입 - f, F 접미사
- 16진 리터럴 - 0x, 0X
- 2진 리터럴 - 0b, 0B 

### 6.2.4 Any, Any?: 최상위 타입
- 자바에서 Object가 최상위 클래스이듯 코틀린은 Any 타입이 모든 널이 될 수 없는 타입의 최상위 클래스이다.
- 단 자바는 원시타입은 Object를 상속하지 않지만 코틀린은 전부 Any에 포함된다.
- 코틀린 함수가 Any를 사용하면 자바 바이트코드의 Object로 컴파일된다.

### 6.2.5 Unit 타입: 코틀린의 void
- 자바의 void와 같은 기능을 한다
- void와 달리 Unit을 타입 인자로 쓸 수 있다.
- Unit 타입의 함수는 Unit 값을 묵시적으로 반환한다.
- “Unit” - 단 하나의 인스턴스만 갖는 타입

### 6.2.6 Nothing 타입: 이 함수는 결코 정상적으로 끝나지 않는다.
- 함수가 비정상적으로 끝나지 않음을 알려주는 반환 타입
- Nothing 타입은 아무 값도 포함하지 않음

```java
fun fail (message: String) : Nothing {
	throw IllegaStateException(message)
}

val address = company.address ?: fail("No address")
println(address.city)
```

## 6.3 컬렉션과 배열
### 6.3.1 널 가능성과 컬렉션
List<Int?> 는 Int? 타입의 값을 저장
- 컬렉션도 Nullable과 Non-nullable 타입으로 구분된다.
- List<Int?>? - Nullable한 값으로 이뤄진 Nullable 리스트
	- 이런 리스트는 변수에 대해 널 검사를 수행한 다음에 그 리스트에 속한 모든 원소에 대해 다시 널 검사를 수행해야 한다.
- filterNotNull()로 널을 걸러낼 수 있다

### 6.3.2 읽기 전용과 변경 가능한 컬렉션
- 코틀린은 컬렉션 안의 데이터에 접근하는 인터페이스와 컬렉션 안의 데이터를 변경하는 인터페이스를 분리함.
- 원소를 추가하고 제거하기 위해선 Mutable Collection을 사용해야 한다.
- 가능하면 항상 읽기 전용 컬렉션을 사용하는 것이 좋다.
- 읽기 전용 컬렉션은 스레드 안전하지 않다.

### 6.3.3 코틀린 컬렉션과 자바
- 코틀린의 컬렉션을 자바에게 넘길 땐 아무 컬렉션이나 뮤터블 컬렉션을 인자로 넘길 수 있다.
	- 자바는 읽기 전용과 변강 가능 컬렉션을 구분하지 않으므로 문제가 된다
	- 자바로 컬렉션을 건낼 땐 주의해야 한다.

### 6.3.4 컬렉션을 플랫폼 타입으로 다루기
- 플랫폼 타입은 사용자가 읽기 전용이나 변경 가능으로 선택해서 다룰 수 있다.
- 보통은 문제되지 않으나 컬렉션 타입이 시그니처에 들어간 자바 메소드 구현을 오버라이드 하려는 경우 문제가 된다
	- 오버라이드 하려는 메소드의 자바 컬렉션 타입을 어떤 코틀린 컬렉션 타입으로 표현할지 결정해야 함.

1. 컬렉션이 널이 될 수 있는가?
2. 컬렉션의 원소가 널이 될 수 있는가?
3. 오버라이드하는 메소드가 컬렉션을 변경할 수 있는가?

```java
// 자바
interface DataParser<T. {
	void parseData(String input,
		 List<T> output,
		 List<String> errors);
}


class PersonParser : DataPaser<Person> {
	override fun parseData(input: String,
		output: MutableList<Person>,
		errors: MutableList<String?>) {
			// ...
	}
}
```


### 6.3.5 객체의 배열과 원시 타입의 배열
```java
fun main(args: Array<String>) {
	for (i in args.indices) {
		println("Argument $i is: ${args[i]}")
	}	
}
```

- 코틀린에서 배열을 만드는 방법
	1. arrayOf 함수에 원소를 넘기면 배열을 만들 수 있다.
	2. arrayOfNulls 함수에 정수 값을 인자로 넘기면 모든 원소가 null이고 인자로 넘긴 값과 크기가 같은 배열을 만들 수 있다. 물론 원소 타입이 널이 될 수 있는 타입인 경우에만 이 함수를 쓸 수 있다.
	3. Array 생성자는 배열 크기와 람다를 인자로 받아서 람다를 호출해서 각 배열원소를 초기화해준다.  arrayOf를 쓰지 않고 각 원소가 널이 아닌 배열을 만들어야 하는 경우 이 생성자를 사용한다.

**알파벳으로 이뤄진 배열 만들기**
```java
>>> val letters = Array<String>(26) { i -> ('a' + i).toString() }
```

**컬렉션을 vararg 메소드에게 넘기기**
```java
>>> val strings = listOf("a", "b", "c")
>>> println("%s/%s/%s".format(*strings.toTypedArray()) // vararg 인자를 넘기기 위해 스프레드 연산자(*)를 써야 한다.
a/b/c
```

**배열에 forEachIndexed 사용하기**
```java
fun main(args: Array<String>) {
	args.forEachIndexed { index, element ->
		println("Argument $index is: $element")
	}
}
```

## 6.4 요약
- 코틀린은 널이 될 수 있는 타입을 지원해 NullPointerException 오류를 컴파일 시점에 감지할 수 있다.
- 코틀린의 안전한 호출(?.), 엘비스 연산자(?:), 널 아님 단언(!!), let 함수 등을 사용하면 널이 될 수 있는 타입을 간결한 코드로 다룰 수 있다.
- as? 연산자를 사용하면 값을 다른 타입으로 변환하는 것과 변환이 불가능한 경우를 처리하는 것을 한꺼번에 편리하게 처리할 수 있다.
- 자바에서 가져온 타입은 코틀린에서 플랫폼 타입으로 취급된다. 개발자는 플랫폼 타입을 널이 될수 있는 타입으로도, 널이 될 수  없는 타입으로도 사용할 수 있다.
- 코틀린에서 수를 표현하는 타입이 일반 클래스와 똑같이 생겼고 일반 클래스와 똑같이 동작한다. 하지만 대부분 컴파일러는 숫자 타입을 자바 원시 타입으로 컴파일한다.
- 널이 될 수 있는 원시 타입은 자바의 박싱한 원시 타입에 대응한다.
- Any 타입은 다른 모든 타입의 조상 타입이며, 자바의 Object에 해당한다. Unit은 자바의 void와 비슷하다.
- 정상적으로 끝나지 않는 함수의 반환 타입을 지정할 때 Nothing 타입을 사용한다.
- 코틀린 컬렉션은 표준 자바 컬렉션 클래스를 사용한다. 하지만 코틀린은 자바보다 컬렉션을 더 개선해서 읽기 전용 컬렉션과 변경 가능한 컬렉션을 구별해 제공한다.
- 자바 클래스를 코틀린에서 확장하거나 자바 인터페이스를 코틀린에서 구현하는 경우 메소드 파라미터의 널 가능성과 변경 가능성에 대해 깊이 생각해야 한다.
- 코틀린의 Array 클래스는 일반 제네릭 클래스처럼 보인다. 하지만 Array는 자바 배열로 컴파일된다.
- 원시 타입의 배열 IntArray와 같이 각 타입에 대한 특별한 배열로 표현된다.






#코틀린/Kotlin in Action#