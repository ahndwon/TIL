# Ch10_Fragment_Arguments
## 프래그먼트로부터 액티비티 시작시키기
액티비티와 동일, 내부적으로 부합되는 Activity 메서드를 호출한다.
`Fragment.startActivity(intent)`


### 엑스트라 쓰기

```java
public static Intent newIntent(Context packageContext, UUID crimeId) {
	Intent intent = new Intent(packageContext, CrimeActivity.class);
	intent.putExtra(EXTRA_CRIME_ID, crimeId);
	return intent;
}
```


### 엑스트라 읽기
프래그먼트가 액태비티의 인텐트에 저장된 데이트를 액세스하는 방법

1. **직접 액세스**
```java
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	UUID crimeId = (UUID) getActivity().getIntent()
			.getSerializableExtra(CrimeActivity.EXTRA_CRIME_ID);
	mCrime = CrimeLab.get(getActivity().getCrime(crimeId));
}
```

**단점** - 프래그먼트의 캡슐화가 어려워진다. 프래그먼트를 재사용하기 힘들어짐

2. **프래그먼트 인자 사용**
모든 프래그먼트 인스턴스는 첨부된 Bundle 객체를 가질 수 있다. 이 번들 객체는 Activity의 인텐트 엑스트라와 동일하게 키와 값이 한 쌍으로 된 데이터, 즉 **인자**들을 갖는다. 

프래그먼트 인자는 Bundle 객체를 생성하고 그 객체에 ‘put’메서드를 사용해서 인자를 추가해주면 된다.

```java
Bundle args = new Bundle();
args.putSerializable(EXTRA_MY_OBJECT, myObject);
args.putInt(EXTRA_MY_INT, myInt);
args.putCharSequence(EXTRA_MY_STRING, myString);
```


### 인자를 프래그먼트에 첨부하기
**newInstance()**라는 static 메서드를 Fragment 클래스에 추가하여 인스턴스를 생성하는것이 좋다.

```java
public class CrimeFragment extends Fragment {
	private static final String ARG_CRIME_ID = "crime_id";
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;

	public static CrimeFragment new Instance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(ARG_CRIME_ID, crimeId);
		
		CrimeFragment fragment = new CrimeFragment();
		fragment.setArguments(args);
		return fragment;
	}
}
```


## 인자 가져오기 
프래그먼트가 자신에게 전달된 인자를 액세스해야 할 때는 Fragment 클래스으 메서드인 getArguments()를 호출한 후 Bundle의 ‘get’ 메서드들 중 하나를 호출하면 된다.

```java
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
	mCrime = CrimeLab.getSerialiable(ARG_CRIME_ID);
}
```

## 프래그먼트로부터 결과 받기
액티비티와 비슷하다. Fragment.startActivityForResult(),  Fragment.onActivityResult() 를 오버라이드하면 된다.

단, 프래그먼트는 액티비티로부터 결과값을 받을 수 있지만 자신의 결과 값은 가질 수 없다. 액티비티만이 결과 값을 가진다. 따라서 Fragment는 자신의 startActivityForResult(...)와 onActivityResult() 메서드를 갖지만, setResult() 메서드는 갖지 않는다.

대신 다음과 같이 호스팅 액티비티에 결과 값의 반환을 알려주면 된다.
```java
public class CrimeFragment extends Fragment {
	public void returnResult() {
		getActivity().setResult(Activity.RESULT_OK, null);
	}
}
```

## 프래그먼트 인자를 사용하는 이유
항상 제대로 동작할거라는 보장이 없다.
장치의 구성에 변경이 생기거나 또는 사용자가 우리 앱을 종료하면 안드로이드 운영체제가 우리 프래그먼트의 모든 메모리를 회수할 수 있으므로 프래그먼트 인스턴스 변수는 모두 소멸될 것이다. 그리고 우리 프래그먼트 인스턴스가 다시 생성된다. 이 때 언제 메모리가 부족해서 회수될지 알 수 없으므로 프래그먼트의 인스턴스 변수를 설정하는 것은 신뢰할 수 있는 방법이 아니다.




#android/실무에바로적용하는안드로이드