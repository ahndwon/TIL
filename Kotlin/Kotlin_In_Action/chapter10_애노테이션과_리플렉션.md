# 10장 애노테이션과 리플렉션
애노테이션과 리플렉션을 사용하면 사용하려는 함수가 정의된 클래스의 이름과 함수 이름, 파라미터 이름 등을 몰라도 사용할 수 있다.

## 10.1 애노테이션 선언과 적용
### 10.1.1 애노테이션 적용
코틀린에서는 자바와 같은 방법으로 애노테이션을 사용할 수 있다.
애노테이션을 적용하려는 대상 앞에 애노테이션을 붙이면 된다.

**@Depracated**
자바에서와 의미가 똑같다. 하지만 코틀린은 replaceWith 파라미터를 통해 옛버전을 대신할 수 있는 패턴을 제시할 수 있다.

```java
@Deprecated("Use removeAt(index) instead.", ReplaceWith("removeAt(index)"))
fun remove(index: Int) { ... }
```

애노테이션에 인자를 넘길 때는 일반 함수와 마찬가지로 괄호 안에 인자를 넣는다. 이런 remove 함수 선언이 있다면 인텔리J 아이디어는 remove를 호출하는 코드에 대해 경고 메시지를 표시해주고 quick fix도 제시해 준다.


**애노테이션 지정법**
- 클래스를 애노테이션 인자로 지정할 때는 @MyAnnotation(MyClass::class)처럼 ::class를 클래스 이름 뒤에 넣어야 한다.
- 다른 애노테이션을 인자로 지정할 때는 인자로 들어가는 애노테이션의 이름 앞에 @를 넣지 않아야 한다.
	- ex) 위 예제의 ReplaceWith는 애노테이션이지만 Deprecated 애노테이션의 인자로 들어가므로 ReplaceWith 앞에 @를 사용하지 않는다.
- 배열을 인자로 저장하려면 @RequestMapping(path = arrayOf(“/foo”, “/bar”)) 처럼 arrayOf 함수를 사용한다. 자바에서 선언한 애노테이션 클래스를 사용한다면 value라는 이름의 파라미터가 필요에 따라 자동으로 가변 길이 인자로 변환된다. 따라서 그런 경우에는 @JavaAnnotationWithArrayValue(“abc”, “foo”, “bar”) 처럼 arrayOf 함수를 쓰지 않아도 된다.

**const**
애노테이션 인자를 컴파일 시점에 알 수 있어야 한다. 따라서 임의의 프로퍼티를 인자로 지정할 수는 없다. 프로퍼티를 애노테이션 인자로 사용하려면 그 앞에 const 변경자를 붙여야 한다. 컴파일러는 const가 붙은 플퍼티를 컴파일 시점 상수로 취급한다.


### 10.1.2 애노테이션 대상
사용 지점 대상(use-site target) 선언으로 애노테이션을 붙일 요소를 정할 수 있다.
지점 대상은 @ 기호와 애노테이션 이름 사이에 붙으며, 애노테이션 이름과는 콜론(:)으로 분리된다.
`@get:Rule`  get -> 사용지점 대상,   Rule -> 애노테이션 이름

규칙을 지정하려면 공개 필드나 메소드 앞에 @Rule을 붙여야 한다. 
코틀린의 필드는 기본적으로 비공개이기 때문에 프로퍼티가 아닌 접근자에 애노테이션을 붙여야 한다.

```java
class HasTempFolder {
	@get:Rule
	val folder = TemporaryFolder() 
	@Test
	fun testUsingTempFolder() {
		val createdFile = folder.newFile("myfile.txt")
		val createdFolder = folder.newFolder("subfolder")
	}
}
```

**코틀린 사용 지점 대상 지원 목록**
- property - 프로퍼티 전체. 자바에서 선언된 애노테이션에는 이 사용 지점 대상을 사용할 수 없다.
- field - 프로퍼티에 의해 생성되는(backing) 필드
- get - 프로퍼티 게터
- set - 프로퍼티 세터
- receiver - 확장 함수나 프로퍼티의 수신 객체 파라미터
- param - 생성자 파라미터
- setparam - 세터 파라미터
- delegate - 위임 프로퍼티의 위임 인스턴스를 담아둔 필드
- file - 파일 안에 선언된 최상위 함수와 프로퍼티를 담아두는 클래스
	- packag 선언 앞에서 파일의 최상위 수준에만 적용할 수 있다


자바와 달리 코틀린에서는 임의의 식을 애노테이션 인자로 사용할 수 있다.
```java
fun test(list: List<*>) {
	@Suppress("UNCHECKED_CAST")
	val strings = list as List<String>
	// ...
}
```


### 10.1.3 애노테이션을 활용한 JSON 직렬화 제어
**직렬화**
객체를 저장장치에 저장하거나 네트워크를 통해 전송하기 위해 텍스트나 이진 형식으로 변환하는 것

**역직렬화**
텍스트나 이진 형식으로 저장된 데이터로부터 원래의 객체를 만들어낸다. 직렬화에 자주 쓰이는 형시에 JSON이 있다.	

JSON에는 객체의 타입이 저장되지 않기 때문에 JSON 데이터로부터 인스턴스를 만들려면 타입 인자로 클래스를 명시해야 한다.

- @JsonExclude - 직렬화나 역직렬화 시 그 프로퍼티를 무시할 수 있다.
- @JsonName 애노테이션을 사용하면 프로퍼티를 표현하는 키/값 쌍의 키로 프로퍼티 이름 대신 애노테이션이 지정한 이름을 쓰게 할 수 있다.

```java
data class Person(
	@JsonName("alias") val firstName: String,
	@JsonExclude val age: Int? = null
)
```

### 10.1.4 애노테이션 선언
애노테이션 클래스는 오직 선언이나 식과 관련 있는 메타데이터의 구조를 정의한다.
내부에 아무 코드도 들어올 수 없다.
파라미터가 있는 애노테이션을 정의하려면 애노테이션 클래스의 주생성자에 파라미터를 선언해야 한다.

```java 
// 자바
public @interface JsonName {
	String value();
}

// 코틀린 
annotation class JsonName(val name: String)
```


### 10.1.5 메타애노테이션: 애노테이션을 처리하는 방법 제어
**메타애노테이션(meta-annotation)**
애노테이션 클래스에 적용할 수 있는 애노테이션
표준 라이브러리에서의 메타애노테이션은 컴파일러가 애노테이션을 처리하는 방법을 제어한다.
프레임워크 중의 메타애노테이션은 주입 가능한 타입이 동일한 여러 객체를 식별할때 사용한다.

**@Target**
애노테이션을 적용할 수 있는 요소의 유형을 지정한다. 애노테이션 클래스에 대해 구체적인 @Target을 지정하지 않으면 모든 선언에 적용할 수 있는 애노테이션이 된다.

```java
@Target (AnnotationTarget.PROPERTY)
annotation class JsonExclude
```

메타애노테이션을 직접 만들어야 한다면 ANNOTATION_CLASS를 대상으로 지정해야 한다
```java
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class BindingAnnotation

@BindingAnnotation
annotation class MyBinding
```


### 10.1.6 애노테이션 파라미터로 클래스 사용
KCLass는 자바 java.lang.Class 타입과 같은 역할을 하는 코틀린 타입이다. 코틀린 클래스에 대한 참조를 저장할 때 KClass 타입을 사용한다. 

KClass<CompanyImpl>는 KClass<out Any>의 하위 타입이다
out 키워드가 빠지면 Deserialize-Inteface에 Company::class를 넘길수 없고 Any::class만 넘길 수 있다


### 10.1.7 애노테이션 파라미터로 제네릭 클래스 받기
@customSerializer 애노테이션은 커스텀 직렬화 클래스에 대한 참조를 인자로 받는다.
이 직렬화 클래스는 ValueSerializer 인터페이스를 구현해야만 한다.
```java
interface ValueSerializer<T> {
	fun toJsonValue(value: T): Any?
	fun fromJsonValue(jsonValue: Any?): T
}

data class Person(
	val name: String,
	@CustomSerializer(DateSerailizer::class) val birthDate: Date
)

annotation class CustomSerializer(
	val serializerClass: KClass<out ValueSerializer<*>>
)
```


## 10.2 리플렉션: 실행 시점에 코틀린 개체 내부 관찰
**리플렉션**
실행 시점에(동적으로) 객체의 프로퍼티와 메소드에 접근할 수 있게 해주는 방법
타입과 관계없이 객체를 다뤄야 하거나 객체가 제공하는 메소드나 프로퍼티 이름을 오직 실행 시점에만 알 수 있는 경우에 사용 -> JSON 직렬화

코틀린 리플렉션 API
1. 자바가 제공하는 java.lang.reflect 표준 리플렉션
	- 코틀린 클래스는 일반 자바 바이트코드로 컴파일되므로 자바 리플렉션 API도 코틀린 클래스를 컴파일한 바이트코드를 완벽히 지원한다. 이는 리플렉션을 사용하는 자바 라이브러리와 코틀린 코드가 완전히 호환된다는 뜻
2. 코틀린이 제공하는 kotlin.reflect 코틀린 리플렉션 API
	- 자바에는 없는 프로퍼티나 널이 될 수 잇는 타입과 같은 코틀린 고유 개념에 대한 리플렉션을 제공한다.하지만 자바 리플렉션을 완전히 대체할 수는 없다.

### 10.2.1 코틀린 리플렉션 API : KClass, KCallable, KFuntion, KProperty
**KClass**
java.lang.Class에 해당
클래스 안에 있는 모든 선언을 열거하고 각 선언에 접근하거나 클래스의 상위 클래스를 얻을 수 있다.

```java
// 코틀린
MyClass::class
// 자바
java.lang.Object.getClass()
```









#코틀린/Kotlin in Action#