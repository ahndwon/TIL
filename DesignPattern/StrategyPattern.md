# StrategyPattern
전략패턴은 정책 패턴이라고도 불리며 실행 중에 알고르짐을 선택할 수 있게 하는 행위 소프트웨어 디자인 패턴이다

전략 패턴은
- 특정한 계열의 알고리즘들을 정의하고
- 각 알고리즘을 캡슐화하며
- 이 알고리즘들을 해당 계열 안에서 상호 교체가 가능하게 만든다.

![](StrategyPattern/A95786F2-0EDB-4DAB-B690-021B3A990646.png)
[전략 패턴](https://upload.wikimedia.org/wikipedia/commons/4/45/W3sDesign_Strategy_Design_Pattern_UML.jpg)

전략은 알고리즘을 사용하는 클라이언트와는 독립적으로 다양하게 만든다. 

**전략 패턴 구현 순서**
1. 알고리즘 캡슐화
2. 캡슐화한 알고리즘을 인터페이스에 위임



## 구현(코틀린)
```java
interface BillingStrategy {
    fun getPrice(rawPrice: Double) : Double
}

class HappyHourStrategy: BillingStrategy {
    override fun getPrice(rawPrice: Double): Double = rawPrice * 0.5
}

class NormalStrategy: BillingStrategy {
    override fun getPrice(rawPrice: Double): Double {
        return rawPrice
    }
}

class Customer(var strategy: BillingStrategy) {
    private val drinks = ArrayList<Double>()

    fun add(price: Double, quantity: Int) {
        drinks.add(strategy.getPrice(price * quantity))
    }

    fun printBill() {
        var sum : Double = 0.0
        for (price in drinks) {
            sum += price
        }

        *println*("Total due: $sum")
        drinks.clear()
    }
}

fun main(args: Array<String>) {
    val firstCustomer = Customer(NormalStrategy())

    // Normal billing
    firstCustomer.add(1.0, 1)

    // Start Happy Hour
    firstCustomer.strategy = HappyHourStrategy()
    firstCustomer.add(1.0, 2)

    // New Customer
    val secondCustomer = Customer(HappyHourStrategy())
    secondCustomer.add(0.8, 1)

    // The Customer Pays
    firstCustomer.printBill()

    // End Happy Hour
    secondCustomer.strategy = NormalStrategy()
    secondCustomer.add(1.3, 2)
    secondCustomer.add(2.5, 1)
    secondCustomer.printBill()
}
```












#디자인패턴