# 1장 - 코틀린 소개
##  코틀린이란 무엇인가?
- 자바 플랫폼에서 돌아가는 새로운 프로그래밍 언어
- 자바 코드와의 상호운용성(interoperability)를 중시한다.
- 기존 자바 라이브러리나 프레임워크와 함께 잘 작동하며 성능도 자바와 같은 수준

## 1.1 코틀린 맛보기
http://try.kotl.in

```java
data class Person(val name: String, val age: Int? = null)

fun main(args: Array<String>) {
	val persons = listOf(Person("영희"), Person("철수", age = 29))
	val oldest = persons.maxBy { it.age ?: 0 }
	println("나이가 가장 많은 사람: $oldest")
}							
```

**?: 연산자**
?: 연산자 (엘비스 연산자) - 변수 값이 null인 경우 0을 반환하고 그렇지 않은 경우 변수의 값을 반환함

## 1.2 코틀린의 주요 특성
### 1.2.1 대상 플랫폼 : 서버, 안드로이드 등 자바가 실행되는 모든 곳
**코틀린을 활용할 수 있는 일반적인 영역**

- 서버상의 코드 ( 웹 애플리케이션 백엔드 )
- 안드로이드 기반의 모바일 애플리케이션

인텔의 멀티 OS 엔진을 사용하면 iOS 디바이스에서도 코틀린 사용 가능
자바스크립트로도 코틀린 컴파일 가능

-> 다양한 환경에서 활용 가능

### 정적 타입 지정 언어
자바와 마찬가지로 코틀린은 정적타입 (statically typed) 지정 언어다.
- **statically typed** ( 정적 타입 ) -> 모든 프로그램 구성 요소의 타입을 컴파일 시점에 알 수 있고 프로그램 안에서 객체의 필드나 메소드를 사용할 때마다 컴파일러 타입을 검증해준다는 뜻
- **dynamically typed** (동적 타입) -> Groovy, JRuby, Javascript, Python 가 대표적 언어,
	- 장점 :  타입과 관계없이 모든 값을 변수에 넣을 수 있고, 메소드나 필드 접근에 대한 검증이 실행 시점에 일어나며, 그에 따른 코드가 더 짧아지고 데이터 구조를 더 유연하게 생성하고 사용할 수 있다.
	- 단점 : 이름을 잘못 입력하는 등의 실수도 컴파일 시 걸러내지 못하고 실행 시점(Runtime)에 오류가 발생함


코틀린은 정적타입 언어지만 타입을 추론 할 수 있다.
```java
	var x : Int = 1 // 타입을 명시
	var x = 1 // 컴파일러가 타입을 추론함
```


**정적 타입 지정의 장점**
- 성능 - 실행 시점에 어떤 메소드를 호출할지 알아내는 과정이 필요 없으므로 메소도 호출이 빠름
- 신뢰성 - 컴파일러가  프로그램의 정확성을 검증하기 때문에 실행 시 프로그램이 오류로 중단될 가능성이 적어짐
- 유지 보수성 - 코드에서 다루는 객체가 어떤 티입에 속하는지 알 수 있기 때문에 처음 보는 코드를 다룰 때도 더 쉽다.
- 도구 지원 - 정적 타입 지정을 활용하면 더 안전하게 리팩토링 할 수 있고 도구는 더 정확한 코드 완성 기능을 제공할 수 있으며, IDE의 다른 지원 기능도 더 잘 만들 수 있다.

**코틀린 특징**
- 코틀린은 nullable type을 지원 -> 널 포인터 예외 발생 여부를 체크하기 때문에 프로그램 신뢰성 향상
- 코틀린은 함수형 프로그래밍 언어이다.


### 함수형 프로그래밍과 객체지향 프로그래밍
**함수형 프로그래밍의 핵심 개념**
- 일급 시민(first class)인 함수 - 함수를 일반 값처럼 다룰 수 있다. 함수를 변수에 저장할 수 있고, 함수를 인자로 다른 함수에 전달 할 수 있으며, 함수에서 새로운 함수를 만들어서 반환할 수 있다.
- 불변성(immutability) - 함수형 프로그래밍에서는 일단 만들어지고 나면 내부 상태가 절대로 바뀌지 않는 불변 객체를 사용해 프로그램을 작성한다.
- 부수 효과(side effect) 없음 - 입력이 같으면 항상 같은 출력을 내놓고 다른 객체의 상태를 변경하지 않으며, 함수 외부나 다른 바깥 환경과 상호작용하지 않는 순수 함수를 사용

자바에서도 함수형 프로그래밍이 가능하긴 함 (람다)

## 1.3 코틀린 응용
### 1.3.1 코틀린 서버 프로그래밍
서버 프로그래밍의 분야
- 브라우저에 HTML 페이지를 돌려주는 웹 애플리케이션
- 모바일 애플리케이션에게 HTTP를 통해 JSON API를 제공하는 백엔드 애플리케이션
- RPC(Remote Procedure Call) 프로토콜을 통해 서로 통신하는 작은 서비스들로 이뤄진 마이크로서비스

원격 프로시저 호출(영어: remote procedure call, 리모트 프로시저 콜, RPC)은 별도의 원격 제어를 위한 코딩 없이 다른 주소 공간에서 함수나 프로시저를 실행할 수 있게하는 프로세스 간 통신 기술
다시 말해, 원격 프로시저 호출을 이용하면 프로그래머는 함수가 실행 프로그램에 로컬 위치에 있든 원격 위치에 있든 동일한 코드를 이용할 수 있다.

### 1.3.2 코틀린 안드로이드 프로그래밍
코틀린을 통해 보일러플레이트 제거 가능
Anko  라이브러리를 사용하면 수많은 안드로이드 API에 대한 코틀린 어댑터를 제공받을 수 있다.
DSL - Domain Specific Language

Anko Layout 예
[image:70F5A7EC-8886-4F79-BAD1-41F227DD5D1B-688-0000034BAE35B6F0/5D88292B-11F7-4D93-8361-233F75E52507.png]

Kotlin Android Extensions 활용
빌드 그래들에 아래 플러그인 추가
`apply plugin: 'kotlin-android-extensions'`
[image:29CF0315-BEE9-4F15-A214-547FB90A2E0F-688-00000354A86DE1CB/77C718D3-A2D7-4F6D-A08A-2BB66546BBD6.png]
					<  사용  전 >

[image:6B30678C-496F-46E3-ACD2-8F40C5F82D46-688-000003661AC461C1/E4A33AB6-C28B-463D-A716-8DC5697FFA2E.png]
					<  사용   후  >

**안드로이도 개발에 코틀린 사용시 장점**
- Null Pointer Exception 방지 가능
- 자바 6와 완전 호환되어 호환성 문제가 발생하지 않는다.

## 코틀린의 철학
### 1.4.1 실용성
코틀린은 학문 연구로서의 사용보단 실제 업무에 더 적합하다.
이미 다른 프로그래 언어가 채택한 검증된 해법과 기능에 의존하므로 안전하고 배우기 더 쉽다.

### 1.4.2 간결성
코틀린은 코드를 간결하게 작성하게 해줘서 의도롤 쉽게 파알 할 수 있고 보일러플레이트가 적다.
게터, 세터, 생성자 묵시적으로 제공하기 때문에 코드가 간결함.

### 1.4.3 안전성
안전성 -> 프로그램에서 발생 할 수 있는 오류 중에서 일부 유형의 오류를 프로그램 설계가 원천적으로 방지

안전성과 생산성 사이에는 trade-off 관계 성립

코틀린은 nullable로 NullPointer Exception 안전성 보장

```java
val s: String? = null // null 가능
val s2: String = ""	// null 불가능
```

ClassCastException에 대해서도 타입 검사 가능

```java
if (value is String)	// 타입 검사
	println(value.toUpperCase())  // 해당 타입의 메소드 사용
```

### 1.4.4 상호운용성
**자바와 코틀린은 자유롭게 오갈 수 있다**
- 자바와 코틀린 소스 파일 자유롭게 내비게이션 가능
- 여러 언어로 이뤄진 프로젝트를 디버깅하고 서로 다른 언어로 작성된 코드를 언어와 관계없이 한 단계씩 실행할 수 있다.
- 자바 메소드를 리팩토링해도 그 메소드와 관련 있는 코틀린 코드까지 제대로 변경된다. 역으로 코틀린 메소도 리팩토링해도 자바 코드까지 모두 자동으로 변경됨

## 1.5 코틀린 도구 사용
### 1.5.1 코틀린 코드 컴파일
[image:5EB26403-4AF8-48EA-AA32-689F11ED8E97-688-0000094A8E3B3AF5/E2DAA9D3-445F-4247-B380-43FFDF544824.png]

코틀린 컴파일러로 컴파일한 코드는 코틀린 런타임 라이브러리에 의존한다.
코틀린은 메이븐, 그레이들, 앤트 등의 빌드 시스템이랑도 호환됨.


```java
// $ kotlinc Hello.kt 
//  => HelloKt.class 
// $ javap -c HelloKt.class

// $ kotlin HelloKt
// $ java HelloKt

/*
Exception in thread "main" java.lang.NoClassDefFoundError: kotlin/jvm/internal/Intrinsics
	at HelloKt.main(Hello.kt)
Caused by: java.lang.ClassNotFoundException: kotlin.jvm.internal.Intrinsics
	at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
	at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:338)
	at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
	... 1 more
*/

// 코틀린의 새로운 기능에 대한 부분이 포함되어야 한다.
//  $ kotlin <- kotlin의 기능이 포함된 클래스가 로드된다. - 500KB
//  $ java   <- kotlin의 기능이 로드되지 않는다.

//  $ kotlinc -include-runtime Hello.kt -d Hello.jar
//  $ java -jar Hello.jar

```


### 1.5.3 대화형 쉘
REPL (read-eval-print loop) 
사용법 : kotlinc 를 아무 인자 없이 실행하면 됨


## 요약
- 코틀린 -> 타입 추론을 지원하는 정적 타입 지정 언어
- 코틀린은 객체지향과 함수형 프로그래밍 스타일을 모두 지원
- 코틀린을 서버 애플리케이션 개발에 활용 가능
- 코틀린은 안드로이드에도 활용 가능
- 코틀린은 실용적이고 안전하며, 간결하고 상호운용성이 좋다. NullPointerException등 다양한 오류를 방지하고 코드를 간결하게 해주며 자바의 기존 라이브러리 모두 사용 가능하다.
