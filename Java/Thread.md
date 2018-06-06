# 180409  멀티쓰레드
## 멀티쓰레드

**스레드 생성 방법**
	1.  java.lang.Thread 클래스 직접 개체화
	2. Thread를 상속해서 하위 클래스 생성

## Thread 클래스로부터 직접 생성
`Thread thread = new Thread(Runnable target);`

Runnable - 인터페이스 타입이기 때문에 구현 객체를 만들어 대입해야 한다. -> run() 을 재정의

**Runnable 익명 객체 생성**
```java
Thread thread = new Thread(new Runnable() {
	public void run() {
		//....TODO
	}
});
```

**람다 사용**
```java
Thread thread = new Thread( () -> {
	//... TODO
});
```

**생성한 스레드 실행**
`thread.start();`


## 스레드의 이름
메인 스레드 - “main”
직접 생성한 스레드 - “Thread-n”

스레드 이름 변경 - `thread.setName("스레드 이름");`
스레드 이름 - `thread.getName();`

스레드 객체의 참조가 필요할때 - `Thread thread = Thread.currentThread();`


## 스레드 우선순위
멀티스레드는 동시성(Concurrency) 또는 병렬성(Parallelism)으로 실행됨.

자바 스레드 스케줄링 - 우선순위(Priority) 방식, 순환할당( Round-Robin) 방식

- 우선순위 방식 
	- 1~ 10까지의 우선순위가 부여됨 ( 1이 가장 낮고 , 10이 가장 높다)
	- 기본적으로 5가 부여됨
	- `thread.setPriority(우선순위);`


**임계영역 (Critical Section)**
	- 멀티 스레드 프로그램에서 단 하나의 스레드만 실행 할 수 있는 코드 영역
	- synchronized 로 메소드나 동기화 블록 지정 가능

**메소드**
```java
public synchronized void method() {
	//임계영역;
}
```

**동기화 블록**
```java
public void method() {
	synchronized(공유객체) {
		//임계영역
	}
}
```

	- `join()` - 다른 스레드의 종료를 기다림
	- `wait()` - 스레드 일시정지
	- `notify()` - wait()에 의해 일시 정지된 스레드 중 한개를 실행대기 상태로 만듬
	- `notifyAll()` - wait()에 의해 일시 정지된 모든 스레드를 실행대기 상태로 만듬
	-   `stop()`- 스레드의 종료를 유도함
	- `interrupt()` - 스레드가 일시정지 상태에 있을 때 InterruptedException 예외를 발생 시키는 역할. -> run() 메소드 정상 종료 가능

**데몬 스레드** - 주 스레드의 작업을 돕는 보조적인 역할을 수행하는 스레드, 주 스레드가 종료되면 데몬 스레드는 강제적으로 자동 종료됨

```java
public static void main(String[] args) {
	AutoSaveThread thread = new AutoSaveThread();
	thread.setDaemon(true);
	thread.start();
}
```

## 스레드 그룹
	- 스레드는 그룹으로 관리 할 수 있다.
		- `ThreadGroup group = new ThreadGroup(String name);`
	
## 스레드풀
- 갑작스런 스레드의 폭증을 막기 위해 사용
- 스레드풀은 작업 처리에 사용되는 스레드를 제한된 개수만큼 정해 놓고 작업 큐(Queue)에 들어오는 작업들을 하나씩 스레드가 맡아 처리한다.


			 

#더더랩JAVA