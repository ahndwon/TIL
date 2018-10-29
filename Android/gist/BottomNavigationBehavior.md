# BottomNavigationBehavior
스크롤시 하단내비게이션 숨기는 Behavior
스크롤 이벤트에 animate를 통해 움직일 수도 있으며
아니면 스크롤 이동값으로 숨길 수 있다.

```kotlin
class BottomNavigationBehavior<V : View>(context: Context, attrs: AttributeSet) :
        androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun onStartNestedScroll(
            coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout,
            child: V,
            directTargetChild: View,
            target: View,
            axes: Int,
            type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
}

    override fun onNestedPreScroll(
            coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout,
            child: V,
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray,
            type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
// Translation by Animate
//        if (dy < 0) {
//            showBottomNavigationView(child as BottomNavigationView)
//        } else if (dy > 0) {
//            hideBottomNavigationView(child as BottomNavigationView)
//        }

// Translation by Scroll
        child.translationY= max (0f,  min (child.height.toFloat(), child.translationY+ 1.5f * dy))
    }

    private fun hideBottomNavigationView(view: BottomNavigationView) {
        view.animate().translationY(view.height.toFloat())
    }

    private fun showBottomNavigationView(view: BottomNavigationView) {
        view.animate().translationY(0f)
    }
}
```

#androidgist