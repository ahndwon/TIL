# Duck_Typing
* 동적 타이핑의 한 종류
* 객체의 변수 및 메소드의 집합이 객체의 타입을 결정하는 것
* 클래스 상속이나 인터페이스 구현으로 타입을 구분하는 대신, 객체가 어떤 타입에 걸맞은 변수와 메소드를 지니면 객체를 해당 타입에 속하는 것으로 간주함.

## 유래
덕 테스트에서 유래한다.
`만약 어떤 새가 오리처럼 걷고, 헤엄치고 꽥꽥거리는 소리를 낸다면 나는 그 새를 오리라고 부를 것이다.`

덕 타이핑 에서는 객체의 타입보다 객체가 사용되는 양상이 더 중요하다. 

**Example**
덕 타이핑이 없는 프로그래밍 언어로는 `오리`라는 타입의 객체를 인자로 받아 객체의 `걷기` 메소드와 `꽥꽥거리기` 메소드를 차례로 호출하는 함수를 만들 수 있다.
하지만 같은 함수를 덕타이핑이 지원되는 언어에서는 인자로 받는 객체의 타입을 검사하지 않도록 만들 수 있다.
-> 메소드만 제대로 구현되어 있으면 런타임 에러가 발생하지 않고 사용 가능

덕타이핑은 정적 타이핑에 비해 타입의 제한에서 자유롭다.


### 예제
```python
class Duck:
        def quack(self): print u"꽥꽥!"
        def feathers(self): print u"오리에게 흰색, 회색 깃털이 있습니다."

class Person:
        def quack(self): print u"이 사람이 오리를 흉내내네요."
        def feathers(self): print u"사람은 바닥에서 깃털을 주어서 보여 줍니다."

def in_the_forest(duck):
        duck.quack()
        duck.feathers()

def game():
        donald = Duck()
        john = Person()
        in_the_forest(donald)
        in_the_forest(john)
```

출처 : [덕 타이핑 - 위키백과, 우리 모두의 백과사전](https://ko.wikipedia.org/wiki/%EB%8D%95_%ED%83%80%EC%9D%B4%ED%95%91)


#공통