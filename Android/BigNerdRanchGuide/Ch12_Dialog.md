# Ch12_Dialog
## Dialog(대화상자)
* 사용자의 주의를 끌고 입력을 받는 데 사용됨
* 선택할 내용이나 중요한 정보를 보여줄 때 유용하다.


## AppCompat 라이브러리
### AppCompat 라이브러리 사용 방법
의존성(Dependency)에 추가
Android Studio -> File -> Project Structure -> Dependencies 탭 -> 검색해서 추가

## DialogFragment
AlertDialog를 사용할 때는 Fragment의 서브 클래스인 DialogFragment의 인스턴스에 래핑하는 것이 좋다.

DialogFragment 없이 AlertDialog를 보여주는 것이 가능하지만 권장하지 않는다. 왜냐하면 FragmentManager에 의해 대화상자가 관리되는 것이 더 장점이 많고 화면 회전시 화면에서 사라지기 때문이다. 
	





#android/실무에바로적용하는안드로이드