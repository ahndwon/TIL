# 구조 분해 선언과 component 함수
## 구조 분해 선언 (destructuring declaration)
구조 분해를 사용하면 복합적인 값을 분해해서 여러 다른 변수를 한꺼번에 초기화할 수 있다.

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

## 구조 분해 선언과 루프
- 사용하지 않는 변수는 언더바
`val (_, status) = getResult()`
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

#코틀린#