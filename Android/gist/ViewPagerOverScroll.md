# ViewPagerOverScroll
ViewPager에서 OverScroll 이벤트를 받아내기 힘들다
하지만 간단한 방법으로 OverScroll 이벤트를 판단할 수 있다.

ViewPager에 OnPageChangeListener을 추가해준 후 position을 받아서 
ViewPager가 스크롤 중인 상태이고 아이템이 마지막  position일 때  실행할 동작을 정해주면 된다.

```kotlin
viewPager.addOnPageChangeListener(object : viewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Log.d("OnPageScrolled", "state: $state")
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    Log.d("OnPageScrolled", "position : $position")
                    Log.d("OnPageScrolled", "size : ${adapter.getItemListSize()}")

                    if (position == adapter.getItemListSize() - 1) {
						//TODO OverScroll 시 실행할 동작 추가
                        finish()
                    }
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                this@MainActivity.position = position
            }

            override fun onPageSelected(position: Int) {

            }

        })

```



#android/gist