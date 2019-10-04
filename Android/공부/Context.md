Context

# Context
## Context란? 
컨섹스트는 어플리케이션의 상태에 대한 정보에 접근할 수 있게 해준다. 액티비티, 프래그먼트, 서비스가 리소스 파일과, 이미지, 테마/스타일, 외부 저장 경로에 접근하게 해주는 것이 바로 컨텍스트다. 또한 안드로이드의 빌트인 서비스인 layout inflation, keyboard, content provider에도 접근할 수 있게 해준다.

**Context가 필요한 경우**
주로 현재 액티비티의 인스턴스를 넘기면 된다. 액티비티로부터 만들어진 adapter 나 fragment 들은 액티비티의 인스턴스가 필요하다. 하지만 액티비티 바깥에서는 application context가 필요하다

	* *activity context* - 액티비티 안에서 만들어진 어댑터나 프래그먼트 등, 액티비티 안에서 무언가를 할 경우 -> **라이프사이클이 관련된 Context가 필요할 경우 (ex View)**

	* *application context* - 액티비티의 밖에서 이루어지는 어플리케이션이나 서비스 등에 필요 -> **라이프사이클이 관련 없는 Context가 필요할 경우**

![](Context/EE7B691D-DDAF-4733-B3F4-40A804247C07.png)
![Context](https://blog.hanumoka.net/images/20171011-android-context.png)

### Toast
토스트는 어떤 window에도 부착(attach)되지 않으므로 application context 이든 activity context이든 상관이 없다.


## 컨텍스트 호출 방법
* `Activity.this` - 액티비티 클래스에 접근하지만 액티비티 클래스는 Context 클래스를 상속하고 있으므로 액티비티 컨텍스트를 얻을 수 있다.
* `getApplication()` - Application 객체를 가리키지만 Application 클래스는 Context 클래스를 상속하므로 application context 를 얻을 수 있다.
* `getApplicationContext()` -  application context 제공
* `getBaseContext()` - activity context 제공

## Application vs Activity Context 
주로 theme과 style은 어플리케이션 레벨에서 적용되지만 액티비티 레벨 에서도 적용할 수 있다. 이걸 이용하여 전체적인 어플리케이션에 스타일을 적용한 후 몇몇 액티비티에만 별도의 스타일을 적용할 수도 있다.

```xml
<application
       android:allowBackup="true"
       android:icon="@mipmap/ic_launcher"
       android:label="@string/app_name"
       android:theme="@style/AppTheme" >
       <activity
           android:name=".MainActivity"
           android:label="@string/app_name"
           android:theme="@style/MyCustomTheme">
```

대부분의  View는 Activity Context가 필요하다


## Anonymous functions
익명 함수를 사용 시 this를 통해 액티비티의 인스턴스를 넘겨주어 context를 줄 수 있다.
이럴 땐 Activity의 인스턴스임을 명시해줄 수 있다.

```java
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // resource lookup using Kotlin extensions
        val tvTest = abc
        
        tvTest.setOnClickListener({ view -> Toast.makeText(this@MainActivity, "hello", Toast.LENGTH_SHORT).show()}) 
}
```

## Adapters
### Array Adapter
ListView에서 사용하는 어댑터를 생성할 때, `getContext()`를 통해 레이아웃 인플레이션에서 컨텍스트를 준다. -> 이것은 ArrayAdapter를 인스턴스화한 컨텍스트를 넘겨준다.

``` java
 if (convertView == null) {
      convertView = 
          LayoutInflater
              .from(getContext())
              .inflate(R.layout.item_user, parent, false);
   }
```
ArrayAdapter 를 애플리케이션 컨텍스트로 인스턴스화할 경우 themes/style 들이 적용이 안되므로 꼭 Activity Context 를 사용해야 한다.


### RecyclerView Adapter
RecyclerView.Adapter는 ListAdapter와 달리 context 를 필요로 하지 않는다. 대신 context가 필요할 경우 ViewHolder의 인스턴스를 통해 사용할 수 있다.

`viewHolder.itemView.getContext() `


## Avoiding memory leaks
Application Context 는 주로 싱긑런 인스턴스가 생성해야 할 때 사용된다. 예를 들면 커스텀 매니저 클래스가 컨텍스트 정보를 통해  여러 액티비티에서 사용되어야 할 시스템 서비스에 접근해야 할 경우에 Application Context가 사용된다. Activity Context를 사용할 경우 액티비티가 사용되지 않음에도 불구하고 싱글턴 객체 때문에 파괴할 수 없게 되므로 메모리를 돌려받을 수 없다 -> Memory Leak


아래는 CustomManager 클래스가 context 를 저장하고 있기 때문에 안드로이드 시스템에서 파괴할 수 없다.
```java
class CustomManager private constructor(private val context: Context) {
    companion object {
        @Volatile
        private var instance: CustomManager? = null

        // double-checked locking algorithm
        // https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
        // http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
        fun getInstance(context: Context): CustomManager {
            val i = instance
            if (i != null) {
                return instance as CustomManager
            }
            return synchronized(this) {
                val i2 = instance
                if (i2 != null) {
                    i2
                } else {
                    val created = CustomManager(context)
                    instance = created
                    created
                }
            }
        }
    }
}
```

## 컨텍스트를 보관하는 방법 : application context를 사용
메모리 누수를 피하기 위해선 절대 라이프 사이클을 넘어서는 컨텍스트에 대한 참조를 하면 안된다. 제대로 컨텍스트를 보관하는 방법은 `CustomManager.getInstance()`에 저장하는 것이다. -> 싱글턴의 인스턴스를 액티비티의 라이프 사이클처럼 관리하여 안전을 보장한다.

Application Context는 *라이프사이클과 무관한 컨텍스트*가 필요할 경우 사용해야 한다.

```java

class CustomManager private constructor(private val context: Context) {
    companion object {
        fun getInstance(context: Context): CustomManager {
            // synchronized code

            val created = CustomManager(context.applicationContext)
        }
    }
}

```


참고
- [Using Context | CodePath Android Cliffnotes](https://guides.codepath.com/android/Using-Context#recyclerview-adapter)
- [android - difference and when to use getApplication(), getApplicationContext(), getBaseContext() and someClass.this - Stack Overflow](https://stackoverflow.com/questions/10347184/difference-and-when-to-use-getapplication-getapplicationcontext-getbasecon)

#android/공부 