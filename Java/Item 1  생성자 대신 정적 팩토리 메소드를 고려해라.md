# Item 1 : 생성자 대신 정적 팩토리 메소드를 고려해라
생성자를 통해 인스턴스를 생성 하는게 기존 방법이다. 하지만 정적 팩토리 메소드를 통해서도 인스턴스를 생성할 수 있다. 정적 팩토리 메소드는 디자인 패턴의 **팩토리 메소드 패턴**과 다르다. 

##  정적 팩토리 메소드 장점
1. 생성자와 달리 **이름**이 있기 때문에 이름을 잘 지으면 메소드가 행하는 행동을 유추하기 쉽다.
```java
// prime을 반환
BigInteger(int, int, Random)
	
// 정적 팩토리 메소드
// 행위가 이름을 통해 드러난다.
BingInteger.probablePrime()
```

하나의 클래스는 주어진 시그니처로 오직 단 하나의 생성자만 가질 수 있다.
이것을 극복하기 위해 파라미터의 갯수나 타입이 다른 파라미터를 사용하는데 이것은 사용자에게 혼란을 야기한다.  -> 사용자가 어떤 생성자를 이용해야되는지 기억 불가

따라서 이름을 가진 정적 팩토리 메소드를 통해 생성자의 이런 단점을 극복 할 수 있다.


2. 생성자와 달리 매번 호출될때마다 새로운 객체를 생성하지 않아도 된다.
정적 팩토리 메소드는 새로운 객체를 생성하는 대신 기존에 생성된 인스턴스를 재사용하거나 캐싱하여 쓸데 없는 객체의 생성을 방지한다. 이 기법은  **Flyweight** 패턴과 비슷하다.
-> ex) Boolean.valueOf(boolean) : 절대 객체를 생성하지 않는다.

정적 팩토리 메소드가 같은 객체를 반환하도록 하는 능력은 클래스가 인스턴스를 엄격히 관리할 수 있게 해준다. 인스턴스 관리를 통해 싱글턴이나 비인스턴스화를 보장하거나 동일한 인스턴스가 중복되지 않는걸 보장할 수 있다.

3. 생성자와 달리 정적 팩토리 메소드는 반환형의 하위 타입인 객체를 반환할 수 있다.
-> 이것은 유연한 반환을 제공한다.

이 유연성을 이용하여 API는 클래스를 public으로 만들지 않아도 객체를 반환할 수 있다.
-> 인터페이스 기반 프레임워크에서 많이 사용된다.
자바 8 이전엔 인터페이스는 정적 메소드를 가질 수 없었다.

4. 반환 타입이 파라미터에 따라 달라질 수 있다.
ex) 자바의 EnumSet 클래스는 64개 이하의 원소를 가질때 RegularEnumSet 인스턴스를 반환하고 65개 이상일 땐 long 배열로 이루어진 JumboEnumSet을 반환한다.
	
5. 클래스를 가지고 있는 메소드가 써지는 동안에 반환하는 객체의 클래스가 존재할 필요가 없다.

## 정적 팩토리 메소드 단점
1. 주된 한계점은 생성자가 public이거나 protected가 아닌 클래스는 종속될 수 없다는 점이다.
하지만 이런 단점은 오히려 장점이 될 수도 있다 -> 프로그래머가 상속 대신 구성을 이용하도록 한다.

2. 프로그래머가 찾기 힘들다
-> API 문서에 생성자 처럼 돋보이지 않는다. 그러므로 클래스가 정적 팩토리 메소드를 통해 인스턴스화해야 하는 사실을 알기 힘들다. 
이 문제점을 네이밍 컨벤션을 통해 극복하고 있다.

	- **from** - 하나의 파라미터를 받고 상응하는 타입의 인스턴스를 반환
		`Date d = Date.from(instant)`
	- **of** - 여러개의 파라미터를 받아서 인스턴스 반환
		`Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);`
	- **valueof** - from과 of의 좀 더 장황한 대안
		`BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);`
	- **instance** , **getInstance**- 파라미터에 따른 인스턴스를 반환한다. 하지만 같은 값을 가진다고 볼 순 없다.
		`StackWalker luke = StackWalker.getInstance(options);`
	- **create** or **newInstance** - instance랑 getInstance 와 비슷하지만 새로운 인스턴스가 반환됨을 보장한다는 점에서 다르다.
		`Object newArray = Array.newInstance(classObject, arrayLen);`
	- **getType** - getInstance와 비슷하다. 하지만 팩토리 메소드가 다른 클래스에 있을 경우 사용한다. Type은 팩토리 메소드에 의해 반환되는 객체의 타입이다.
		`FileStore fs = Files.getFileStroe(path);`
	- **newType** - newInstance와 비슷하다. 하지만 팩토리 메소드가 다른 클래스에 있을 경우 사용. Type은 팩토리 메소드에 의해 반환되는 객체의 타입이다.
		`BuffereReader br = Files.newBufferedReader(path);`
	- **type** - getType과 newType의 좀 더 간결한 대안
		`List<Complaint> litany = Collections.list(legacyLitany);`

결론적으로 정적 팩토리 메소드나 public 생성자들은 각자 자기만의 쓰임새가 있고 상대적 이점에 대해 이해 하는게 좋다. 정적 팩토리 메소드가 더 선호 되는 경우가 많으므로 항상 정적 팩토리 메소드의 사용을 고려해보는 것이 좋다.

#더더랩JAVA/Effective_Java 