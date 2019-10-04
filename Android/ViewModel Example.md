ViewModel Example

# ViewModel Example
## 1. Gradle 추가
```
dataBinding {
    enabled = true
}

implementation ‘androidx.lifecycle:lifecycle-extensions:2.0.0’
implementation “androidx.lifecycle:lifecycle-viewmodel-ktx:2.0.0”
kapt “androidx.lifecycle:lifecycle-compiler:2.0.0”

implementation “io.reactivex.rxjava2:rxjava:2.2.2”
implementation ‘io.reactivex.rxjava2:rxandroid:2.1.0’
implementation ‘io.reactivex.rxjava2:rxkotlin:2.3.0’
```

## 2. ObservableViewModel 추가
```kotlin
open class ObservableViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    */***
*     * Notifies listeners that all properties of this instance have changed.*
*     */*
fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    */***
*     * Notifies listeners that a specific property has changed. The getter for the property*
*     * that changes should be marked with [Bindable] to generate a field in*
*     * `BR` to be used as `fieldId`.*
*     **
*     ****@param***fieldId The generated BR id for the Bindable field.*
*     */*
fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }
}

```

## 3. Layout 생성
1. activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.drawerlayout.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/drawerLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@android:color/white"
    tools:context="xyz.thingapps.viewmodel_example.MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <androidx.appcompat.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@android:color/black"
            android:theme="@style/ThemeOverlay.AppCompat.Dark" />

        <FrameLayout
            android:id="@+id/container"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />

    </LinearLayout>

    <LinearLayout
        android:id="@+id/drawer"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="@android:color/white"
        android:orientation="vertical">

        <TextView
            android:id="@+id/dialogButton"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="open dialog" />

    </LinearLayout>

</androidx.drawerlayout.widget.DrawerLayout>

```


2. home_fragment.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/linearLayout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="xyz.thingapps.viewmodel_example.HomeFragment">

    <FrameLayout
        android:id="@+id/firstFragment"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@id/secondFragment"
        app:layout_constraintTop_toTopOf="parent" />

    <FrameLayout
        android:id="@+id/secondFragment"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toBottomOf="@id/firstFragment" />

</androidx.constraintlayout.widget.ConstraintLayout>

```


3. fragment_first.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".FirstFragment">


        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:orientation="horizontal">

            <Button
                android:id="@+id/decreaseButton"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="-" />

            <TextView
                android:id="@+id/countNumber"
                android:layout_width="100dp"
                android:layout_height="match_parent"
                android:text="countNumber" />

            <TextView
                android:id="@+id/countText"
                android:layout_width="100dp"
                android:layout_height="match_parent"
                android:text="countText" />

            <Button
                android:id="@+id/increaseButton"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="+" />

        </LinearLayout>

    </FrameLayout>
```

4. fragment_second.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".FirstFragment">


        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:orientation="horizontal">

            <Button
                android:id="@+id/decreaseButton"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="-" />

            <TextView
                android:id="@+id/countNumber"
                android:layout_width="100dp"
                android:layout_height="match_parent"
android:text="countNumber" />

            <TextView
                android:id="@+id/countText"
                android:layout_width="100dp"
                android:layout_height="match_parent"
                android:text="countText" />

            <Button
                android:id="@+id/increaseButton"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="+" />

        </LinearLayout>

    </FrameLayout>
```


5. fragment_button_dialog.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:orientation="vertical">

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:orientation="horizontal">

            <Button
                android:id="@+id/decreaseButton1"
                android:layout_width="50dp"
                android:layout_height="wrap_content"
                android:text="-" />

            <TextView
                android:id="@+id/countNumber1"
                android:layout_width="50dp"
                android:layout_height="match_parent"
                android:text="number" />

            <TextView
                android:id="@+id/countText1"
                android:layout_width="50dp"
                android:layout_height="match_parent"
                android:text="count" />

            <Button
                android:id="@+id/increaseButton1"
                android:layout_width="50dp"
                android:layout_height="wrap_content"
                android:text="+" />

        </LinearLayout>

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:orientation="horizontal">

            <Button
                android:id="@+id/decreaseButton2"
                android:layout_width="50dp"
                android:layout_height="wrap_content"
                android:text="-" />

            <TextView
                android:id="@+id/countNumber2"
                android:layout_width="50dp"
                android:layout_height="match_parent"
                android:text="number" />

            <TextView
                android:id="@+id/countText2"
                android:layout_width="50dp"
                android:layout_height="match_parent"
                android:text="count" />

            <Button
                android:id="@+id/increaseButton2"
                android:layout_width="50dp"
                android:layout_height="wrap_content"
                android:text="+" />

        </LinearLayout>

    </LinearLayout>
```


## 4. Fragment 생성
1. HomeFragment
```xml
class HomeFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.*home_fragment*, container, false)

        *fragmentManager*?.beginTransaction()?.replace(R.id.*firstFragment*, FirstFragment())?.commit()
        *fragmentManager*?.beginTransaction()?.replace(R.id.*secondFragment*, SecondFragment())?.commit()

        return view
    }
}
```
2. FirstFragment
```kotlin
class FirstFragment : Fragment() {
    private var viewmodel : CountViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = *activity*?.*let***{**ViewModelProviders.of(**it**).get(CountViewModel::class.*java*) **}**
}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding : xyz.thingapps.viewmodel_example.databinding.FragmentFirstBinding =
                DataBindingUtil.inflate(inflater, R.layout.*fragment_first*, container, false)

        binding.*viewmodel*= viewmodel

        return binding.*root*
}
}

```

3. SecondFragment
```kotlin

class SecondFragment : Fragment() {

    private var viewmodel : CountViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = *activity*?.*let***{**ViewModelProviders.of(**it**).get(CountViewModel::class.*java*) **}**
}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding : xyz.thingapps.viewmodel_example.databinding.FragmentSecondBinding =
                DataBindingUtil.inflate(inflater, R.layout.*fragment_second*, container, false)

        binding.*viewmodel*= viewmodel

        return binding.*root*
}
}
```

4. ButtonDialogFragment
```kotlin
class ButtonDialogFragment : DialogFragment() {
    private var viewmodel : CountViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel = *activity*?.*let***{**ViewModelProviders.of(**it**).get(CountViewModel::class.*java*) **}**
}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding: xyz.thingapps.viewmodel_example.databinding.FragmentButtonDialogBinding =
                DataBindingUtil.inflate(inflater, R.layout.*fragment_button_dialog*, container, false)

        binding.*viewmodel*= viewmodel

        return binding.*root*
}
}

```


## 5. CountViewModel 생성
```kotlin

class CountViewModel : ObservableViewModel() {
    var count1 = ObservableInt(0)
    var count2 = ObservableInt(0)

    fun increment1() {
        count1.set(count1.get() + 1)
        notifyPropertyChanged(BR.*count1String*)
    }

    fun increment2() {
        count2.set(count2.get() + 1)
        notifyPropertyChanged(BR.*count2String*)
    }

    fun decrement1() {
        count1.set(count1.get() - 1)
        notifyPropertyChanged(BR.*count1String*)
    }

    fun decrement2() {
//        count2 = ObservableInt(count2.get() + 1)
        count2.set(count2.get() + 1)
        notifyPropertyChanged(BR.*count2String*)
    }

    // get이나 set을 해야됨
    @Bindable
//    fun count1ToString() : String {
    fun getCount1String() : String {
        // count1 == 1
        return when {
            count1.get() == 1 -> "first"
            count1.get() == 2 -> "second"
            count1.get() == 3 -> "third"
            count1.get() == 4 -> "fourth"
            count1.get() == 5 -> "fifth"
            count1.get() == 6 -> "sixth"
            count1.get() == 7 -> "seventh"
            count1.get() == 8 -> "eighth"
            count1.get() == 9 -> "ninth"
            count1.get() == 10 -> "tenth"
            else -> "else"
        }
    }

    @Bindable
    fun getCount2String() : String {
        // count1 == 1
        return when {
            count2.get() == 1 -> "first"
            count2.get() == 2 -> "second"
            count2.get() == 3 -> "third"
            count2.get() == 4 -> "fourth"
            count2.get() == 5 -> "fifth"
            count2.get() == 6 -> "sixth"
            count2.get() == 7 -> "seventh"
            count2.get() == 8 -> "eighth"
            count2.get() == 9 -> "ninth"
            count2.get() == 10 -> "tenth"
            else -> "else"
        }
    }
}
```



## 6.  Converter 추가
```kotlin
@file:JvmName("Converter")

package xyz.thingapps.viewmodel_example

import androidx.databinding.ObservableInt

fun observableIntToString(value : ObservableInt) : String {
    return value.get().toString()
}

```



## 7. 레이아웃에 변수 추가
```xml
<layout>

    <data>
        <import type="xyz.thingapps.viewmodel_example.Converter" />

        <variable
            name="viewmodel"
            type="xyz.thingapps.viewmodel_example.CountViewModel" />
    </data>

```


## 8. 레이아웃에 메소드 추가
**onClick**
```xml
<Button
    android:id="@+id/decreaseButton"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:onClick="@{()->viewmodel.decrement2()}"
    android:text="-" />

```


**text**
```xml
<TextView
    android:id="@+id/countText"
    android:layout_width="100dp"
    android:layout_height="match_parent"
    android:text="@{viewmodel.count2String}" />
```


**Converter.observableIntToString()**
```xml
<TextView
    android:id="@+id/countNumber"
    android:layout_width="100dp"
    android:layout_height="match_parent"
    android:text="@{Converter.observableIntToString(viewmodel.count2)}"

```





#android