# Ch11_ViewPager
## ViewPager와 PagerAdapter
여러 면에서 ViewPager와 RecyclerView는 유사하다. RecyclerView가 뷰를 생성하기 위해 Adapter가 필요하듯이 ViewPager는 PagerAdapter가 필요하다. 

PagerAdapter의 FragmentStatePagerdapter를 사용하면 더 편리하게 구현할 수 있다. FragmentStatePagerAdapter는 액태비티가 아닌 프래그먼트를 사용하는 PagerAdapter를 구현한 것이며, 프래그먼트의 상태도 저장하고 복원할 수 있다.

FragmentStatePagerAdapter는 두 개의 간단한 메서드인 getCount()와 getItem(int)만 구현하면 된다.

```java
mViewPager = (ViewPager) findViewById(R.id.activity_crime_pager_view_pager);
mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
	@Override
	public Fragment getItem(int position) {
		Crime crime = mCrimes.get(position);
		return CrimeFragment.newInstance(crime.getId());
	}

	@Override
	public int getCount() {
		return mCrimes.size();
	}
});
```


**ViewPager**
뷰페이저는 현재의 항목을 화면에 로딩할 뿐만 아니라 화면을 미는데 응답을 즉시 할 수 있게 하기 위해서 각 방향의 인접 페이지 하나씩을 미리 로딩해 놓는다. **setOffScreenpageLimit(int)** 메서드를 호출하면 로딩되는 인접 페이지의 개수를 변경할 수 있다.


### 초기 페이저 항목 설정
뷰페이저는 기본적으로 맨 첫버째 항목을 보여준다. 변경하기 위해선 인덱스를 설정해야 한다.
```java
public class CrimePagerActivity extends FragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
		});

		for (int i = 0; i < mCrimes.size(); i++) {
			if (mCrimes.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
			}
		}
	}
}
```


## FragmentStatePagerAdapter vs FragmentPagerAdapter
* **FragmentStatePagerAdapter** 
	* 사용이 필요 없어진 프래그먼트는 소멸된다. 
	* 그리고 액티비티의 FragmentManager로부터 그 프래그먼트를 완전히 삭제하기 위해 트랜잭션이 커밋된다. 
	* 프래그먼트가 소멸될 때 그것의 Bundle 객체를 onSaveInstanceState(Bundle) 메서드로 보존한다.


* **FragmentPagerAdapter**
	* 필요없는 프래그먼트는 트랜잭션에서 remove() 대신 detach()를 호출한다. 따라서 프래그먼트의 뷰는 소멸되나 인스턴스는 남아있다.
	* **단점** - 상대적으로 메모리를 더 많이 차지함


### ViewPager 동작 방법
ViewPager와 PagerAdapter 클래스들은 내부적으로 많은 일을 처리한다. 
ViewPager에서 프래그먼트가 아닌 보통의 View 객체를 호스팅하고자 할 때는 PagerAdapter 인터페이스를 구현하면 된다.

**RecyclerView대신 ViewPager가 사용할 때**
RecyclerView.Adapter 는  자신이 필요한 시점에 곧바로 뷰가 제공되기를 기대하지만 프래그먼트의 뷰가 생성되는 시점은 우리가 아닌 FragmentManager가 결정한다. 따라서 RecyclerView가 프래그먼트의 뷰를 달라고 Adapter에 요청할 때 우리가 즉시 프래그먼트를 생성하고 그것의 뷰를 제공할 수 없을 것이다.

이래서 ViewPager가 존재한다.  ViewPager는 Adapter 대신 PagerAdapter 클래스를 사용한다. PagerAdapter는 Adapter보다 더 복잡하다. 퍋








#android/실무에바로적용하는안드로이드