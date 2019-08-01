Layouts

# Layouts
#부스트코스
부스트코스 안드로이드 과정을 시작하며 가장 처음에 배우는 것 중 하나가 바로 Layout이다. 기본적인 Layout의 종류와 구조에 대해 알아보았다.


## ViewGroup
레이아웃은 전부 ViewGroup의 하위 클래스이다. ViewGroup이란 View에 child 그룹들을 추가할 수 있는 View이다.

ViewGroup의 주요 역할은 자식 View들을 어디에 위치시키고 크기를 정하는 등이다.

## Layout_Attributes
View와 같이 ViewGroup은 XML 속성들을 사용할 수 있다. 중요한 점은 layout_attributes들은 해당 View를 위한 것이 아닌 부모 ViewGroup을 위한 것이다.

다음의 예를 보자
```xml
<android.support.design.widget.AppBarLayout>
  <android.support.v7.widget.Toolbar
    app:layout_scrollFlags="scroll|enterAlways" />
</android.support.design.widget.AppBarLayout >
```
위의 예에서 툴바를 볼 경우 layout_scrollFlag에 대한 나용이 없다. 이것은 LayoutParms이에 존재 한다. 각 View는 부모에 연결될때 각자의 LayoutParams를 가진다. 기본적으로 width, height의 정보를 가지지만 더 많은 속성들이 추가될 수 있다.


## Common Android Layouts
안드로이드에는 몇가지 기본 레이아웃들이 있다. 각각 특징이 있으므로 그 특징에 대해 알고 있으면 특정 상황에 무엇을 쓸지 판단할 때 도움이 된다.

### Linear Layout
LinearLayout은 이름처럼 자식뷰들을 일렬로 나타낸다. 방향은 ```android:orientation``` 속성을 horizontal, vertical로 설정해서 결정할 수 있다.

각 뷰의 사이즈를 남은 공간에 따라 맞춰지도록 하고 싶을 경우  ```layout_weight``` 속성을 사용하면 된다.

![LinearLayout](https://miro.medium.com/max/1400/1*v3UU0b_fZcBKZNtCIQDB2g.png)

### FrameLayout
LinearLayout과 완전히 다르게 동작한다. 모든 자식들은 스택처럼 그려진다. 뷰들의 포지션을 변경하기 위해선 layout_gravity 속성을 사용할 수 있다.

![FrameLayout](https://miro.medium.com/max/1276/1*YgidpWmqi-7Ams8LjkQt7A.jpeg)

### RelativeLayout
RelativeLayout은 자식뷰들의 위치를 세밀하게 조정하기 알맞은 레이아웃이다. 각 뷰의 경계 혹은 부모뷰에 따라 상대적으로 위치를 조정할 수 있다.
 
매우 편리하지만 성능하락을 조심해야 한다.
    
![RelativeLayout](https://miro.medium.com/max/1400/1*9o0_YoVZsO48amXmJhnWPQ.png)

### GridLayout
행과 열로 구분된 레이아웃을 구현하기에 최적화된 레이아웃이다. 특이한 점은 자식 뷰들의 width와 height 를 요구하지 않는다. 각 뷰의 Alignment에 따라 크기가 커지거나 작아진다.

### CoordinatorLayout
FrameLayout의 하위클래스로써 다른 점은 Behavior이라는 컨셉이 추가되었다.

자식뷰에 Behavior 속성을 줄 경우 nested scrolling, 터치 이벤트, 크기 조정 등 추가적인 작업을 하는데 도움을 준다.

### ConstraintLayout
기존 RelativeLayout에 대비 훨씬 편리하게 위치를 상대적으로 조정하게 해준다. ConstraintLayout의 목적은 nested View들을 줄이기 쉽게 하여 성능 향상을 추구하는것이다.

기존에 RelativeLayout의 속성들 보다 더 많은 위치 조정 속성들을 제공한다.

#### RelativeLayout 속성
* layout_toRightOf
* layout_toLeftOf
* layout_toTopOf
* layout_toBottomOf
* 

#### ConstraintLayout 속성
* layout_constraintTop_toTopOf - 뷰의 위를 다른 뷰의 위에 위치하도록 한다.
* layout_constraintTop_toBottomOf - 뷰의 위를 다른 뷰의 아래에 위치하도록 한다.
* layout_constraintBottom_toTopOf - 뷰의 아래를 다른 뷰의 위에 위치하도록 한다.
* layout_constraintBottom_toBottomOf - 뷰의 아래를 다른 뷰의 아래에 위치하도록 한다. 
* layout_constraintStart_toTopOf - 뷰의 왼쪽을 다른 뷰의 위에 위치하도록 한다.
* layout_constraintStart_toBottomOf - 뷰의 왼쪽을 다른 뷰의 아래에 위치하도록 한다.
* layout_constraintStart_toStartOf - 뷰의 왼쪽을 다른 뷰의 왼쪽에 위치하도록 한다.
* layout_constraintStart_toEndOf - 뷰의 왼쪽을 다른 뷰의 오른쪽에 위치하도록 한다.
* layout_constraintEnd_toTopOf - 뷰의 오른쪽을 다른 뷰의 위에 위치하도록 한다.
* layout_constraintEnd_toBottomOf - 뷰의 오른쪽을 다른 뷰의 아래에 위치하도록 한다.
* layout_constraintEnd_toStartOf - 뷰의 오른쪽을 다른 뷰의 왼쪽에 위치하도록 한다.
* layout_constraintEnd_toEndOf - 뷰의 오른쪽을 다른 뷰의 오른쪽에 위치하도록 한다.


#### Auto-connect
Auto-connect 기능을 사용하면 자동으로 constraint를 추가해준다.
![Auto-connect](https://miro.medium.com/max/960/1*QUY4Z9sGGU75xArVYyXssA.gif)


## include
레이아웃을 별도의 xml 파일로 작성한후 다른 레이아웃에서 재사용할 수 있게 해준다.

사용법은 `<include>` 태그를 사용하는 것이다.

### include Example
다음은 부스트코스 프로젝트1에서 내가 사용한 방법이다. include를 통해 엄청 긴 xml을 짧게 줄이고 있다.
```xml
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <include layout="@layout/poster_layout" />

        <include layout="@layout/movie_ratings_layout" />

        <include layout="@layout/horizontal_divider"/>

        <include layout="@layout/summary_layout" />

        <include layout="@layout/horizontal_divider"/>

        <include layout="@layout/director_actor_layout" />

        <include layout="@layout/horizontal_divider"/>

        <include layout="@layout/one_line_review_layout" />

        <include layout="@layout/horizontal_divider"/>

        <include layout="@layout/reservation_layout"/>

        <include layout="@layout/horizontal_divider"/>

    </LinearLayout>

</ScrollView>
```
### 단점
include 할 경우 또 하나의 레이아웃을 기존 레이아웃에 추가하는 것이기 때문에 불필요한 계층이 더 추가될 수도 있다.

다음의 xml을 예로 들어보자.
```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">

    <ImageView  
        android:layout_width="fill_parent" 
        android:layout_height="fill_parent" 
    
        android:scaleType="center"
        android:src="@drawable/golden_gate" />
    
    <TextView
        android:layout_width="wrap_content" 
        android:layout_height="wrap_content" 
        android:layout_marginBottom="20dip"
        android:layout_gravity="center_horizontal|bottom"

        android:padding="12dip"
        
        android:background="#AA000000"
        android:textColor="#ffffffff"
        
        android:text="Golden Gate" />

</FrameLayout>
```

위의 레이아웃을 include 할 경우 아래와 같이 불필요한 FrameLayout이 View 계층 추가된다.

![Include](https://docs.huihoo.com/android/2.1/resources/articles/images/merge2.png)


## merge
merge를 사용할 경우 위의 include 의 문제점을 해결할 수 있다. merge는 이름 그대로 기존 레이아웃에 merge를 할려는 레이아웃을 끼워서 합쳐준다.

### merge Example
다음의 레이아웃을 예로 들어보자.
```xml
<merge xmlns:android="http://schemas.android.com/apk/res/android">

    <ImageView  
        android:layout_width="fill_parent" 
        android:layout_height="fill_parent" 
    
        android:scaleType="center"
        android:src="@drawable/golden_gate" />
    
    <TextView
        android:layout_width="wrap_content" 
        android:layout_height="wrap_content" 
        android:layout_marginBottom="20dip"
        android:layout_gravity="center_horizontal|bottom"

        android:padding="12dip"
        
        android:background="#AA000000"
        android:textColor="#ffffffff"
        
        android:text="Golden Gate" />

</merge>

```


위와 같은 레이아웃을 merge 할 경우 아래와 같이 불필요한 계층을 하나 더 추가되지 않고 레이아웃을 추가할 수 있다.
![merge](https://docs.huihoo.com/android/2.1/resources/articles/images/merge3.png)



### 참고
- [Exploring the new Android ConstraintLayout](https://medium.com/exploring-android/exploring-the-new-android-constraintlayout-eed37fe8d8f1)
- [Layouts, Attributes, and you
](https://medium.com/androiddevelopers/layouts-attributes-and-you-9e5a4b4fe32c)
[Android Beginners : Views & Layouts
](https://medium.com/@geekanamika/android-beginners-views-layouts-657a5bbeebe2)

#부스트코스

























