# 컬렉션과 범위 관례
코틀린에서는 인덱스 연산자도 관례를 따름
	-> 인덱스 연산자([]) 사용하여 원소를 읽을 시 자동으로 get 연산자 메소드로 변환되고 쓰는 연산은 set 연산자 메소드로 변환된다.

**get 관례**
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


**set 관례**
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


## in
객체가 컬렉션에 들어있는지 검사
(멤버십 검사(membership test))
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


## rangeTo
.. 연산자는 rangeTo 함수 호출로 컴파일된다.

`start..end` -> `start.rangeTo(end)`

`operator fun <T: Comparable<T>> T.rangeTo(that: T) : ClosedRange<T>`
- rangeTo는 아무 클래스에나 정의 가능하며 Comparable 인터페이스를 구현하면 정의할 필요가 없다
- rangeTo는 다른 산술 연산자보다 우선순위가 낮다
- 괄호로 감싸면 메소드 호출 가능
	- `(0..n).forEach { print(it) }`



## for 루프를 위한 iterator 관례
향상된 for문은 iterator()를 호출하고 그 iterator()에 대해 hasNext와 next 호출을 반복하는 식으로 동작한다.


#코틀린#







