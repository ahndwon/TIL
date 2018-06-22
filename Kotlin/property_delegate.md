# Property Delegate
**프로퍼티 위임**
프로퍼티의 접근과  초기화를 직접하지 않고 다른 객체에 위임한다.


## 흔히 쓰이는 프로퍼티 위임 방식들
1. **lazy**
지연 초기화
객체가 생성되는 시점에 초기화하는 것이 아니라,
처음 접근하는 시점에 초기화하고 싶을 때 사용
코틀린 표준 라이브러리가 제공
```java
class User {
    // var name: String? = null
    // null을 가질 수 있다.
    //  : 사용하기 전에 반드시 null 체크를 해야 한다.

    // lateinit var name: String
    // 초기화되지 않은 시점에 접근하면 예외가 발생한다.
    //  name은 var 이다.
    //  => 런타임에 값이 변경될 수 있다.

    // User 객체가 생성된 이후에 처음 접근되는 시점에 초기화가 수행된다.
    val name: String by lazy {
        "Tom"
        // onCreate - findViewById(R.id.button)
    }
}
```

2. **KVO (Key Value Observing) Programming**
- iOS와 ObjC에서도 사용
- 모델 객체의 어떤 값이 변경되었을 경우 이를 UI에 반영하기 위해서 컨트롤러는 모델 객체에 Observing을 도입하여 델리게이트에 특정 메시지를 보내 처리할 수 있도록 하는 것
- 프로퍼티의 값의 변경을 통보받는 기술

**특징**
- 일대일, 일대다 관계에 대해서도 Observing을 적용할 수 있습니다.
- 모델 데이터에 반영되는 구조를 가진 앱은 코코아 바인딩을 사용하면 코드 작성을 최소화 할 수 있습니다.

```java
class TextView {
    var text: String = ""
        set(value) {
            println("Update TextView's text to $value")
        }
}

class Activity {
    // name의 값이 변경될 때마다, nameTextView의 내용을 업데이트 하고 싶다.
    // var name: String = ""
    var name by Delegates.observable("") { _, old, new ->
        println("$old -> $new")
        nameTextView.text = new
    }

    val nameTextView = TextView()
}

fun main(args: Array<String>) {
    val activity = Activity()
    activity.name = "Tom"
    // activity.nameTextView.text = "Tom"

    activity.name = "Bob"
    // activity.nameTextView.text = "Bob"
}
```

3. **KVC (Key Value Coding) Programming**
- 어플리케이션이 정보를 의미하는 문자열(또는 키)를 사용하여 간접적으로 객체의 속성값을 접근하는 매커니즘
- Key-value coding은 key-value observing, Cocoa bindings, Core Data와 함께 작업하는 기본적인 기술
- iOS와 ObjC에서도 사용

**특징**
- 키가 되는 문자열은 런타임시 결정됩니다.
- 소스 코드가 간결해지면서 유지보수도 쉬워집니다.
- 클래스간 의존성이 낮아집니다.

```java
class User(map: Map<String, Any>) {
    val name: String by map    // name = map["name"]
    val age: Int by map        // age = map["age"]
    // val address: String by map // address = map["address"]

    override fun toString(): String {
        return "User($name, $age)"
    }
}

fun main(args: Array<String>) {
    val map = mapOf("name" to "Tom", "age" to 42, "address" to "Suwon")
    val user = User(map)

    println(user)
}

```


4. **vetoable: validation**
- 특정한 프로퍼티가 가져야하는 제약을 델리게이트를 통해서 처리할 수 있다.
```java
class User {
    var password: String by Delegates.vetoable("Hello") { _, old, new -> 
		println("$old -> $new")
    	new.length >= 5
    }

// 특수 문자
    // 숫자가 반드시 포함되어 있어야 한다.
    // 길이가 5 이상이어야 한다.
}

fun main(args: Array<String>) {
    val user = User()
    user.password = "Showme"
    user.password = "a"

    println(user.password)
}
```


#코틀린#