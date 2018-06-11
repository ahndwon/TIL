# Linear Layout
[](https://developer.android.com/images/ui/linearlayout.png?hl=ko)
![](LinearLayout/E9DF4B98-E82B-4AEA-9188-D638634B4768.png)

- 세로 또는 가로의 단일 방향으로 모든 하위 항목을 정렬하는 뷰 그룹.
- `android:orientation` 특성을 사용하여 레이아웃 방향을 지정할 수  있다.
- 모든 하위 항목은 순차적으로 스택된다
	- 세로일 경우 너비에 상관없이 한 행당 하나의 하위 항목만 위치 가능
	- 가로의 경우 높이가 한 행의 높이(가장 큰 하위 항목의 높이 + 패딩)
- 하위 항목 사의의 여백 및 각 하위 항목의 중력(오른쪽, 가운데, 왼쪽 정렬)을 유지한다


## 레이아웃 가중치
- `android:layout_weight` 특성을 이용하여 가중치를 개별하위 항목에 할당할 수 있다
	- 단, 세로 레이아웃일 경우 `android:layout_height`를 0dp, 가로일 경우 `android:layout_width`를 0dp로 설정해야 가중치가 제대로 적용된다.
- 기본 가중치는 0이다
	- 3개의 텍스트 뷰가 있는데 두개만 가중치를 1로 주고 나머지 하나는 가중치를 주지 않으면 자동으로 해당 뷰의 콘텐츠 크기만큼만 영역이 할당된다. 나머지 영역을 가중치가 주어진 뷰들이 채운다.

#android