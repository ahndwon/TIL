RecyclerView

# RecyclerView
안드로이드 롤리팝 (5.0)이 발표되면서 ListView 보다 유연하고 성능이 향상된 RecyclerView가 함께 발표되었다. 기존의 ListView는  커스터마이징 하기 힘들었고, 구조적인 문제로 성능상의 문제도 있었다.  RecyclerView는 ListView의 문제를 해결하기 위해 개발자에게 더 다양한 형태로 커스타이징을 지원한다. 가장 큰 차이점은 RecyclerView는 Layout Manager와 View Holder 패턴의 의무사용, Item에 대한 뷰의 변형이나 애니메이션할 수 있는 개념이 추가되었다.


## 주요 클래스
- Adapter - 기존의 ListView에서 사용하는 Adapter와 같은 개념으로 데이터와 아이템에 대한 View 생성
- ViewHolder - 재활용 View에 대한 모든 서브 뷰를 보유
- LayoutManager - 아이템의 항목을 배치
- ItemDecoration - 아이템 항목에서 서브뷰에 대한 처리
- ItemAnimation -  아이템 항목이 추가, 제거되거나 정렬될때 애니메이션 처리

### Adapter
리스트 뷰는 데이터가 어디서 왔냐에 따라 BaseAdapter를 상속한 ArrayAdapter(배열로부터 가져올 때 사용), CursorAdapter(DB로부터 가져올 때), SimpleAdapter(XML 등으로 가져올 때) 를 지정해서 사용한다. 하지만 리사이클러 뷰는 Universal한 Adapter를 사용해서 데이터 소스를 처리하기 때문에 더 유연하고 편리하다. 어댑터는 다음의 3가지 인터페이스를 구현해야 한다

1. onCreateViewHolder(ViewGroup parent, int viewType): 뷰 홀더를 생성하고 뷰를 붙여주는 부분
2. onBindViewHolder(ListItemViewHoder holder, int position) : 재활용 되는 뷰가 호출하여 실행되는 메소드, 뷰 홀더를 전달하고 어댑터는 Position의 데이터를 결합시킵니다.
3. getItemCount() : 데이터의 개수 반환

리스트 뷰가 사용했던 getView() 메소드는 매번 호출되면서 null 처리를 해줘야했다면, OnCreateViewHodler는 새롭게 생성될 때만 불린다.


## ViewHolder 패턴
장점
UI를 수정할 때다 호출되는 findViewById()를 뷰홀더 패턴을 이용해 한번만 함으로써 리스트 뷰의 지연을 초래하는 무거운 연산을 줄여준다.

하지만 오래된 옛날의 기계들일 수록 퍼포먼스 향상이 크고 최신의 디바이스는 View Holder 패턴을 사용하지 않아도 성능 차이가 미세하다. 그리고 ViewHolder 패턴을 사용한 ListView와 RecyclerView의 성능은 같다. 단지 RecyclerView는 뷰홀더 패턴이 강제될 뿐이다.

## LayoutManager
![](RecyclerView/B1790062-FAB6-43CD-BCB6-069B41FD7643.png)
[LayoutManager](https://t1.daumcdn.net/cfile/tistory/25460B385783314C01)

ListView와 RecyclerView의 또 가장 큰 차이는 기존   ListView는 수직 스크롤만 가능했다는 점이다. 리스트뷰를 수평으로 사용할 수가 없었다. 하지만 RecyclerView는 수평을 지원하며 더 다양한 타입의 리스트들을 지원하고 , 커스텀 할 수 있다. 많은 타입의 리스트를 사용하기 위해선 RecyclerView.LayoutManager 클래스를 사용하면 된다. RecyclerView는 아래와 같은 3가지의 미리 정의된 Layout Manager들을 제공한다.

1. LinearLayoutManager : 리사이클러 뷰에서 가장 많이 쓰이는 레이아웃으로 수평, 수직 스크롤을 제공하는 리스트를 만들 수 있다.
2. StaggeredGridLayoutManager : 이 레이아웃을 통해 뷰마다 크기가 다른 레이아웃을 만들 수 있다.
3. GridLayoutManager: 격자형 리스트를 만들 수 있다.



## Item Decoration
리스트 뷰에서는 아래의 파라미터를 XML에 추가함으로써 쉽게 divide할 수 있다. 리사이클러 뷰에서는 RecyclerView.ItemDecoration 클래스를 통해 divider를 원하는 아이템에 추가해주게 됐다. 조금 복잡해졌지만 동적인 데코레이팅이 가능해졌다.

## Item Animator
ListView에서는 아이템의 삽입이나 삭제에 특별한 애니메이션이 없었지만 RecyclerView에서는 RecyclerView.ItemAnimator 클래스를 통해 애니메이션을 핸들링 할 수 있게 됐다. 이 클래스를 통해서 아이템 삽입, 삭제, 이동에 대한 커스터마이징이 가능하고, 또한 DefaultItemAnimator가 제공되므로 커스터마이즈가 필요 없이 사용할 수도 있습니다. notifyItemChanged(int position), notifyItemInserted(int position), notifyItemRemoved(int position)을 ItemAnimator을 통해 특정 아이템에 대한 애니멩션을 발 생시킬 수 있다.

![](RecyclerView/81ED454B-575C-4D01-B299-3F1D2A19BB73.png)
[RecyclerView animation](https://t1.daumcdn.net/cfile/tistory/2439F7475782AA0B2A)


## 주의점
클릭으로 인한 중첩을 OnItemTouchListener로 피해야 한다.
리스트뷰는 쉽게 클릭을 AdapterView.OnItemClickListener()를 통해 감지할 수 있었다. 하지만 RecyclerView는 AdapterView가 아닌RecyclerView의 OnItemTOuchListener로 터치 이벤트를 감지한다. 즉 개발자가 터치 이벤트를 인터셉트하는 제어권한을 갖고 있는 것이다. 개발자는 리사이클러 뷰에 터치 이벤트가 전달되기 전에 조작할 수 있기에 매우 유용하다.



## 코틀린 구현
```xml
// activity_main.xml
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"/>

</android.support.constraint.ConstraintLayout>

```

```xml
// cardview.xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="100dp">

    <android.support.v7.widget.CardView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:cardBackgroundColor="@android:color/darker_gray"
        app:cardCornerRadius="10dp"
        app:cardElevation="7dp">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <ImageView
                android:id="@+id/image"
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:layout_gravity="center"
                android:contentDescription="Image">
            </ImageView>

            <TextView
                android:id="@+id/imageTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"

                android:gravity="center">

            </TextView>
        </LinearLayout>
    </android.support.v7.widget.CardView>
</android.support.constraint.ConstraintLayout>
```


```java
class Item(val image: Int, val imageTitle: String)

class MyAdapter(private val context: Context,
                private val items: ArrayList<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lastPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(cardview, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return items.size;
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myViewHolder = holder as ViewHolder
        myViewHolder.imageView.setImageResource(items.get(position).image)
        myViewHolder.textView.setText(items.get(position).imageTitle)

        setAnimation(holder.textView, position)
    }

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if(position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}


class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val imageView: ImageView = view.findViewById(R.id.image)
    val textView: TextView = view.findViewById(R.id.imageTitle)
}


class MainActivity : AppCompatActivity() {
    val mContext: Context bylazy{
applicationContext
}

    override funonCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = ArrayList<Item>()

        items.add(Item(R.drawable.abc_ic_star_black_48dp,"item 1"))
        items.add(Item(R.drawable.abc_ic_star_black_48dp,"item 2"))
        items.add(Item(R.drawable.abc_ic_star_black_48dp,"item 3"))
        items.add(Item(R.drawable.abc_ic_star_black_48dp,"item 4"))
        items.add(Item(R.drawable.abc_ic_star_black_48dp,"item 6"))
        items.add(Item(R.drawable.abc_ic_star_black_48dp,"item 5"))
        items.add(Item(R.drawable.abc_ic_star_black_48dp,"item 7"))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation= LinearLayoutManager.VERTICAL

		  recyclerView.layoutManager= layoutManager

        recyclerView.adapter= MyAdapter(this, items)
    }
}
```
#android