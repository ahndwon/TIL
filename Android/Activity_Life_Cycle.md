# Activity Life Cycle
![](Activity_Life_Cycle/6B045AF0-CA58-46C1-A0AE-AFACB555B5FC.png)

## 액티비티 생명주기
onCreate() -> onStart() -> onResume() -> onPause() -> onStop() -> onDestroy()
경우에 따라서 onRestart() 메소드 호출


![](Activity_Life_Cycle/49E5F69E-BC48-44F4-A61A-647FB6C168B2.png)


 **해당 Activity**
1. onCreate()
2. onStart()
3. onResume()
4. onRestart()

**다른 Activity가 호출 되는 경우**
1. onPause()
2. onStop()
3. onDestroy()



## 각 생명주기에서 하기 좋은 처리
* **onCreate** : 초기화 처리, 뷰 생성 등
* **onStart** : 통신이나 센서 처리 시작
* **onRestart** : 보통 아무 작업 안 함.
* **onResume** : 필요한 애니메이션 실행 등의 화면 갱신 처리
* **onPause** : 애니메이션 등 화면 갱신 처리 정지 또는 일시 정지할 때 필요 없는 리소스 해체하거나 필요한 데이터 영속화
* **onStop** : 통신이나 센서 처리 정지
* **onDestroy** : 필요 없는 리소스 해제, 액티비티 참조 정리



### 생명주기 메서드 호출 순서

1. **시작할** 때 : onCreate -> onStart -> onResume
2. **화면 회전할 때** : onPause -> onStop -> onDestroy -> onCreate -> onStart -> onResume
3. **다른 액티비티가 위에 뜰 때/전원 키로 화면 OFF할 때/홈 키** : onPause -> onStop
4. **백 키로 액티비티 종료** : onPause -> onStop -> onDestory
5. **백 키로 기존 액티비티에 돌아올 때/홈 키로 나갔다가 돌아올 때** : onRestart -> onStart -> onResume
6. **다이얼로그 액티비티나 투명 액티비티가 위에 뜰 때** : onPause

### 액티비티 라이프타임

* 전체 라이프타임 : onCreate() ~ onDestroy()
* 가시(visible) 라이프타임 : onStart() <  < onStop()
* 포그라운드 라이프타임 : onResume() < < onPause()



참고 : [안드로이드 액티비티(Activity) 생명주기 총정리 :: Programming](http://programmingfbf7290.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%95%A1%ED%8B%B0%EB%B9%84%ED%8B%B0Activity-%EC%83%9D%EB%AA%85%EC%A3%BC%EA%B8%B0-%EC%B4%9D%EC%A0%95%EB%A6%AC)

#android