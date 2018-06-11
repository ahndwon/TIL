# Relative Layout
![](RelativeLayout/CB0A747F-F2FD-4CB6-A6EC-C63E7E65581E.png)
[](https://developer.android.com/images/ui/relativelayout.png)

- 자식 뷰들을 상대적 위치에 의해 위치시키는 뷰 그룹
- 각 뷰의 위치는 형제 원소들에 상대적이거나 아니면 부모 뷰의 상대적 위치에 의해 정해진다.
- Constraint Layout을 사용하는 것이 더 좋은 성능과 도구 지원 얻을 수 있다.
- Relative Layout은 중첩된 뷰 그룹들을 없애주고 레이아웃 계층을 단순하게 유지할 수 있게 해주기 때문에 성능을 향상시킨다.


## 뷰 위치하기
- Relative Layout은 자식 뷰들이 부모나 서로의 상대적인 위치에 근거하여 자신의 위치를 정한다.
- 따라서 뷰 2개를 오른쪽 가장자리에 나란하게 할 수도 있고, 서로 위아래로 위치시킬 수 도 있으며, 화면 정가운데 등등 다양하게 가능하다.
- 기본값으로 모든 자식 뷰는 좌측 상단에 그려진다.

`android:layout_alignParentTop`
	- 만약 true 일 경우 뷰가 부모의 상단 끝에 위치하게 된다.

`android:layout_centerVertical`
	- 만약 true 일 경우 뷰가 부모의 중앙에 위치하게 된다.

`android:layout_below`
	- 특정 뷰의 ID에 의거하여 그 뷰 아래에 위치시킨다.

`android:layout_toRightOf`
	- 특정 뷰의 ID에 의거하여 그 뷰 우측에 위치시킨다.

더 다양한 특성은 [RelativeLayout.LayoutParams](https://developer.android.com/reference/android/widget/RelativeLayout.LayoutParams.html)에 나와있다.

모든 레이아웃 속성들은 boolean이나 뷰의 ID를 값으로 가진다.






#android