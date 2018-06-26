# 9장 제네릭스
질문 : 타입소거, 스타 프로젝션
## 9.1 제네릭 타입 파라미터
자바와 똑같이 클래스에 타입 파라미터를 정하는 형태로 사용한다.
ex) List<String>, Map<K, V>

**타입 추론**
```java
val readers : MutableList<String> = mutableListOf()

// 위와 아래는 동등한 선언이다
val readers = mutableListOf<String>()
```


### 9.1.1 제네릭 함수와 프로퍼티
**제네릭 고차 함수 호출**
```java
val authors = listOf("Dmitry", "Svetlana")
val readers = mutableListOf<String>(/*...*/)
fun <T> List<T>.filter(predicate: (T) -> Boolean): List<T>
>>> readers.filter { it !in authors }
```
람다 파라미터에 대해 자동으로 만들어진 변수 it의 타입은 T라는 제네릭 타입이다

**제네릭 확장 프로퍼티**
```java
val <T> List<T>.penultimate: T
	get() = this[size - 2]
>>> pritnln(listOf(1,2,3,4).penultimate)
3
```

**확장 프로퍼티만 제네릭하게 만들 수 있다**
일반 프로퍼티는 타입 파라미터를 가질 수 없다.  클래스 프로퍼티에 대한 여러 타입의 값을 저장할 수는 없으므로 제네릭한 일반 프로퍼티는 말이 되지 않는다.

```java
>>> val <T> x: T = TODO()
ERROR: type parameter of a property must be used in its receiver type
```

### 9.1.2 제네릭 클래스 선언
자바와 마찬가지로 클래스 이름 뒤에 (<>) 기호를 붙이면 클래스(인터페이스)를 제네릭하게 만들 수 있다. 타입 파라미터를 이름 뒤에 붙이고 나면 클래스 본문 안에서 타입 파라미터를 다른 일반 타입처럼 사용할 수 있다.

```java
interface List<T> { 
	operator fun get(index: Int) : T // T를 일반 타입처럼 사용 가능
}
```

**제네릭 클래스를 확장하는 클래스**
제네릭 클래스를 확장하는 클래스를 정의하려면 기반 타입의 제네릭 파라미터에 대해 타입 인자를 지정해야 한다. 이때 구체적인 타입을 넘길 수도 있고 타입 파라미터로 받은 타입을 넘길수도 있다.
```java
// 하위 클래스에서 상위 클래스의 함수를 오버라이딩하거나 사용하려면 타입인자 T를 구체적 타입으로(여기선 String)으로 치환해야 한다.
class StringList: List<String> {
	override fun get(index: Int): String = ... 
}

class ArrayList<T> : List<T> { // 앞의 T와 뒤의 T는 같지 않다.
	override fun get(index: Int): T = ...
} 
```


### 9.1.3 타입 파라미터 제약
**type parameter constraint**
- 클래스나 함수에 사용할 수 있는 타입 인자를 제한하는 기능
- 어떤 타입을 제네릭 타입의 타입 파라미터에 대한 상한(Upper Bound)으로 지정하면 그 제네릭 타입을 인스턴스화할 때 사용하는 타입 인자는 반드시 그 상한 타입이거나 그 상한 타입의 하위 타입이어야 한다.
- 제약을 가하려면 타입 파라미터 이름 뒤에 콜론(:)을 표시하고 그 뒤에 상한 타입을 적으면 된다.

```java
fun <T : Number> List<T>.sum(): T
```

**타입 파라미터에 여러 제약을 가하기**
where 키워드로 여러 타입 파라미터를 제약할 수 있다.
```java
fun <T> ensureTrailingPeriod(seq: T)
	where T: CharSequence, T: Appendable { // 제약 목록
	if (!seq.endsWith('.')) { // CharSequence 인터페이스의 확장 함수
		seq.append('.') // Appendable 인터페이스의 메소드 호출
	}
}
```


### 9.1.4 타입 파라미터를 널이 될 수 없는 타입으로 한정
제네릭 클래스나 함수를 정의하고 그 타입을 인스턴스화할 때는 널이 될 수 있는 타입을 포함하는 어떤 타입으로 타입 인자를 지정해도 타입 파라미터를 치환할 수 있다.
- 아무런 상한을 정하지 않은 타입 파라미터는 결과적으로 Any?를 상한으로 정한 파라미터와 같다.

```java
class Processor<T> {
	fun process(value: T) {
		value?.hashCode() // null이 될 수 있으모로 안전한 호출해야함
	}
}

// 항상 null이 될 수 없는 타입만 인자로 받고 싶을 경우
class Processor<T : Any> {
	fun process(value: T) {
		value.hashCode() // value는 null이 될 수 없다.
	}
}
```

## 9.2 실행 시 제네릭스의 동작: 소거된 타입 파라미터와 실체화된 타입 파라미터
## 9.2.1 실행 시점의 제네릭: 타입 검사와 캐스트
- 자바와 마찬가지로 코틀린 제네릭 타입 인자 정보는 런타임에 지워진다.(타입 소거:  type erasure) -> 제네릭 클래스 인스턴스가 그 인스턴스를 생성할 때 쓰인 타입 인자에 대한 정보를 유지하지 않는다는 뜻이다.
- 예) List<String> 객체를 만들고 그 안에 문자열을 여럿 넣더라도 실행 시점에는 그 객체를 오직 List로만 볼 수 있다. 그 List 객체가 어떤 타입의 원소를 저장하는 실행 시점에는 알 수 없다.

**타입소거 장점**
- 저정해야 하는 타입 정보의 크기가 줄어들어서 전반적인 메모리 사용량이 줄어든다
**단점**
- 개개체가 어떤 타입인지 알 수 없다

**star projection**
코틀린에서는 타입 인자를 명시하지 않고 제네릭 타입을 사용할 수 없으므로  스타 프로젝션을 사용하여 타입을 표현한다.
인자를 알 수 없는 제네릭 타입을 표현할 때 사용
`if (value is List<*>) { ... }`


**제네릭 타입으로 타입 캐스팅**
```java
fun printSum(c: Collection<*>) {
	val intList = c as? List<Int> // 여기서 Unchecked cast: List<*> to List<Int> 경고 발생
		?: throw IllegaArgumentException("List is expected")
	println(intList.sum())
}
>>> printSum(listOf(1, 2, 3))
6
```
컴파일러가 캐스팅 관련 경고를 한다는 점을 제외하면 모든 코드가 문제없이 컴파일됨

 **알려진 타입 인자를 사용해 타입 검사**
```java
fun printSum(c: Collection<Int>) {
	if (c is List<Int>) {
		println(c.sum())
	}
}
```


### 9.2.2 실체화환 타입 파라미터를 사용한 함수 선언
인라인 함수의 타입 파라미터는 실체화되므로 실행 시점에 인라인 함수의 타입 인자를 알 수 있다 -> 타입 소거에 대한 영향을 받지 않는다

**reified**
reified 키워드는 이 타입 파라미터가 실행 시점에 지워지지 않음을 표시한다.
```java
inline fun <reified T> isA(value: Any) = value is T
>>> println(isA<String>("abc"))
true
>>> println(isA<String>(123))
false
```

**인라인 함수에서만 실체화한 타입 인자를 쓸 수 있는 이유**
컴파일러는 인라인 함수의 본문을 구현한 바이트코드를 그 함수가 호출되는 모든 지점에삽입하기 때문이다. 컴파일러는 실체화한 타입 인자를 사용해 인라인 함수를 호출하는 각 부분의 정확한 타입 인자를 알 수 있다.
단, 자바 코드에선 reified  타입 파라미터를 사용하는 inline 함수를 호출 할 수 없다.

### 9.2.3 실체화한 타입 파라미터로 클래스 참조 대신
표준 자바 API인 ServiceLoader 사용해 서비스를 읽어 들이려면 다음 코드처럼 호출해야 된다.
`val serviceImpl = ServiceLoader.load(Service::class.java)`

구체화한 타입 파라미터로 아래와 같이 간단히 할수 있다.
`valserviceImpl = loadService<Service>()`


```java
inline fun <reified T> loadService() {
	return ServiceLoader.load(T::class.java)
```


### 9.2.4 실체화한 타입 파라미터의 제약
**실체화한 타입 파라미터 사용 가능한 경우**
- 타입 검사와 캐스팅(is, !is, as, as?)
- 코틀린 리플렉션 클래스 API(::class)
- 코틀린 타입에 대응하는 java.lang.Class를 얻기(::class.java)
- 다른 함수를 호출할 때 타입 인자로 사용

**사용 불가능한 경우**
- 타입 파라미터 클래스의 인스턴스 생성하기
- 타입 파라미터 클래스의 동반 객체 메소드 호출하기
- 실체화한 타입 파라미터를 요구하는 함수를 호출하면서 실체화하지 않은 타입 파라미터로 받은 타입을 타입 인자로 넘기기
- 클래스, 프로퍼티, 인라인 함수가 아닌 함수의 타입 파라미터를 reified로 지정하기


## 9.3 변성: 제네릭과 하위 타입
**variance(변성)**
List<String>와 List<Any>와 같이 기저 타입이 같고 타입 인자가 다른 여러 타입이 서로 어떤 관계가 있는지 설명하는 개념

### 9.3.1 변성이 있는 이유: 인자를 함수에 넘기기
List<Any> 타입의 파라미터를 받는 함수에 List<String>을 넘길 경우 안전성을 보장할 수 없다.

```java
fun addAnswer(list: MutableList<Any>) {
	list.add(42)
}

>>> val strings = mutalbeListOf("abc", "bac")
>>> addAnswer(strings)
>>> println(strings.maxBy { it.length })
ClassCastException: Integer cannot be cast to String
```
MutableList<Any>가 필요한 곳에 MutableList<String>을 넘기면 예외가 발생한다.
하지만 원소 추가나 변경이 없는 경우인 List<String>을 List<Any> 대신 넘겨도 된다.

### 9.3.2 클래스, 타입, 하위 타입
**하위 타입**
어떤 타입 A의 값이 필요한 모든 장소에 어떤 타입 B의 값을 넣어도 아무 문제가 없다면 타입 B는 타입 A의 하위 타입이다.
[subtype-supertype](https://cdn-images-1.medium.com/max/1200/1*rXI0hFY84ICHcPnJv3aMJQ.png)

![](chapter9_%E1%84%8C%E1%85%A6%E1%84%82%E1%85%A6%E1%84%85%E1%85%B5%E1%86%A8%E1%84%89%E1%85%B3/FCBB5070-9B2E-4183-999C-B5F952851A10.png)

**어떤 타입이 다른 타입의 하위 타입인지 검사하기**
```java
fun test(i: Int) {
	val n: Number = i // Int는 Number의 하위 타입이기 때문에 컴파일 됨
	
	fun f(s: String) { /*...*/ }
	f(i)	// Int는 String의 하위 타입이 아니어서 컴파일 X
}
```

**하위 클래스**
근본적으로 하위 타입과 같음
Int 클래스는 Number의 하위 클래스이므로 Int는 Number의 하위 타입이다.
어떤 인터페이스를 구현하는 클래스의 타입은 그 인터페이스 타입의 하위 타입이다.
널이 될수 있는 타입은 하위 타입과 하위 클래스가 같지 않은 경우를 보여주는 예
-> A?는 A의 하위 타입, 역은 성립 X

**무공변(invariant)**
제네릭 타입을 인스턴스화할 때 타입 인자로 서로 다른 타입이 들어가면 인스턴스 타입 사이의 하위 타입 관계가 성립하지 않는 제네릭 타입
자바에서는 모든 클래스가 무공변이다.

**공변적(covariant)**
A가 B의 하위 타입이면  List<A>는 List<B>의 하위 타입이다. 이런 클래스나 인터페이스는 공변적이다.

### 9.3.3 공변성: 하위 타입 관계를 유지
**out**
코틀린에서 제네릭 클래스가 타입 파라미터에 대해 공변적임을 표시하기 위해 파라미터 이름 앞에 붙임
```java
interface Producer<out T> { // 클래스가 T에 대해 공변적
	fun produce(): T
}
```

**무공변 사용**
- 명시적으로 타입 캐스팅 해줘야됨
```java
open class Animal {
	fun feed() { ... }
}

class Herd<T : Animal> { // T는 무공변적
	val size: Int get()= ...
	operator fun get(i: Int) : T { ... }
}

fun feedAll(animals: Herd<Animal>) {
	for (i in 0 until animals.size) {
		animals[i].feed()
	}
}

class Cat: Animal() {
	fun cleanLitter() { ... }
}

fun takeCareOfCats(cats: Herd<Cat>) {
	for (i in 0 until cats.size) {
		cats[i].cleanLitter()
		// feedAll(cats) // Error expected Herd<Animal> 발생
	}
}
```


**공변적인 클래스 사용**
```java
class Herd<out T: Animal> { // T는 공변적
	...
}

fun takeCareOfCats(cats: Herd<Cat>) {
	for (i in 0 until cats.size) {
		cats[i].cleanLitter()
		feedAll(cats) // 캐스팅할 필요가 없다.
	}
}
```

- 모든 클래스를 공변적으로 만들 수는 없다.
- 타입 파라미터를 공변적으로 지정하면 클래스 내부에서 그 파라미터를 사용하는 방법을 제한한다.
- 타입 안전성을 보장하기 위해 공변적 파라미터는 항상 아웃 위치에만 있어야 한다.
-> 클래스가 T 타입의 값을 생산할 수 는 있지만 T 타입의 값을 소비할 수는 없다는 뜻

- T가 반환 타입에 쓰임
-> T는 아웃 위치에 존재, 그 함수는 T 타입의 값을 생산(produce)한다
- T가 함수의 파라미터 타입에 쓰임
-> T는 인 위치에 존재, 그 함수는 T 타입의 값을 소비(consume)한다

**타입 파라미터 T에 붙은 out 키워드의 의미**
- **공변성**: 하위 타입 관계가 유지된다
- **사용 제한**: T를 아웃 위치에서만 사용할 수 있다.



### 9.3.4 반공변성: 뒤집힌 하위 타입 관계
**반공변성(contravariance)**
공변성의 반대
반공변 클래스의 하위 타입 관계는 공변 클래스와 반대
-> 예) 타입 B가 타입 A의 하위 타입인 경우 Consumer<A>가 Consumer<B>의 하위 타입인 관계가 성립하면 제네릭 클래스 Consumer<T>는 타입 인자 T에 대해 반공변이다.

**공변성**
- Producer<out T>
- 타입 인자의 하위 타입 관계가 제네릭 타입에서도 유지된다.
- Produer<Cat>은 Producer<Animal>의 하위 타입이다.
- T를 이웃 위치에서만 사용할 수 있다.

**반공변성**
- Consumer<in T>
- 타입 인자의 하위 타입 관계가 제네릭 타입에서 뒤집힌다.
- Consumer<Animal>은 Consumer<Cat>의 하위 타입이다.
- T를 인 위치에서만 사용할 수 있다.

**무공변성**
- MutableList<T>
- 하위 타입 관계가 성립하지 않는다.
- T를 아무 위치에서나 사용할 수 있다.

























#코틀린/Kotlin in Action#