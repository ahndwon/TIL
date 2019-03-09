# MVVM
## MVVM을 사용하는 이유
안드로이드는 화면 회전이 일어나면 Activity가 종료되고, 메모리에서 제거 되었다가 다시 생성됨 -> **화면 회전으로 인해 UI 데이터 유지가 어려움**

### 화면 회전에 대한 기존의 해결책
액태비티가 종료되기 직전 onSaveInstanceState() 콜백으로 상태 또는 데이터 저장, 하지만 직렬화 할 수 없는 객체는 저장 불가능

또 다른 방법은 유보된 프래그먼트(Retained Fragment)를 사용하는 방법. UI가 없는 워커(헤드리스) 프래그먼트를 사용해서 UI에 필요한 데이터를 관리하고, 프래그먼트를 setRetainInstace(true)로 설정하여 액티비티 재생성 시, 프래그먼트를 메모리에 유지(소멸시키지 않고 유보)시키는 것. 

워커 프래그먼트는 단지 액티비티에서 분리(onDetach)된 후 새로운 액티비티로 다시 호스팅(onAttach) 될 뿐 소멸과 생성을 반복하지 않는다. 하지만 프래그먼트 도입은 또 다른 엣지 케이스(일정한 범위를 넘어섰을 때 발생하는 문제)를 다뤄야 한다는 점에서 새로운 고난의 장을 여는것을 의미한다.

### ViewModel로 해결 방법
애초에 UI관련 데이터를 보관하고, 관리하기 위해 디자인 되었음.
액티비티가 재생성 되는 상황에서도 ViewModel 인스턴스를 유지함으로써 데이터를 안전하게 다룰 수 있음. 또한 데이터의 소유권을 액티비티와 프래그먼트로부터 분리시킴으로써 관심사 분리, 즉 액ㄱ티비티와 프래그먼트는 UI를 업데이트 하는 역할에 집중시킨다는 의미에서 단일 책임 원칙을 따를 수 있는 발판이 마련된 셈.


### ViewModel 수명주기
보통 ViewModel 인스턴스는 액티비티의 onCreate()에서 요청을 함. 아래와 같이 onCreate()가 여러번 호출 되어도 일관되게 유지된다.
![](MVVM/3AD8D037-4692-463A-A049-3ABD565F18CB.png)
![ViewModel lifecycle](https://cdn-images-1.medium.com/max/800/1*oW2OtsU4itFE-1njkwJ06w.png)

**ViewModel은 액티비티 스코프의 싱글톤 객체처럼 사용**할 수 있기 때문에 프래그먼트들 사이에서 ViewModel을 이용해 데이터를 쉽게 공유할 수 있다. 이는 **프래그먼트 간 데이터 공유에 더 이상 중간자 역할로서의 액티비티가 필요하지 않다는 것을 의미.**
-> 액티비티가 지나치게 많은 역할을 하는것을 막음

ViewModel은 액티비티 스코프가 완전히 종료되는 시점에 종료가 되고, 이때 onCleared() 함수가 호출됨. 이 콜백은 ViewModel 클래스의 유일한 함수이며 VieModel의 리소스를 해제하기에 적합한 곳.


## 자세하게 ViewModel 살펴보기
**ViewModel 라이브러리는 내부적으로 프래그먼트를 사용함.**
ViewModel 생성은 ViewModelProvider로만 가능하다.
액티비티나 프래그먼트에서 최초로 ViewModel을 생성할 때,  ViewModelProvider가 HolderFragment라는 프래그먼트를 생성해 액티비티에 추가한다. 이 HolderFragment가 ViewModel을 멤버 변수로 관리하며, 위에서 설명한 **프래그먼트의 setRetainInstance(true)로 프래그먼트를 유지하는 기법을 사용**

ViewModel은 유보된 프래그먼트(Retained Fragment)의 연장선에 있는 라이브러리다.

### ViewModel 생성방법
추상 클래스인 ViewModel 클래스를 상속

```kotlin
 class ChronometerViewModel : ViewModel() {
    override fun onCleared() {
        // Do somthing to clean up
        ...
    }
}
```

ViewModel 클래스는 자체적으로 어떤 기능도 포함하고 있지 않기 때문에 생성자로 생성하는 것은 의미가 없다.
ViewModelProvider를 통해 객체를 생성해야만 HolderFragment에 의해 ViewModel이 관리되며,  기기의 구성 변경에서 살아남을 수 있다.

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {        
        val chronometerModel = ViewModelProviders.of(this).get(ChronometerViewModel::class.java)
        ...
    }
}
```


커스텀 생성자를 갖는 ViewModel은 다음과 같이 ViewModelProvider.Factory 인터페이스를 사용해서 객체를 생성해야 한다.

```kotlin
class ChronometerViewModel(val initialTime: Long) : ViewModel() {
   ...
}

class ChronometerViewModelFactory(val initialTime: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Long::class.java).newInstance(initialTime)
    }
}


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val factory = ChronometerViewModelFactory(SystemClock.elapsedRealtime())
        val chronometerModel = ViewModelProviders.of(this, factory).get(ChronometerViewModel::class.java)
        ...
    }
}
```


## ViewModel 사용 시 주의점
ViewModel에 액티비티, 프래그먼트, 뷰에 대한 컨텍스트를 저장해서는 안된다. 액티비티가 재생성 될 때, ViewModel은 액티비티 수명 주기 외부에 존재하기 때문에 **UI 컨텍스트를 ViewModel에 저장한다면 메모리 누수를 발생시키는 직접적인 원인** 되기 때문.

**하지만  Application Context를 저장하는 것은 문제가 되지 않는다.**
Application Context는 전체 앱의 수명 주기를 의미하기 때문에 메모리 누수에 영향을 주지 않으며 이런 용도를 위해 AndroidViewModel 클래스를 제공함.



## MVVM 구성 요소

View : 데이터를 표시
ViewModel : 데이터를 소유함
Activity/Fragment : UI를 업데이트






참고: [안드로이드 아키텍처 컴포넌트, ViewModel 이해하기 – 한로니 – Medium](https://medium.com/@jungil.han/%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8-viewmodel-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-2e4d136d28d2)

#android/공부