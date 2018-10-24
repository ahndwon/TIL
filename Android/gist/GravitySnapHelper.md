# GravitySnapHelper
![Google Play GravitySnapHelper](http://www.tellmehow.co/wp-content/uploads/2017/06/snap_final-compressor.gif)

리사이클러뷰 사용시 아이템의 한쪽 면을 Gravity를 기준으로 화면의 끝에 밀착시키면서 스와이프를 할시 자동 정렬하기 위해 사용한다. 


## *소스 코드*
```kotlin
class GravitySnapHelper @JvmOverloads constructor(
        private val mGravity: Int,
        private var mSnapLastItemEnabled: Boolean = false,
        internal var mSnapListener: SnapListener? = null)
    : LinearSnapHelper() {

    private var mVerticalHelper : OrientationHelper? = null
    private var mHorizontalHelper : OrientationHelper? = null
    private var mIsRtlHorizontal: Boolean = false
    internal var mSnapping: Boolean = false
    private val mScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                mSnapping = false
            }
            if (newState == RecyclerView.SCROLL_STATE_IDLE&& mSnapping && mSnapListener != null) {
                val position = getSnappedPosition(recyclerView)
                if (position != RecyclerView.NO_POSITION) {
                    mSnapListener!!.onSnap(position)
                }
                mSnapping = false
            }
        }
    }

    init {
        if (mGravity != Gravity.START&& mGravity != Gravity.END
&& mGravity != Gravity.BOTTOM&& mGravity != Gravity.TOP) {
            throw IllegalArgumentException("Invalid gravity value. Use START " + "| END | BOTTOM | TOP constants")
        }
    }

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(@Nullable recyclerView: RecyclerView?) {
        if (recyclerView != null) {
            if (mGravity == Gravity.START|| mGravity == Gravity.END) {
                mIsRtlHorizontal = false
            }
            if (mSnapListener != null) {
                recyclerView.addOnScrollListener(mScrollListener)
            }
        }
        super.attachToRecyclerView(recyclerView)
    }

    override fun calculateDistanceToFinalSnap(@NonNull layoutManager: RecyclerView.LayoutManager,
                                     @NonNull targetView: View): IntArray {
        val out = IntArray(2)

        if (layoutManager.canScrollHorizontally()) {
            if (mGravity == Gravity.START) {
                getHorizontalHelper(layoutManager)?.let{
out[0] = distanceToStart(targetView, it, false)
                }

} else {  END
                getHorizontalHelper(layoutManager)?.let{
out[0] = distanceToEnd(targetView, it, false)
                }
}
        } else {
            out[0] = 0
        }

        if (layoutManager.canScrollVertically()) {
            if (mGravity == Gravity.TOP) {
                getVerticalHelper(layoutManager)?.let{
out[1] = distanceToStart(targetView, it, false)
                }

} else {  BOTTOM
                getVerticalHelper(layoutManager)?.let{
out[1] = distanceToEnd(targetView, it, false)
                }
}
        } else {
            out[1] = 0
        }

        return out
    }

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        var snapView: View? = null
        if (layoutManager is LinearLayoutManager) {
            when (mGravity) {
                Gravity.START-> {
                    getHorizontalHelper(layoutManager)?.let{
snapView = findStartView(layoutManager, it)
                    }
}`
                Gravity.END-> {
                    getHorizontalHelper(layoutManager)?.let{
snapView = findEndView(layoutManager, it)
                    }
}
                Gravity.TOP-> {
                    getVerticalHelper(layoutManager)?.let{
snapView = findStartView(layoutManager, it)
                    }
}
                Gravity.BOTTOM-> {
                    getVerticalHelper(layoutManager)?.let{
snapView = findEndView(layoutManager, it)
                    }
}
            }
        }

        mSnapping = snapView != null

        return snapView
    }

    
      Enable snapping of the last item that's snappable.
      The default value is false, because you can't see the last item completely
      if this is enabled.
     
     @paramsnap true if you want to enable snapping of the last snappable item
     
fun enableLastItemSnap(snap: Boolean) {
        mSnapLastItemEnabled = snap
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper, fromEnd: Boolean): Int {
        return if (mIsRtlHorizontal && !fromEnd) {
            distanceToEnd(targetView, helper, true)
        } else helper.getDecoratedStart(targetView) - helper.startAfterPadding

}

    private fun distanceToEnd(targetView: View, helper: OrientationHelper, fromStart: Boolean): Int {
        return if (mIsRtlHorizontal && !fromStart) {
            distanceToStart(targetView, helper, true)
        } else helper.getDecoratedEnd(targetView) - helper.endAfterPadding

}

    
      Returns the first view that we should snap to.
     
     @paramlayoutManager the recyclerview's layout manager
     @paramhelper        orientation helper to calculate view sizes
     @returnthe first view in the LayoutManager to snap to
     
private fun findStartView(layoutManager: RecyclerView.LayoutManager,
                              helper: OrientationHelper): View? {

        if (layoutManager is LinearLayoutManager) {
            val firstChild = layoutManager.findFirstVisibleItemPosition()

            if (firstChild == RecyclerView.NO_POSITION) {
                return null
            }

            val child = layoutManager.findViewByPosition(firstChild)

            val visibleWidth: Float

             We should return the child if it's visible width
             is greater than 0.5 of it's total width.
             In a RTL configuration, we need to check the start point and in LTR the end point
            if (mIsRtlHorizontal) {
                visibleWidth = (helper.totalSpace- helper.getDecoratedStart(child)).toFloat()  helper.getDecoratedMeasurement(child)
            } else {
                visibleWidth = helper.getDecoratedEnd(child).toFloat()  helper.getDecoratedMeasurement(child)
            }

             If we're at the end of the list, we shouldn't snap
             to avoid having the last item not completely visible.
            val endOfList = layoutManager
                    .findLastCompletelyVisibleItemPosition() == layoutManager.getItemCount() - 1

            return if (visibleWidth > 0.5f && !endOfList) {
                child
            } else if (mSnapLastItemEnabled && endOfList) {
                child
            } else if (endOfList) {
                null
            } else {
                 If the child wasn't returned, we need to return
                 the next view close to the start.
                layoutManager.findViewByPosition(firstChild + 1)
            }
        }

        return null
    }

    private fun findEndView(layoutManager: RecyclerView.LayoutManager,
                            helper: OrientationHelper): View? {

        if (layoutManager is LinearLayoutManager) {
            val lastChild = layoutManager.findLastVisibleItemPosition()

            if (lastChild == RecyclerView.NO_POSITION) {
                return null
            }

            val child = layoutManager.findViewByPosition(lastChild)

            val visibleWidth: Float

            if (mIsRtlHorizontal) {
                visibleWidth = helper.getDecoratedEnd(child).toFloat()  helper.getDecoratedMeasurement(child)
            } else {
                visibleWidth = (helper.totalSpace- helper.getDecoratedStart(child)).toFloat()  helper.getDecoratedMeasurement(child)
            }

             If we're at the start of the list, we shouldn't snap
             to avoid having the first item not completely visible.
            val startOfList = layoutManager
                    .findFirstCompletelyVisibleItemPosition() == 0

            return if (visibleWidth > 0.5f && !startOfList) {
                child
            } else if (mSnapLastItemEnabled && startOfList) {
                child
            } else if (startOfList) {
                null
            } else {
                 If the child wasn't returned, we need to return the previous view
                layoutManager.findViewByPosition(lastChild - 1)
            }
        }
        return null
    }

    internal fun getSnappedPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager

if (layoutManager is LinearLayoutManager) {
            if (mGravity == Gravity.START|| mGravity == Gravity.TOP) {
                return layoutManager.findFirstCompletelyVisibleItemPosition()
            } else if (mGravity == Gravity.END|| mGravity == Gravity.BOTTOM) {
                return layoutManager.findLastCompletelyVisibleItemPosition()
            }
        }

        return RecyclerView.NO_POSITION

}

    private fun getVerticalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager)
        }
        return mVerticalHelper
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper
    }

    interface SnapListener {
        fun onSnap(position: Int)
    }

}

```


## 사용 방법
```kotlin
val snapHelper = GravitySnapHelper(Gravity./START/)
snapHelper.attachToRecyclerView(recyclerView)
```



#androidgist