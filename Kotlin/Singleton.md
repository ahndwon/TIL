# Singleton
**쓰임새**
- 싱글턴 객체 선언
- 동반객체(companion object)를 통한 정적 메소드 사용
- 객체 식을 통한 무명 내부 클래스 사용

## 싱글턴 패턴
- 객체를 하나만 생성하여 필요시 재생성하지 않고 기존에 생성되어있는 객체를 사용하는 패턴

### 자바의 싱글턴 패턴

**Eager initialization**
```java
public class Singleton {
    private static Singleton *instance*= new Singleton();

    private Singleton() {
        System.*out*.println("call Singleton constructor.");
    }

    public static Singleton getInstance() {
        return *instance*;
    }
}
```
문제점 : 클래스가 load 되는 시점에 생성되기 때문에 싱글턴을 많이 사용할 경우 프로그램이 느려질 수 있다.

**lazy initialization**
```java
public class LazySingleton {
    private static LazySingleton instance;
    private LazySingleton() { }

    public static LazySingleton getInstance() {
        if (*instance*== null)
            *instance*= new LazySingleton();
        return *instance*;
    }
}
```
장점 : getInstance()를 호출할 때 객체를 생성하기 때문에 객체생성 비용이 적다.
단점: 멀티쓰레드 환경에서 동기화 문제가 발생한다.


**thread safe initialization**
```java
public class ThreadSafeSingleton {
    private static ThreadSafeSingleton *instance*;
    private ThreadSafeSingleton() {}

    public static synchronized ThreadSafeSingleton getInstance() {
        if (*instance*== null)
            *instance*= new ThreadSafeSingleton();
        return *instance*;
    }
}
```
장점 : 동기화를 하기 때문에 멀티쓰레드 환경에서 안전하다
단점: 많은 쓰레드들이 getInstace()를 호출시 synchronized로 인한 성능 저하가 일어난다.


**initialization on demand holder idiom(IODH)**
```java
public class DemandHolderIdiom {
    private DemandHolderIdiom () {}

    private static class Singleton {
        private static final DemandHolderIdiom INSTANCE
= new DemandHolderIdiom();
    }

    public static DemandHolderIdiom getInstance() {
        return Singleton.*INSTANCE*;
    }
}
```
장점 : static final 키워드를 사용하면 불변 객체로 생성되기 때문에 멀티쓰레드에 대한 안정성이 보장된다.
현재 자바에서 제일 많이 쓰이는 싱글턴의 형태이다


## 코틀린의 싱글턴
```java
object Singleton {

}
```
위와 같이  object 키워드를 사용시 자바의 IODH와 같은 싱글턴을 만들 수 있다.
그러므로 보일러플레이트를 많이 제거해준다.












#코틀린/더더랩수업