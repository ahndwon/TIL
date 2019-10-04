RecyclerView_DotPageIndicator

# RecyclerView_DotPageIndicator
## RecyclerView.ItemDecoration
어플리케이션이 item에 특별한 drawing을 추가하거나 레이아웃 오프셋을 설정할 수 있도록 해준다. 주로 리사이클러 뷰의 아이템 사이사이에 경계선이나 빈 공간을 추가하고 싶을 시에 이용된다.

모든 ItemDecoration 들은 추가된 순서로 그려진다.

## DotPageIndicator
![dot indicator](https://i.stack.imgur.com/jg9SL.png)
이런 Page의 갯수와 현재 위치를 나타내주는 Indicator를 만들기 위해선 ItemDecoration()을 상속하는 클래스를 만들어야한다.


### 아이템 사이에 공백 추가
RecyclerView.ItemDecoration 의 getItemOffsets() 을 오버라이딩하여 offset을 설정해주어야 한다. 이때 입력한 숫자는 픽셀단위가 되야 되므로 기기의`Resources.getSystem().displayMetrics.density` 을 통해 density를 얻고 여기에 구하고자 하는 값을 곱하면 된다. 


### onDrawOver()
Page Indicator를 그리기 위해선 onDrawOver()를 오버라이딩 해줘야한다.  표시해야할 아이템의 포지션은 `val activePosition = layoutManager.findFirstCompletelyVisibleItemPosition()` 이런 식으로 구할 수 있다. 만약 아이템이 뷰에 꽉 찬다면 findFirstVisible()를 호출하면 되지만 선택된 아이템 주변의 아이템이 뷰에 일부분 나타난다면 `findFirstCompletelyVisibleItemPosition()` 을 호출하면 된다.


### 한 페이지씩 스와이핑
`PagerSnapHelper()`를 인스턴스화한 후`snapHelper.attachToRecyclerView(view.bigMindRecyclerView)` 
이렇게 리사이클러 뷰를 연결해주면 된다.

```java
val snapHelper = PagerSnapHelper()
snapHelper.attachToRecyclerView(view.bigMindRecyclerView)
```


## Kotlin 구현
```java
class DotItemDecoration : RecyclerView.ItemDecoration() {

    private val colorActive = 0xFF7e7e7e
    private val colorInactive = 0xFFd8d8d8

    private val dp = Resources.getSystem().displayMetrics.density

    private val mIndicatorHeight = (dp * 16).toInt()
    private val mIndicatorStrokeWidth = dp * 2
    private val mIndicatorItemLength = dp * 8
    private val mIndicatorItemPadding = dp * 4

    private val mPaint = Paint().apply
strokeCap = Paint.Cap.ROUND
        strokeWidth = mIndicatorStrokeWidth
        style = Paint.Style.FILL
        isAntiAlias = true
    

override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
                                state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.left = (dp * 16).toInt()
        }
        outRect.right = (dp * 16).toInt()
//        outRect.bottom = (dp * 20).toInt()
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = parent.adapter?.itemCount
itemCount?.let {
// center horizontally, calculate width and subtract half from center
            val totalLength = mIndicatorItemLength * itemCount
            val paddingBetweenItems = Math.max(0, itemCount - 1) * mIndicatorItemPadding
            val indicatorTotalWidth = totalLength + paddingBetweenItems
            val indicatorStartX = (parent./width/- indicatorTotalWidth) / 2f

            // center vertically in the allotted space
            val indicatorPosY = parent./height/- mIndicatorHeight / 2f

            drawInactiveIndicators(c, indicatorStartX, indicatorPosY, itemCount)

            val layoutManager = parent./layoutManager/as LinearLayoutManager
            val activePosition = layoutManager.findFirstCompletelyVisibleItemPosition()
            if (activePosition == RecyclerView.NO_POSITION) {
                return
            }

            // find offset of active page (if the user is scrolling)
            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition)
        }
}

    private fun drawInactiveIndicators(c: Canvas, indicatorStartX: Float,
                                       indicatorPosY: Float, itemCount: Int) {
        mPaint.color = colorInactive.toInt()

        // width of item indicator including padding
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var start = indicatorStartX
        for (i in 0 until itemCount) {
            // draw the line for every item
            c.drawCircle(start, indicatorPosY, (dp * 3), mPaint)
            start += itemWidth
        }
    }

    private fun drawHighlights(c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
                               highlightPosition: Int) {
        mPaint.color = colorActive.toInt()

        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding

        var highlightStart = indicatorStartX + itemWidth * highlightPosition

        c.drawCircle(highlightStart, indicatorPosY,
                (dp * 3), mPaint)
        highlightStart += itemWidth

    }
}

```


### 참고
Line Page Indicator - [Paging your RecyclerView](https://blog.davidmedenjak.com/android/2017/06/24/viewpager-recyclerview.html)



#android/공부