Saving UI States

# Saving UI States

## 유저에 의한 UI 상태 손실

- 백 버튼 사용
- 최근 앱 화면에서 액티비티 제거
- 액티비티에서 navigating up
- Settings 화면에서 앱 종료
- Activity를 finish 하는 경우

위와 같은 상황엔 유저가 종료한것이기 때문에 상태를 보존하지 않아도 상관이 없다.

물론 예외인 상황도 존재


## 시스템에 의한 UI 상태 손실
- 화면 회전
- 멀티 윈도우 모드로 진입
- 유저가 다른 앱으로 전환


위와 같은 시나리오에서 시스템이 액티비티를 파괴할 수 도 있다.



![c53acc0299140a37623fd548ae235952.png](../_resources/9d2e65b4564c491f8bf219f482f2fa65.png)


## 대처 방법
### ViewModel 과 onSavedInstanceState 혼합 사용


ViewModel은 시스템에 의한 액티비티 종료에 살아나질 못하므로 onSavedInstanceState과 같이 사용되어야 한다.





출처 : [Saving UI States](https://developer.android.com/topic/libraries/architecture/saving-states.html)