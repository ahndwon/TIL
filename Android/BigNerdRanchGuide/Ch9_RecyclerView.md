# Ch9_RecyclerView
## RecyclerView
![RecyclerView](http://2.bp.blogspot.com/-FEQmu0m6m0k/Wd2k3Ij6GWI/AAAAAAAALk0/j94Ao9EkblAahHJH3ohCZKLNVhIvftnFwCK4BGAYYCw/s1600/03-recyclerview-diagram.png)
ViewGroup의 서브 클래스, 자식 View 객체들의 리스트를 보여준다. ListView와 다르게 자식들을 한번에 모두 생성하지 않고 화면을 채우는데 필요한 갯수만큼 만 생성한다. 그리고 화면 밖으로 View가 나갈 경우 그 View를 재활용하여 새로운 객체를 보여준다.

### ViewHolder, Adapter
RecyclerView는 View들을 재활용하고 화면에 보여주는 책임만 갖는다. 그러므로 그 View에 표시할 정보들은 ViewHolder 와 Adapter를 통해 구현해줘야 한다. 

**ViewHolder**  - 하나의 View를 보존하는 일을 한다. ViewHolder의 서브클래스를 생성하여 이용한다.

**어댑터** - RecyclerView의 ViewHolder를 생성한다. 컨트롤러 객체로 RecyclerView와 RecyclerView가 보여줄 데이터 사이에 위치한다. 그리고 모델 계충의 데이터를 ViewHolder와 결합한다.



**리사이클러 뷰 설정**
```java
public class CrimeListFragment extends Fragment {
	private RecyclerView mCrimeRecyclerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
		mCrimeRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
		mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		return view;
	}
}
```


## ListView와 GridView
RecyclerView와 다르게 ViewHolder 패턴을 강제하지 않는다. RecyclerView에 대비 동작을 변경하는 것이 너무 복잡하다. 애니메이션 커스터마이징도 힘들다. -> RecyclerView로 대체됨

## 싱글톤
안드로이드에서 흔히 사용되는 패턴. 싱글톤은 프래그먼트나 액티비티보다 더 오래 존재하므로 편리하다. 데이터를 전달하기 편하다

**단점**
1. 생명주기를 가지므로 장기간 데이터 저장엔 부적합하다.
2. 코드의 단위 테스트를 어렵게 한다 -> 의존성 인젝터로 해결
3. 잘못 사용될 가능성이 있다 -> 유지 보수 힘들어짐

#android/실무에바로적용하는안드로이드