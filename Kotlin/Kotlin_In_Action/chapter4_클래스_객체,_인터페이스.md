# 4장 클래스, 객체, 인터페이스
코틀린 기본 선언 : final & public
				중첩 클래스는 기본적으로 내부 클래스가 아니다
				-> 코틀린의 중첩 클래스에는 외부 클래스에 대한 참조가 없다

## 4.1 클래스 계층 정의
### 4.1.1 코틀린 인터페이스 
자바 8 인터페이스와 비슷
-> 구현이 있는 메소드 정의 가능 (디폴트 메소드)

- 콜론(:)을 클래스명 옆에 붙여서 사용

```java
interface Clickable {
	fun click()
}

class Button : Clickable {
	override fun click() = println("I was clicked")
}
```

**override 변경자**
자바와 달리 오버라이드롤 반드시 써줘야 오버라이드 가능
-> 실수로 오버라이드 하는 경우를 방지

**기본 구현이 있는 메소드**
- 자바와 달리 default를 붙이지 않고 일반 메소드 처럼 추가하면 된다.
- 단, 인터페이스를 두개 이상 사용시 메소드명이 중복될시 새로 구현해야한다.
- 상위 타입의 메소드를 호출할 때는 `super<타입명>.메소드()` 의 형태로 호출


**자바에서 코틀린의 메소드가 있는 인터페이스 사용**
코틀린의  `클래스명. DefaultImpls.메소드명()` 로 사용

### 4.1.2 open, final, abstract 변경자 : 기본적으로 final 
**취약한 기반 클래스** (fragile base class ) : 하위 클래스가 상위 클래스에 기반을 둔 클래스

- 취약한 기반 클래스 문제를 해결하기 위해 기본적으로 final이라 오버라이드 불가능
- 오버라이드 하기 위해선 메소드명과 클래스명에 **open**키워드를 추가해야한다.
- 오버라이드 하는 메소드를 하위 클래스에서 오버라이드 하지 못하게 하기 위해선 **final** 키워드를 추가해야 한다.

**abstract**
- 추상 메소드나 클래스를 만들기 위해 사용
- 인터페이스의 메소드는 구현이 없을 경우 기본적으로 추상이므로 abstract 키워드를 안 붙여도 된다.

### 4.1.3 가시성 변경자: 기본적으로 공개
- public - 모든 곳에서 볼 수 있다
- internal - 같은 모듈 안에서만 볼 수 있다
- protected - 하위 클래스에서 만 볼 수 있다.
- private - 같은 클래스 안에서만 볼 수 있다.

코틀린은 package-private이 없다. 오직 네임스페이스를 관리하기 위한 용도이다
코틀린에서 package-private을 표현 할 수 없다.


### 4.1.4 내부 클래스와 중첩된 클래스: 기본적으로 중첩 클래스
- 코틀린의 중첩 클래스는 명시적 요청 없인 바깥쪽 클래스 인스턴스에 대한 접근 권한이 없다

- 자바에서 중첩 클래스 : static으로 선언하면 그 클래스를 둘러싼 바깥쪽 클래스에 대한 묵시적인 참조가 사라진다
- 코틀린의 중첩 클래스 : 자바와 정반대로 동작. 기본이 자바 static 중첩 클래스와 같다

**inner**
- 중첩 클래스를 내부 클래스로 변경하기 위한 변경자

	**클래스 B 안에 정의된 클래스 A**	       	       |     **Java**	  |	**Kotlin**
중첩 클래스 (바깥쪽 클래스에 대한 참조 저장 X)     | static class A	  |	class A
내부 클래스 (바깥쪽 클래스에 대한 참조 저장함)     |class A		  |	inner class A

**this@Outer**
- 내부 클래스 Inner 안에서 바깥쪽 클래스의 참조를 접근하기 위해 사용

### 4.1.5 봉인된 클래스
- 코틀린의 when식은 반드시 디폴트 분기인 else 분기를 가지고 있어야 한다.
- 그러나 sealed 변경자를 클래스명 옆에 붙이면 그 상위 클래스를 상속한 하위 클래스 정의를 제한할 수 있다.
- sealed 클래스의 하위 클래스를 정의할 때는 반드시 상위 클래스 안에 중첩시켜야 한다.
- sealed 클래스는 자동으로 open이다.
- 디폴트 분기를 사용하지 않고 when을 사용하면 나중에 sealed 클래스의 상속 계층에 새로운 하위 클래스를 추가해도 when 식이 컴파일되지 않는다. -> when식을 고쳐야 함을 알 수 있다
- sealed 클래스는 기본적으로 private 생성자를 가진다. -> 클래스 내부에서만 호출 가능



## 4.2 뻔하지 않은 생성자와 프로퍼티를 갖는 클래스 선언
- 코틀린은 주생성자와 부생성자가 존재
	- 초기화는 초기화 블록을 통해 한다
	
### 4.2.1 클래스 초기화: 주 생성자와 초기화 블록
-  주 생성자: `class User(val nickname: String)`  
	- 클래스 선언 옆에 씀

```java
class User constructor(_nickname: String) {
	val nickname: String
	init {
		nickname = _nickname
	}
}
```
- constructor - 주 생성자나 부 생성자 정의를 시작할 때 사용
- init - 초기화 블록을 시작
- 언더바 (_) - 파라미터와 프로퍼티의 이름이 같을 땐 파라미터에 _를 앞에 붙여서 구분.

**기반 클래스에서 주 생성자 호출**
```java
open class User(val nickname: String) {...}

class TwitterUser(nickname: String) : User(nickname) { ... }
```


### 4.2.2 부 생성자: 상위 클래스를 다른 방식으로 초기화
일반적으로 코틀린에서는 생성자가 여럿 있는 경우가 자바보다 훨씬 적다


### 4.2.3 인터페이스에 선언된 프로퍼티 구현

**추상 프로퍼티를 갖고 있는 인터페이스**
```java
interface User {
	val nickname: String
}
```

**게터 ,세터 존재하는 추상 프로퍼티 선언 가능**
```java
interface User {
	val email: String
	val nickname: String
		get() = email.substringBefore('@')
}
```
- 추상 프로퍼티 게터 - 호출 할 때마다 계산해서 결과를 돌려줌.

### 4.2.4 게터와 세터에서 뒷받침하는 피드에 접근
```java
class User(val name: String) {
	var address: String = "unspecified"  // backing field
		set(value: String) {
			println("""
				Address was changed for $name":
				"$field" -> "$value".""".trimIndent())
			field=value // backing field 값 변경
		}
}
```

- field 를 통해 backing field에 접근

### 4.2.5 접근자의 가시성 변경
```java
class LengthCounter {
	var counter: Int = 0
		private set // 세터 가시성 변경
	fun addWord(word: String) {
		counter += word.length
	}
}
```

## 4.3 데이터 클래스와 클래스 위임
### 4.3.1 모든 클래스가 정의해야 하는 메소드
코틀린 클래스도 toString, equals, hashCode등을 오버라이드 가능

- toString() - 객체의 문자열 표현
- equals() - 객체의 동등성
	- java - ==는 원시타입에 사용하면 값 비교, 참조 타입에 사용하면 주소값 비교
	- kotlin - ==는 equals를 호출해서 객체를 비교, === : 참조 비교
- hashCode() 
	- JVM 언에에서는 equals()가 true를 반환하는 두 객체는 반드시 같은 hashCode()를 반환해야한다.

### 4.3.2 데이터 클래스
- data class - toString, equals, hashCode 등 자동 생성

**copy()**
- 객체를 복사하면서 일부 프로퍼티를 바꿀 수 있게 해주는 메소드
- 객체를 메모리상에서 직접 바꾸는 대신 복사본을 만드는 편이 낫다
- 복사본은 원본과 다른 생명주기를 가지며, 복사를 하면서 일부 프로퍼티 값을 바꾸거나 복사본을 제거해도 프로그램에서 원본을 참조하는 다른 부분에 전혀 영향을 끼치지 않는다.
```
>>> val lee = Client("이계영", 4122)
>>> println(lee, copy(postalcode = 4000))
Client (name, postalCode = 4000)
```


### 4.3.3 클래스 위임(Class Delegation): by
- 대규모 객체지향 시스템에서 보통 구현 상속은 시스템을 취약하게 한다
- **Decorator Pattern** : 상속을 허용하지 않는 클래스(기존 클래스) 대신 사용할 수 있는 새로운 클래스(Decorator)를 통해 기존 클래스와 같은 인터페이스를 데코레이터가 제공하고, 기존 클래스를 데코레이터 내부에 필드로 유지하는 패턴.
	- 새로 정의해야 하는 기능은 데코레이터의 메소드에 새로 정의하고 기존 기능은 데코레이터의 메소드가 기존 클래스의 메소드에게 요청을 전달(forwarding)
	- 단점 : 상당히 많은 코드가 필요하다

**by 키워드**

- 데코레이터 패턴으로 인한 보일러플레이트를 코틀린은 by 키워드를 통해 제거하고 있다.

```java
// 자바 delegation
class DelegatingCollection<T> : Collection<T> {
	private val innerList = arrayListOf<T>()
	override val size: Int get() = innerList.size
	override fun isEmpty(): Boolean = innerList.isEmpty()
	override fun contains(element: T) : Boolean = innerList.contains(element)
	override fun iterator() : Iterator<T> = innterList.iterator()
	override fun containsAll(elements: Collection<T>): Boolean = innerList.containAll(elements)
}

// 코틀린 delegation
class DelegatingCollection<T>(
	innerList: Collection<T> = ArrayList<T>()
) : Collection<T> by innerList {}
```
- 메소드 중 동작을 변경하고 싶으면 오버라이드 하면 된다

## 4.4 object 키워드 : 클래스 선언과 인스턴스 생성
1. object declaration(객체 선언)은 싱글턴을 정의하는 방법 중 하나
2. companion object(동반 객체)는 인스턴스 메소드는 아니지만 어떤 클래스와 관련 메소드와 팩토리 메소드를 담을 때 쓴다.
3. 객체 식(자바의 무명 내부 클래스)

### 4.4.1 객체 선언: 싱글턴
- 객체 선언 : 클래스 선언과 그 클래스의 단일 인스턴스의 선언을 합친 선언
```java
object Payroll {
	val allEmployees = arrayListOf<Person>()
	fun calculateSalary() {
		for (person in allEmployess) {
			...
		}
	}
}
```
- 클래스와 같이 프로퍼티 , 메소드, 초기화 블록 등 사용 가능
- 단, 생성자는 선언 불가
- 싱글턴 객체는 객체 선언문이 있는 위치에서 생성자 호출 없이 즉시 생성됨

- 싱글턴은 소규모 소프트웨어에서만 유용
	- 생성을 제어할 수 없고 생성자 파라미터를 지정할 수 없으므로 대규모 소프트웨어에서는 부적합
	- 그런 기능을 사용하기 위해선 의존관계 주입 프레임워크(ex : 구글 주스)와 코틀린 클래스를 함께 사용해야 한다.

**코틀린 객체를 자바에서 사용하기**
INSTANCE필드를 통해 사용
ex) `CaseInsensitiveFileComparator.INSTANCE.compare(file1,file2);`


### 4.4.2 동반객체(Companion Object) : 팩토리 메소드와 정적 멤버가 들어갈 장소
- 코틀린은 static 키워드가 없다.
- 대신 최상위 함수와 객체선언으로 해결( 주로 최상위 함수로 대체)
	- 하지만 최상위 함수는 private으로 표시된 클래스 비공개 멤버에 접근할 수 없다.
- 동반 객체의 프로퍼티나 메소드에 접근하기 위해선 동반객체가 정의된 클래스 이름을 사용해야하므로 Java의 static메소드 호출 방법과 같다

**동반객체의 메소드 호출 방법**
```java
class A {
	companion object {
		fun bar() {
			println("Companion object called")
		}
	}
}
>>> A.bar()
```

**동반객체를 사용한 정적 팩토리 메소드 구현 방법** 
```java
class User private constructor(val nickname: String) {
	companion object {
		fun newSubscribingUser(email: String) = 
			User(email.substringbefore('@')
		fun newFacebookUser(accountId: Int) =
			User(getFacebookName(accountId))
	}
}
```
- 동반객체 장점 :
	-  하위 클래스 객체를 반환할 수도 있다.
	- 생성할 필요가 없는 객체를 생성하지 않아도 됨
- 동반객체 단점 :  
	- 클래스를 확장해야할 때는 동반 객체 멤버를 하위 클래스에서 오버라이드 할 수 없으므로 여러 생성자를 사용하는 편이 낫다. -> 오버라이드 불가능

### 4.4.3 동반 객체를 일반 객체처럼 사용
- 동반 객체는 클래스 안에 정의된 일반 객체 이므로 일반 객체처럼 사용할 수 있다. 
	- 이름 붙이거나 인터페이스 상속, 확장 함수 추가, 프로퍼티 정의 가능
- 동반 객체 이름을 지정하지 않을 경우 자동으로 Companion 으로 정의됨


**동반 객체에서 인터페이스 구현**
```java
interface JSONFactory<T> {
	fun fromJSON(jsonText: String) : T
}

class Person(val name: String) {
	companion object : JSONFactory<Person> {
		override fun fromJSON(jsonText: String) : Person = ...
	}
}
```

**자바에서 코틀린 동반 객체 사용**
동반 객체의 이름을 사용하여 참조에 접근 가능
`Person.Companion.fromJSON("...");`
- 코틀린 클래스의 멤버를 정적인 멤버로 만들어야 할 경우
	- @JvmStatic 애노테이션 사용
	- @JvmField : 정적 필드가 필요할 경우 최상위 프로퍼티나 객체에서 선언된 프로퍼티 앞에 애노테이션을 붙인다.

**동반 객체 확장 함수**
```java
// 확장할 모듈
class Person(val firstName: String, val lastName: String) {
	companion object {
		// 비어있는 동반객체 선언
	}
}

// 다른 모듈에서 확장 함수 선언
fun Person.Companion.fromJSON(json: String) : Person {
	...
}

>>> val p = Person.fromJSON(json)
```
- 동반객체에 대한 확장함수를 작성할려면 빈 객체라도 꼭 동반객체를 선언해야 확장 함수 작성 가능


### 4.4.4 객체 식: 무명 내부 클래스를 다른 방식으로 작성
```java
window.addMouseListener(
	object : MouseAdapter() {
		override fun mouseClicked(e: MouseEvent) [
			//...
		}

		override fun mouseEntered(e: MouseEvent) {
			//...
		}
	}
}
```
- 사용한 구문은 객체 선언과 같다
	- 하지만 객체 이름이 빠져있다.
- 자바와 달리 final이 아닌 변수도 객체 식 안에서 사용 가능
- 메소드가 하나 뿐인 functional interface인 경우 SAM 변환을 통해 람다로 만들어 사용하는 것이 낫다.


## 4.5 요약
- 코틀린의 인터페이스는 자바 인터페이스와 비슷하지만 디폴트 구현나 프로퍼티를 포함할 수 있다.
- 모든 코틀린 선언은 기본적으로 final이며 public이다.
- 선언이 final이 되지 않게 만들려면(상속이나 오버라이딩이 가능하게 하려면) 앞에 open을 붙여야 한다.
- internal 선언은 같은 모듈 안에서만 볼 수 있다.
- 중첩 클래스는 기본적으로 내부 클래스가 아니다. 내부 클래스로 사용하기 위해선 inner 키워드를 앞에 붙여야 한다.
- sealed 클래스를 상속하는 클래스를 정의하려면 반드시 부모 클래스 정의 안에 중첩(or 내부) 클래스로 정의해야 함 (코틀린 1.1 부터는 같은 파일 안에만 있으면 된다)
- 초기화 블록과 부 생성자를 활용해 클래스 인스턴스를 더 유연하게 초기화할 수 있다.
- field 식별자를 통해 프로퍼티 접근자 안에서 프로퍼티의 데이터를 저장하는 데 쓰이는 backing field를 참조 할 수 있다.
- 데이터 클래스를 사용하면 컴파일러가 equals, hashCode, toString, copy등을 자동으로 생성해 준다.
- 클래스 위임(by)을 사용하면 간단하게 위임할 수 있다.
- 객체 선언을 통해 싱글턴을 정의 할 수 있다.
- 동반 객체는 자바의 정적 메소드와 필드 정의를 대신한다.
- 동반 객체도 다른 객체와 마찬가지로 인터페이스를 구현할 수 있다. 외부에서 동반한 객체에 대한 확장 함수와 프로퍼티를 정의할 수 있다.
- 코틀린의 객체 식은 자바의 무명 내부 클래스를 대신한다. 하지만 인스턴스를 구현하거나 외부 영역의 변수의 값을 변경 할 수 있는 등 더 많은 기능을 제공한다.






#코틀린/Kotlin in Action#