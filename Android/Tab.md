# Tab
ViewPager와 PagerAdapter를 사용하여 탭을 쉽게 구현할 수 있다.

- 각 페이지마다 보여주는 것이  View라면 -> PagerAdapter 사용
- 각 페이지마다 보여주는 것이 Fragment라면 -> FragmentPagerAdapter, FragmentStatePagerAdapter 사용
	- FragmentPagerAdapter는 모든 프래그먼트를 한번에 로드 하고 계속 유지하기 때문에 탭간 이동이 빠르나 메모리를 많이 사용한다.
	- FragmentStatePagerAdapter는 달리 프래그먼트를 파괴, 생성, 상태 유지를 함으로써 메모리를 효율적으로 관리한다.


## PagerAdapter
사용하기 위해선 다음 메소드들을 오버라이딩 해야한다.
- `getCount()` -  페이지의 수를 반환
- `getItem()` - 포지션에 해당하는 프래그먼트 반환
- `getPageTitle()` - 포지션에 해당하는 페이지의 제목을 반환


## 탭 설정
```java
// viewPager -> ViewPager의 id
val adapter = SectionsPageAdapter(supportFragmentManager)
adapter.addFragment(HomeFragment(), "Home")
adapter.addFragment(IssueFragment(), "Issue")
adapter.addFragment(PullRequestFragment(), "Pull Request")
adapter.addFragment(ProfileFragment(), "Profile")

viewPager.adapter= adapter

// tabs -> TabLayout의 id
tabs.setupWithViewPager(container)
```
탭과 페이지들을 연결하기 위해선  setupWithViewPager()를 사용하면 손쉽게 할 수 있다.

#android