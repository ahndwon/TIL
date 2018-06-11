# 5장 람다로 프로그래밍
## 5.1 람다 식과 멤버 참조
### 5.1.1 람다 소개: 코드 블록을 함수 인자로 넘기기
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
- 람다를 따로 선언해서 변수에 저장할 수도 있다
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

// 코틀린은 함수 호출 시 맨 뒤 인자가 람다 식이면 괄호 밖으로 꺼내는 문법 관습이 있다.
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
- 원소를 변환하지는 못한다. 변환은 map 사용
```java
val list = listOf(1, 2, 3, 4)
println(list.filter { it % 2 == 0 }
```

**map**
- 주어진 람다를 컬렉션의 각 원소에 적용한 결과를 모아서 새 컬렉션을 만든다.
```java
val list = listOf(1,2,3,4)
println(list.map { it * it })
```


### 5.2.2 all, any, count, find: 컬렉션에 술어 적용
**all, any**
- 컬렉션에 대해 자주 수행하는 연산으로 컬렉션의 모든 원소가 어떤 조건을 만족하는 연산
```java
val canBeInClub27 = { p: Person -> p.age <= 27 }

>>> val people = listOf(Person("Alice", 27), Person("Bob", 31))
>>> println(people.all(canBeInClub27))
false

>>> println(people.any(canBeInClub27))
true
```
- `any`는 `!all`과 같다
- `all`는 `!any`과 같다
- 하지만 가독성을 위해 앞에 !를 붙이지 않는게 낫다.

**count**
size와 달리 조건을 만족하는 모든 원소가 들어가는 중간 컬렉션이 생기지 않기 때문에
조건을 만족하는 원소를 따로 저장하지 않는 count가 더 효율적이다.

**find, firstOrNull**
```java
>>> val people = listOf(Person("Alice", 27), Person("Bob", 31))
>>> println(People.find(canBeInClub27))
Person(name=Alice, age=27)
```
- 조건을 가장 먼저 만족하는 원소를 반환
- 없을 경우 null을 반환함
- find 와 firstOrNull은 같다.

### 5.2.3 groupBy: 리스트를 여러 그룹을 이뤄진 맵으로 변경
- 전달받은 파라미터를 key로 그에 해당하는 원소들을 value로 하여 컬렉션을 map으로 그루핑 해준다.
```java
>>> val people = listOf(Person("Alice", 31), Person("Bob", 29), Person("Carol", 31))
>>> println(people.groupBy { it.age })

{ 29=[Person(name=Bob, age=29)], 31 = [Person(name=Alice, age=31), Person(name=Carol, age=31)] }
```

### 5.2.4 flatMap과 flatten: 중첩된 컬렉션 안의 원소 처리
**flatMap**
-  인자로 주어진 람다를 컬렉션의 모든 객체에 적용하고(mapping) 람다를 적용한 결과 얻어지는 여러 리스트를 한 리스트로 모은다(flatten).
```java
class Book(val title: String, val authors: List<String>)
books.flatMap { it.authors }.toSet()


>>> val strings = listOf("abc", "def")
>>> println(strings.flatMap { it.toList() }
[a, b, c, d, e, f]
```
- toList() 를 문자열에 적용하면 그 문자열에 속한 모든 문자로 이뤄진 리스트 반환함


## 5.3 지연 계산(lazy) 컬렉션 연산
**sequence**
```java
// 문제점 : 연쇄 호출로 인해 리스트가 2개 생성되기 때문에 원소가 많아질수록 효율이 떨어짐
people.map(Person::name).filter { it.startsWith("A") }

// 중간 결과를 저장하는 컬렉션이 생성되지 않기 때문에 성능이 좋다
people.asSequence()
	.map(Person::name)
	.filter { it.startsWith("A") }
	.toList ()
```
- 코틀린 지연 계산 시퀀스는 Sequence 인터페이스에서 시작한다
	- 한 번에 하나씩 열거될 수 있는 원소의 시퀀스를 표현한다.
	- Sequence 안에는 iterator라는 단 하나의 메소드가 있다. 그 메소드를 통해 시퀀스로부터 원소 값을 얻을 수 있다.
	- 장점 : 시퀀스의 원소는 필요할 때만 계산이 되기 때문에 중간 처리 결과를 저장하지 않는다.
	- asSequence 확장 함수를 통해 어떤 컬렉션이든 시퀀스로 바꿀 수 있다.
- 시퀀스 원소를 인덱스를 사용해 접근하는 등 다른 API 메소드가 필요하면 시퀀스를 리스트로 변환해야 한다.


### 5.3.1 시퀀스 연산 실행: 중간 연산과 최종 연산
- 중간 연산(intermediate)
	- 다른 시퀀스 반환함
	- 그 시퀀스는 최초 시퀀스의 원소를 변환하는 방법을 안다
	- 중간 연산은 항상 지연 연산된다
- 최종 연산(terminal)
	- 결과를 반환한다.
	- 중간 연산에서 연기 됐던 모든 계산이 수행된다.
	- 결과는 최초 컬렉션에 대해 변환을 적용한 시퀀스로부터 일련의 계산을 수행해 얻을 수  있는 컬렉션이나 원소, 숫자 또는 객체다.
``` java
listof(1,2,3,4).asSequence()
		.map { print("map($it) "); it * it }  // 중간연산
		.filter { print("filter($it) "); it % 2 == 0 } // 중간연산	
		.toList() // 최종연산
```

![](chapter5_%E1%84%85%E1%85%A1%E1%86%B7%E1%84%83%E1%85%A1%E1%84%85%E1%85%A9_)%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%85%E1%85%A2%E1%84%86%E1%85%B5%E1%86%BC/EA39A122-27DF-47F3-8F71-EB0871F6AF8F.png)

- 코틀린의 시퀀스는 자바의 스트림과 비슷하다.


### 5.3.2 시퀀스 만들기
- 컬렉션 -> sequence : asSequence()
- generateSequence: 이전의 원소를 인자로 받아 다음 원소를 계산한다.

```java
>>> val naturalNumbers = generateSequence(0) { it + 1 }
>>> val numbersTo100 = naturalNumbers.takeWhile { it <=100 }
>>> println (numbersTo100.sum())
5050
```


## 5.4 자바 함수형 인터페이스 활용
`button.setOnClickListener { /* 클릭 시 수행할 동작 */ }`

```java
button.setOnClickListener( new OnClickListener() {
	@Override
	public void onClick(View v) {
		...
	}
}

button.setOnClickListener { 
	void onClick(View v);
}

button.setOnClickListener { view -> 

}
```
- SAM(Single Abstract Method) 인터페이스 or Functional Inteface만 람다로 사용가능


### 5.4.1 자바 메소드에 람다를 인자로 전달
- 함수형 인터페이스를 인자로 원하는 자바 메소드에 코틀린 람다를 전달할 수 있다.
```java
// Runnable을 파라미터로 받는 자바 메소드
void postponeComputation(int delay, Runnable computation);

// 코틀린 컴파일러가 람다를 Runnable 인스턴스로 변환해준다
postponeComputation(1000) { println(42) }
```
- 무명 객체를 사용하면 매번 호출할 때마다 새로운 객체가 생성되므로 람다가 효율적이다.
	- 하지만 람다도 주변 영역의 변수를 포획하면 매번 호출시 새로운 인스턴스가 생성된다.

### 5.4.2 SAM 생성자 : 람다를 함수형 인터페이스로 명시적으로 변경
- 컴파일러가 자동으로 람다를 함수형 인터페이스 무명 클래스로 바꾸지 못하는 경우 SAM 생성자를 사용하면 된다.
	- ex) 함수형 인터페이스의 인스턴스를 반환하는 메소드는 람다를 직접 반환할 수 없고 SAM 생성자를 사용해야 한다
```java
fun createAllDoneRunnable(): Runnable {
	return Runnable { println("All Done!") }
}

>>> createAllDoneRunnable().run()
All done!
```


- 람다로 생성한 함수형 인터페이스 인스턴스를 변수에 저장해야 하는 경우에도 SAM 생성자를 사용할 수 있다. 
	- 여러 버튼에 같은 리스너를 적용하고 싶다면 다음 리스트처럼 SAM생성자를 통해 람다를 함수형 인터페이스 인스턴스로 만들어서 변수에 저장 활용할 수 있다.
```java
val listener = OnClickListener { view - > 
	val text = when (view.id) {
		R.id.button1 -> "First button"
		R.id.button2 -> "Second button"
		else -> "Unknown button"
	}
	toast(text)
{

button1.setOnClickListener(listener)
button2.setOnClickListener(listener)
```


**람다 안에서 this**
- 람다에는 인스턴스 자신을 가리키는 this가 없다
	- 객체가 아니므로 람다를 참조 할 수 없다.
- 람다 안에서 this는 그 람다를 둘러싼 클래스의 인스턴스를 가리킨다.


## 5.5 수신 객체 지정 람다: with와 apply
### 5.5.1 with 함수
- 객체의 이름을 반복하지 않고 그 객체에 대해 다양한 연산을 수행함
```java
// with() 사용 X
fun alphabet(): String {
	val result = StringBuilder()
	for (letter in 'A'...'Z') {
		result.append(letter)
	}
	result.append("\nNow I know the alphabet!")
	return result.toString()
}
>>> println(alphabet())
ABCDEFGHIJKLMNOPQRSTUVWYZ
Now I know the alphabet!

// with() 사용 O
fun alphabet(): String
	val stringBuilder = StringBuilder()
	return with(stringBuilder) {
		for (letter in 'A'..'Z') {
			this.append(letter)
		}
		append("\nNow I know the alphabet!")
		this.toString()
	}
}	

// 축약
fun alphabet() = with(StringBuilder()) {
	for (letter in 'A'..'Z') {
		append(letter)
	}
	append("\nNow I know the alphabet!")
	toString()
}
```
- with는 파리미터가 2개이다
	1. stringBuilder
	2. 람다
	- 뒤의 람다를 괄호 밖으로 빼서 사용

- this가 함수의 수신 객체를 가리킨다는 점에서 확장 함수와 비슷( 확장 함수는 확장하는 타입의 인스턴스를 가리킨다)

**메소드 이름 충돌**
OuterClass에 정의된 함수를 호출하기 위해선 `this@OuterClass.toString()`와 같이 명시해주면 된다.


### 5.5.2 apply 함수
- with와 비슷하지만 항상 자신에게 전달된 객체를 반환한다는 점이 다르다
```java
fun alphabet() = StringBuilder().apply {
	for (letter in 'A'..'Z') {
		append(letter)
	}
	append("\nNow I know the alphabet!")
}.toString()
```

- 객체의 인스턴스를 만들면서 즉시 프로퍼티 중 일부를 초기화해야 하는 경우 유용
- DSL(영역 특화 언어)를 만들 때도 유용


## 5.6 요약
- 람다를 사용하면 코드 조각을 다른 함수에게 인자로 넘길 수 있다.
- 코틀린에서는 람다가 함수 인자인 경우 괄호 밖으로 람다를 빼낼 수 있고, 람다의 인자가 단 하나뿐인 경우 인자 이름을 지정하지 않고 it이라는 디폴트 이름을 부를 수 있다.
- 람다 안에 있는 코드는 그 람다가 들어있는 바깥 함수의 변수를 읽거나 쓸 수 있다.
- 메소드, 생성자, 프로퍼티의 이름 앞에 ::을 붙이면 각각에 대한 참조를 만들 수 있다. 그런 참조를 람다 대신 다른 함수에게 넘길 수 있다.
- filter, map, all, any 등의 함수를 활용하면 컬렉션에 대한 대부분의 연산을 직접 원소를 이터레이션하지 않고 수행할 수 있다.
- 시퀀스를 사용하면 중간 결과를 담는 컬렉션을 생성하지 않고도 컬렉션에 대한 여러 연산을 조합할 수 있다.
- 함수형 인터페이스를 인자로 받는 자바 함수를 호출할 경우 람다를 함수형 인터페이스 인자 대신 넘길 수 있다.
- 수신 객체 지정 람다를 사용하면 람다 안에서 미리 정해둔 수신 객체의 메소드를 직접 호출할 수 있다.
- 표준 라이브러리의 with 함수를 사용하면 어떤 객체에 대한 참조를 반복해서 언급하지 않으면서 그 객체의 메소드를 호출할 수 있다. apply를 사용하면 어떤 객체라도 빌더 스타일의  API를 사용해 생성하고 초기화할 수 있다.


#코틀린/Kotlin in Action#
