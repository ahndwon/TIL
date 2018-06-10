# 5장 람다로 프로그래밍
## 5.1 람다 식과 멤버 참조
### 5.1.1 람다 소개: 코드 블록을 함수 이자로 넘기기
일련의 동작을 변수에 저장하거나 다른 함수에 넘겨야 하는 경우
- 자바 : 자바8 전까지 무명 내부 클래스로 해결 -> 자바8 람다 사용
- 무명 내부 클래스 문제점: 보일러플레이트 -> 가독성 떨어짐, 코드 복잡


### 5.1.2 람다와 컬렉션

**람다를 이용하지 않고 컬렉션 직접 검색**
```java
fun findTheOldest (people: List<Person>) {
	var maxAge = 0
	var theOldest: Person? = null
	for (person in people) {
		if (person.age > maxAge) {
			maxAge = person.age
			theOldest = person
		}
	}
}
```

**람다를 사용한 컬렉션 검색**
```java
>>> val people = listOf*(Person("Alcie", 29), Person("Bob:, 31))
>>> println(people.maxBy { it.age })
```
`{ it.age }`는 바로 비교에 사용할 값을 돌려주는 함수
`people.maxBy(Person::age)`와 같이 멤버 참조를 사용할 수 도 있다.

### 5.1.3 람다 식의 문법
- 람다를 따로 선언해서 변수에 저장할 수도 있따
- 하지만 함수에 인자로 넘기면서 바로 람다를 정의하는 경우가 대부분이다
- 코틀린 람다 식은 항상 중괄호로 둘러싸여 있다
	- 인자 목록 주변에 괄호 X
	- 화살표(/->) 가 인자 목록과 람다 본문을 구분해준다.

**람다 작성법**
```java
// 코드를 줄이지 않은 기본 형태
// 문제점: 타입을 명시하지 않아도 추론 가능
//		구분자가 많아 코드 번잡
people.maxBy({ p: Person -> p.age})

// 코틀리은 함수 호출 시 맨 뒤 인자가 람다 식이면 괄호 밖으로 꺼내는 문법 관습이 있다.
people.maxBy() { p: Person -> p.age}
// 중괄호도 생략 가능
people.maxBy { p: Person -> p.age}

// 타입 생략 가능
people.maxBy { p -> p.age}
```
- 둘 이상의 람다를 인자로 받는 함수라도 맨 마지막 람다만 괄호 밖으로 빼낼 수 있다.
	- 그러므로 둘 이상의 람다를 인자로 받는 함수는 일반적인 함수 호출 구문이 낫다.

**it**
- 람다의 파라미터의 디폴트 이름
	- 코드 단축 가능
	- 단, 람다 중첩시 파라미터 명시하는 것이 더 명확하다
```java
people.maxBy { it.age }
```


### 5.1.4 현재 영역에 있는 변수에 접근
- 람다를 함수 안에서 정의하면 함수의 파라미터 뿐 아니라 람다 정의의 앞에 선언된 로컬 변수까지 람다에서 모두 사용할 수 있다.
- 코틀린은 final이지 않은 변수도 접근 가능
- 람다 안에서 바깥 변수 값 변경 가능
- **람다가 포획한 변수** - 람다 안에서 사용하는 외부 변수

### 5.1.5 멤버 참조
- :: - 멤버 참조 (member reference)
- `클래스명::멤버`
```java
val getAge = Person::age
// 위와 같다.
val getAge = { person: Person -> person.age }
```

**생성자 참조**
- :: 뒤에 클래스 이름을 넣으면 생성자 참조를 만들 수 있다.
```java
>>> val createPerson = ::Person
>>> val p = createPerson("Alice", 29)
```


**확장 함수 참조**
```java
fun Person.isAdult() = age >= 21
val predicate = Person::isAdult
```


**바운드 멤버 참조**
- 멤버 참조를 사용할 때 클래스 인스턴스를 함께 저장한 다음 나중에 그 인스턴스에 대해 멤버를 호출한다. -> 호출 시 수신 대상 객체를 별도로 지정해 줄 필요가 없다.
```java
>>> val p = Person("Dmitry", 34)
>>> val personAgeFunction = Person::age
>>> println(personAgeFunction(p))
34

>>> val dmitrysAgeFunction = p::age
>>> println(dmmitrysAgeFunction())
34
```


## 5.2 컬렉션 함수형 API
### 5.2.1 필수적인 함수: filter와 map
- filter와 map은 컬렉션을 활용할 때 기반이 되는 함수

**filter**
- 컬렉션을 이터레이션 하면서 주어진 람다에 각 원소를 넘겨서 람다가 true를 반환하는 원소만 모은다.
- 컬렉션에서 원하지 않는 원소를 제거한다.
- 원소를 변환하지는 못한다.
```java
val list = listOf(1, 2, 3, 4)
println(list.filter { it % 2 == 0 }
```

**map**
- 주어진 람다를 컬렉션의 각 원소에 적용한 결과를 모아서 새 컬렉션을 만든다.
```java
val list = listOf(1,2,3,4)
println(list.map { it * it }
```










#코틀린/Kotlin in Action#
