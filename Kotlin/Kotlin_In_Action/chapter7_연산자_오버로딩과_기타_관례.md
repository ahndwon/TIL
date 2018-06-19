# 7장 연산자 오버로딩과 기타 관례
convention : 어떤 언어 기능과 미리 정해진 이름의 함수를 연결해주는 기법

자바 - 언어 기능을 타입에 의존함
코틀린 - 언어 기능을 관례(convention)에 의존함 -> 기존 자바 클래스를 코틀린에 적용하기 위함

## 7.1 산술 연산자 오버로딩
코틀린에서 관례를 사용하는 가장 단순한 예
자바에서는 원시 타입에 대해서만 산술 연산자를 사용할 수 있다
코틀린은 컬렉션, 클래스 등에서도 산술 연산자가 사용 가능하다

### 7.1.1 이항 산술 연산 오버로딩
**operator 키워드**
- 연산자를 오버라이딩하기 위해선 함수 앞에 operator 키워드를 붙여야 한다. 
- operator 키워드를 빼먹은 경우 “operator modifier is required… “ 오류가 발생한다.
```java
data class Point(val x: Int, val y: Int) {
	operator fun plus(other: Point): Point {
		return Point(x + other.x, y + other.y)
	}
}

>>> val p1 = Point(10, 20)
>>> val p2 = Point(30, 40)
>>> println(p1 + p2)
Point(x=40, y=60)
```


**연산자를 확장 함수로 정의**
```java
operator fun Point.plus(other: Point): Point {
	return Point(x + other.x, y + other.y)
}
```


**오버로딩 가능한 이항 산술 연산자**
- *  - times
- / - div
- % - mod(1.1부터 rem)
- + - plus
- - - minus

**두 피연산자의 타입이 다른 연산자 정의**
```java
operator fun Point.times(scale: Double) : Point {
	return Point((x * scale).toInt(), (y * scale).toInt())
}

>>> val p = Point(10, 20)
>>> println(p * 1.5)
Point(x=15, y=30)
```

- 코틀린 연산자가 자동으로 교환법칙을 지원하지는 않는다. -> 함수 하나 더 정의해야함

**비트 연사자**
- shl - 왼쪽 시프트 (자바 <<)
- shr - 오른 쪽 시프트(부호 비트 유지, 자바 >>)
- ushr - 오른쪽 시프트(0으로 부호 비트 설정, 자바 >>>)
- and - 비트 곱(자바 &)
- or - 비트 합(자바 |)
- xor - 비트 배타 합(자바 ^)
- inv - 비트 반전(자바 ~)

### 7.1.2 복합 대입 연산자 오버로딩
- += , -= 등 - 복합 대입 연산자 (compound assignment operator)

이론적으로 += 는 plus와 plusAssign 양쪽으로 컴파일 가능
```
a += b
1. a= a.plus(b)
2. a.plusAssign(b)
```
-> plus와 plusAssign을 동시에 정의하면 안된다.

**컬렉션에 대한 연산자**
- +, - : 항상 새로운 컬렉션 반환
- +=, -= : 항상 변경 가능한 컬렉션에 작용해 메모리에 있는 객체 상태를 변환시킴
	- 읽기 전용 컬렉션에선 변경을 적용한 복사본을 반환

### 7.1.3 단항 연산자 오버로딩
이항 연산자 오버로딩과 같다

**단항 연산자 정의**
```java
operator fun Point.unaryMinus() : Point {
	return Point(-x, -y)
}
>>> val p = Point(10,20)
>>> println(-p)
Point(x=-10, y=-20)
```

**오버로딩 가능한 단항 산술 연산자**
- + : unaryPlus
- - : unaryMinus
- ! : not
- ++ : inc
- — : dec

## 7.2 비교 연산자 오버로딩
### 7.2.1 동등성 연산자: equals()
코틀린은 == 연산자를 호출시 equals() 메소드를 호출한다
!= 호출도 equals() 호출

`a==b` -> `a?.equals(b) ?: (b == null)`

**equals 메소드 구현**
```java
class Point(val x: Int, val y: Int) {
	override fun equals(obj: Any?): Boolean {
		if (obj === this) return true // 최적화
		if (obj !is Point) return false // 파라미터 타입 검사
		return obj.x == x && obj.y == y // Point로 스마트 캐스팅해서 프로퍼티 접근
	}
}
```

**식별자 비교(===) 연산자**
자바 == 연산자와 같다.
===는 자신의 두 피연산자가 서로 같은 객체를 가리키는지 비교한다.
===는 오버라이딩 불가능

Any의 equals()는 메소드 앞에 operator가 붙어있으므로 오버라이딩시 operator를 생략해도 자동으로 상위 클래스의 operator 지정이 적용된다.
그리고 Any에서 상속받은 equals 가 확장함수보다 우선순위가 높으므로 확장이 불가하다.

### 7.2.2 순서 연산자 : compareTo
Comparable에 들어있는 compareTo 메소드
코틀린은 관례로 제공
비교 연산자 ( > , < , <= , >=) 는 compareTo 호출로 컴파일된다
`a >= b` -> `a.compareTo(b) >= 0`

**compareTo() 구현**
```java
class Person(val firstName: String, val lastName: String)
: Comparable<Person> {
	override fun compareTo(other: Person) : Int {
		return compareValuesBy(this, other,
			Person::lastName, Person::firstName)
	}
}
```

- compareValuesBy() - 두  객체와 여러 비교 함수를 인자로 받는다. 
	- 첫 번째 비교 함수에 두 객체를 넘겨서 두 객체가 같지 않다는 결과(0이 아닌 값)가 나오면 그 결과 값을 즉시 반환한다 .
	- 두 객체가 같다는 결과(0)가 나오면 두 번째 비교 함수를 통해 두 객체를 비교한다.


## 7.3 컬렉션과 범위에 대해 쓸 수 있는 관례
### 7.3.1인덱스로 원소에 접근: get과 set
코틀린에서는 인덱스 여산자도 관례를 따름
	-> 인덱스 연산자([]) 사용하여 원소를 읽을 시 자동으로 get 연산자 메소드로 변환되고 쓰는 연산은 set 연산자 메소드로 변환된다.

**get 관례 구현**
```java
operator fun Point.get(index: Int) : Int {
	return when(index)	{
		0 -> x
		1 -> y
		else -> 
			throw IndexOutOfBoundsException("Invalid coordinate $index")
	}
}

>>> val p = Point(10, 20)
>>> println(p[1])
20
```

`x[a, b]` -> `x.get(a, b)`
- get 메소드의 파라미터는 반드시 Int가 아니여도 된다( 맵의 경우엔 키 타입으로 파라미터 설정 가능)

**set 관례 구현**
```java
data class MutablePoint(var x: Int, var y: Int)

operator fun MutablePoint.set(index: Int, value: Int) {
	when(index) {
		0 -> x = value
		1 -> y = value
		else ->
			throw IndexOutOfBoundsException("Invalid coordinate $index")
	}
}
```

`x[a, b] = c`  -> `x.set(a, b, c)`

### 7.3.2 in 관례
- in : 객체가 컬렉션에 들어있는지 검사(멤버십 검사(membership test))
	- contains 함수에 대응

```java
data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point): Boolean {
	return p.x in upperLeft.x until lowerRight.x &&
		p.y in upperLeft.y until lowerRight.y
}

>>> val rect = Rectangle(Point(10, 20), Point(50, 50))
>>> println(Point(20,30) in rect)
true
>>> println(Point(5, 5) in rect)
false
```

- until : 좌측은 닫히고 우측은 열린 범위
	- 10 until 20 : 10이상 20미만(19이하)

### 7.3.3 rangeTo 관례
- .. : 범위 만들기 위해 사용
	- 1..10 : 1부터 10까지 모든 수가 들어있는 범위

`start..end` -> `start.rangeTo(end)`
- rangeTo는 아무 클래스에나 정의 가능하며 Comparable 인터페이스를 구현하면 정의할 필요가 없다
- rangeTo는 다른 산술 연산자보다 우선순위가 낮다
- 괄호로 감싸면 메소드 호출 가능
	- `(0..n).forEach { print(it) }`

### 7.3.4 for 루프를 위한 iterator 관례
코틀린의 for 루프는 범위 검사와 똑같이 in 연산자 사용

```java
operator fun ClosedRange<LocalDate>.iterator() : Iterator<LocalDate> = 
	object : Iterator<LocalDate> {
		var current = start
		override fun hasNext() = 
			current <= endInclusive  // compareTo 관례로 날짜 비교
		override fun next() = current.apply { // 현재 날짜를 저장한 다음에 날짜를 변경한다. 그 후 저장해둔 날짜를 반환한다.
			current = plusDays(1) 
		}
}

>>> val newYear = LocaDate.ofYearDay(2017, 1)
>>> val daysOff = newYear.minusDays(1)..newYear
>>> for (dayOff in daysOff) { println(dayOff) }
2016-12-31
2017-01-01
```

## 7.4 구조 분해 선언과 component 함수
- 구조 분해 선언 (destructuring declaration) : 복합적인 값을 분해해서 여러 다른 변수를 한꺼번에 초기화

```java
>>> val p = Point(10, 20)
>>> val (x, y) = p
>>> println(x)
10
>>> println(y)
20
```

`val (a, b) = p` -> `val a = p.component1(), val b = p.component2()`

- data 클래스의 주 생성자의 들어있는 프로퍼티에 대해선 자동으로 componentN 함수가 생성됨 

**componentN 구현**
```java
class Point(val x: Int, val y: Int) {
	operator fun component1() = x
	operator fun component2() = y
}
```

**구조 분해 선언**
```java
data class NameComponents(val name: String, val extension: String)

fun splitFilename(fullName: String) : NameComponents {
	val (name, extension) = fullName.split('.', limit = 2)
	return NameComponents(name, extension)
}

>>> val (name, ext) = splitFilename("example.kt")
>>> println(name)
example
>>> println(ext)
kt
```

- 코틀린 표준 라이브러리에서는 맨 앞의 다섯 원소에 대한 componentN을 제공함
- Pair나 Triple 클래스 사용시 더 쉽게 여러 값을 반환할 수 있따.
	- 안에 담겨 있는 원소의 의미를 말해주지 않으므로 가독성은 떨어짐
	- 하지만 직접 클래스를 작성할 필요가 없으므로 코드는 더 단순해짐

### 7.4.1 구조 분해 선언과 루프
- 루프 안에서도 구조 분해 선언 가능
```java
fun printEntries(map: Map<String, String>) {
	for ((key, value) in map) {
		println("$key -> $value")
	}
}

>>> val map = mapOf("Oracle" to "Java", "JetBrains" to "Kotlin")
>>> printEntries(map)
Oracle -> Java
JetBrains -> Kotlin
```


## 7.5 프로퍼티 접근자 로직 재활용: 위임 프로퍼티
- 위임 프로퍼티를 사용하면 값을 뒷받침하는 필드에 단순히 저장하는 것보다 더 복잡한 방식으로 작동하는 프로퍼티를 쉽게 구현할 수 있다.
	- 또한 접근자 로직을 매번 재구현할 필요가 없다.

### 7.5.1 위임 프로퍼티 소개
 
**일반적인 위임 문법**
```java
class Foo {
	var p : Type by Delegate() // Delegate에게 p 프로퍼티를 위임
}

// 컴파일러가 위의 문법을 시행하는 방법
// 컴파일러는 숨겨진 도우미 프로퍼티를 만들고 위임 객체의 인스턴르를 초기화함
class Foo {
	private val delegate = Delegate()//컴파일러가 생성한 도우미프로퍼티
	var p: Type
	set(value: Type) = delegate.setValue(..., value)
	get() = delgate.getValue(...)
}
```

### 7.5.2 위임 프로퍼티 사용: by lazy()를 사용한 프로퍼티 초기화 지연
- 지연 초기화는 객체의 일부분을 초기화하지 않고 남겨뒀다가 실제로 그 부분의 값이 필요할 경우 초기화할 때 흔히 쓰이는 패턴이다.
	- 초기화 과정에 자원을 많이 사용하거나 객체를 사용할 때마다 꼭 초기화하지 않아도 되는 프로퍼티에 대해 지연 초기화 패턴을 사용할 수 있다.

**backing 프로퍼티를 사용한 지연 초기화**
```java
class Person(val name: String) {
	private var _emails : List<Email>? = null
	val emails: List<Email>
		get() {
			if (_emails==null) {
				_emails = loadEmails(this)
			}
			return _emails!!
		}
}

>>> val p = Person("Alice")
>>> p.emails
Load emails for Alice
>>> p.emails
```


**lazy 키워드를 통한 지연 초기화**
```java
class Person(val name: String) {
	val emails by lazy { loadEmails(this) } // 위의 지연초기화와 같다
}
```
lazy 함수는 코틀린 관례에 맞는 시그니처의 getValue 메소드가 들어있는 객체를 반환한다. 그러므로 by 키워드를 사용하여 위임 프로퍼티를 만들 수 있다.
lazy 의 인자는 값을 초기화할 때 호출할  람다다.


### 7.5.3 위임 프로퍼티 구현
```java
open class PropertyChangeAware {
	protected val changeSupport = PropertyChangeSupport(this)
	fun addPropertyChangeListener(listener: PropertyChangeListener) {
		changeSupport.addPropertyChangeListener(listener)
	}
	
	fun removePropertyChangeListener(listener: PropertyChangeListener) {
		changeSupport.removePropertyChangeListener(listener)
	}
}

class Person(val name: String, age: Int, salary: Int): PropertyChangeAware() {
	var age: Int = age
		set(newValue) {
			val oldValue = field // backing field 사용
			field = newValue
			changeSupport.firePropertyChange( // 프로퍼티 변경 통지
				"age", oldValue, newValue)
		}
	
	var salary: Int = salary
		set(newValue) {
			val oldValue = field
			field = newValue
			changeSupport.firePropertyChange(
				"salary", oldValue, newValue)
		}
}

>>> val p = Person("Dmitry", 34, 2000)
>>> p.addPropertyChangeListener( // 프로퍼티 변경 리스너 추가
		PropertyChangeListener { event -> 
			println("Property ${event.propertyName } changed " + 
				"from ${event.oldValue} tp ${event.newValue}")
		}
	)
>>> p.age = 35
Property age changed from 34 to 35
>>> p.salary = 2100
Property salary changed from 2000 to 2100
```
위는 field 키워드를 사용해 age와 salary 프로퍼티를 뒷받침하는 필드에 접근하는 방법을 보여준다.


 **도우미 클래스를 통해 프로퍼티 변경 통지 구현**
```java
class ObservableProperty(
	val propName: String, var propValue: Int,
	val changeSupport: PropertyChangeSupport
) {
	fun getValue(): Int = propValue
	fun setValue(newValue: Int) {
		val oldValue = propValue
		propValue = newValue
		changeSupport.firePropertyChange(propName, oldValue, newValue)
	}
}

class Person(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
	val _age = ObservableProperty("age", age, changeSupport)
	var arg : Int
		get() = _age.getValue()
		set(value) { _age.setValue(value) }
	val _salary = ObservableProperty("salary", salary, changeSupport)
	var salary: Int
		get() = _salary.getValue()
		set(value) { _salary.setValue(value) }
}
```
위는 코틀린의 위임이 실제로 작동하는 방식과 비슷하다. 프로퍼티 값을 저장하고 그 값이 바뀌면 자동으로 변경 통지를 전달해주는 클래스를 만들었고, 로직의 중복을 상당 부분 제거했다.


**프로퍼티 위임을 위임 구현**
```java
class ObeservableProperty(
	var propValue: Int, val changeSupport: PropertyChangeSupport
) {
	operator fun getValue(p: Person, prop: KProperty<*>): Int = propValue
	operator fun setValue(p: Person, prop: KProperty<*>, newValue: Int) {
		val oldValue = propValue
		propValue = newValue
		changeSupport.firePropertyChange(prop.name, oldValue, newValue)
	}
}

class Person(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
	var age: Int by ObservableProperty(age, changeSupport)
	var salary: Int by ObservableProperty(salary, changeSupport)
}
```
이전과 차이점
- 코틀린 관례를 따르기 위해 getValue와 setValue 함수에도 operator 변경자가 붙는다.
- getValue와 setValue는 프로퍼티가 포함된 객체(여기서는 Person 타입인 p)와 프로퍼티를 표현하는 객체를 파라미터로 받는다.
- KProperty 인자를 통해 프로퍼티 이름을 전달받으므로 주 생성자에는 name 프로퍼티를 없앤다.


**Delegates.observable을 사용해 프로퍼티 변경 통지 구현**
```java
class Person(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
	private val observer = {
		prop : KProperty<*>, oldValue: Int, newValue: Int ->
		changeSupport.firePropertyChange(prop.name, oldValue, newValue)
	}
	var age: Int by Delegates.observable(age, observer)
	var salary: Int by Delegates.observable(salary, observer)
}
```
-  관찰 가능한 프로퍼티 로직을 직접 작성하는 대신 코틀린 표준 라이브러리를 사용한 위임 프로퍼티 구현


### 7.5.4 위임 프로퍼티 컴파일 규칙
**프로퍼티 동작 방식**
```
class C {
	var prop: Type by MyDelegate()
}

val c = C()

// 위를 실행시키는 컴파일러의 동작
class C {
	private val <delegate> = MyDelegate()
	var prop: Type
	get() = <delegate>.getValue(this,<property>)
	set(value: Type) = <delegate>.setValue(this, <property>, value)
}
```

`val x = c.prop` ->	`val x = <delegate>.getValue(c, <property>)`
`c.prop = x` -> `delegate>.setValue(c, <property>, x)`

### 7.5.5 프로퍼티 값을 맵에 저장
- 확장 가능한 객체(expando object)- 위임 프로퍼티를 활용하여 자신의 프로퍼티를 동적으로 정의할 수 있는 객체

**값을 맵에 저장하는 프로퍼티 정의**
```java
class Person {
	// 추가 정보
	private val _attributes = hashMapOf<String, String>()
	fun setAttribute(attrName: String, value: String) {
		_attributes[attrName] = value
	}
	
	// 필수 정보
	val name: String
	get() = _attributes["name"]!! //수동으로 맵에서 정보 꺼냄
}
```

**값을 맵에 저장하는 위임 프로퍼티 사용**
```java
class Person {
	private val _attributes = hashMapOf<String, String>()
	fun setAttribute(attrName: String, value: String) {
		_attributes[attrName] = value
	}
	val name: String by _attributes // 위임 프로퍼티로 맵을 사용
}
```
- 표준 라이브러리가 Map과 MutableMap 인터페이스에 대해 getValue와 setValue 확장 함수를 제공해서 위임을 할 수 있다.

### 7.5.6 프레임워크에서 위임 프로퍼티 활용
객체 프로퍼티를 저장하거나 변경하는 방법을 바꿀 수 있으면 프레임워크를 개발할 때 유용하다

**위임 프로퍼티를 사용해 데이터베이스 칼럼을 접근하기**
```java
object Users : IdTable() {
	val name = varchar("name", length=50).index()
	val age = integer("age")
}

class User(id: EntityID): Entity(id) {
	var name: String by Users.name
	var age: Int by Users.age
}
```


## 7.6 요약
- 코틀린에서는 정해진 이름의 함수를 오버로딩함으로써 표준 수학 연산자를 오버로딩 할 수 있다. 하지만 직접 새로운 연산자를 만들 수는 없다.
- 비교 연산자는 equals와 compareTo 메소드로 변환된다.
- 클래스에 get, set , contains라는 함수를 저으이하면 그 클래스의 인스턴스에 대해 []와 in 연산을 사용할 수 있고, 그 객체를 코틀린 컬렉션 객체와 비슷하게 다룰 수 있다.
- 미리 정해진 관레를 따라 rangeTo, iterator 함수를 정의하면 범위를 만들거나 컬렉션과 배열의 원소를 이터레이션할 수 있다.
- 구조 분해 선언을 통해 한 객체의 상태를 분해해서 여러 변수에 대입할 수 있다. 함수가 여러 값을 한꺼번에 반환해야 하는 경우 구조 분해가 유용하다. 데이터 클래스에 대한 구조 분해는 거저 사용할 수 있지만, 커스텀 클래스의 인스턴스에서 구조 분해를 사용하려면 componentN 함수를 정의해야 한다.
- 위임 프로퍼티를 통해 프로퍼티 값을 저장하거나 초기화하거나 읽거나 변경할 때 사용하는 로직을 재활용할 수 있다. 위임 프로퍼티는 프레임워크를 만들 때 아주 유용하다.
- 표준 라이브러리 함수인 lazy를 통해 지연 초기화 프로퍼티를 쉽게 구현할 수 있다.
- Delegates.observable 함수를 사용하면 프로퍼티 변경을 관찰할 수 있는 관찰자를 쉽게 추가할 수 있다
- 맵을 위임 객체로 사용하는 위임 프로퍼티를 통해 다양한 속성을 제공하는 객체를 유연하게 다룰 수 있다.








7.3 
7.4
#코틀린/Kotlin in Action#