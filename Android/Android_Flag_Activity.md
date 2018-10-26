# Android Flag Activity
## FLAG_ACTIVITY 4가지
1. FLAG_ACTIVITY_SINGLE_TOP
2. FLAG_ACTIVITY_CLEAR_TOP
3. FLAG_ACTIVITY_REORDER_TO_FRONT
4. FLAG_ACTIVITY_NO_HISTORY


### Activity Stack
안드로이드 시스템은 Activity를 Activity Stack을 통해 관리하기 된다. 기존 Stack의 개념과 비슷하다.

![](Android_Flag_Activity/0A11E662-740B-43EF-8D07-3EE5C8FD3B39.png)
![Activity Stack](https://t1.daumcdn.net/cfile/tistory/203CA83F50F2A97514)


## 1. FLAG_ACTIVITY_SINGLE_TOP
![](Android_Flag_Activity/BAA5BDC3-ED6C-4EFB-BD7C-3F962E88EE4B.png)
 ![FLAG_ACTIVITY_SINGLE_TOP](https://t1.daumcdn.net/cfile/tistory/1467E55050F188E135)

동일한 Activity Stack에 **연속적으로** 쌓이면 Activity를 재사용 하는 Flag

재사용 Activity는 onPause(), onNewIntent(), onResume() 순서로 동작한다.


**사용시 주의사항**
![](Android_Flag_Activity/E6FAC0AA-C180-4AD7-9B1E-3E910B3001D0.png)
 동일한 Activity가 연속적으로 사용되어야만 Flag Activity가 적용 된다
 그림처럼 동일한 Activity가 연속적으로 사용되지 않는다면 [A] -> [B] -> [C] -> [D] 에 아무리 해당 Flag 속성을 적용해도 적용되지 않고 그냥 차곡차곡 Activity Stack에 쌓인다.

![](Android_Flag_Activity/BDCA7895-3AB7-4E19-923C-558C3928F0E8.png)


## 2. FLAG_ACTIVITY_CLEAR_TOP

![](Android_Flag_Activity/A9E3EFBC-52F0-4AFA-A076-D3F8591974FB.png)
![ FLAG_ACTIVITY_CLEAR_TOP](https://t1.daumcdn.net/cfile/tistory/2435BA4150F15C812A)

기존 Activity Stack에 동일한 Activity가 쌓일 경우, RootActivity와 동일한 Activity만 남긴 후 Stack에 쌓여 있는 나머지 Activity를 모두 onDestroy()시켜 버리는 Flag.

-> 결국 RootActivity와 동일한 Activity 하나만 남게 된다.

동일한 Activity는 onCreate()부터 시작된다.

**RootActivity는 무조건 남음**


만약 재사용 Activity가 onCreate()가 아닌 재사용을 시키고 싶을 경우  -> FLAG_ACTIVITY_SINGLE_TOP 과 같이 사용하면 된다.


## 3.  FLAG_ACTIVITY_REORDER_TO_FRONT
![](Android_Flag_Activity/B8D610FE-B283-4E48-AE45-7ABB63D30183.png)
![FLAG_ACTIVITY_REORDER_TO_FRONT](https://t1.daumcdn.net/cfile/tistory/2674C64A50F16CFA0E)

Activity Stack에 동일한 Activity가 쌓일 경우 무조건 동일한 Activity를 최상위로 올린다. 최상위로 올라간 Activity는 onResume()으로 재시작된다. 

이때 FLAG_ACTIVITY_CLEAR_TOP은 무시된다.


## 4. FLAG_ACTIVITY_NO_HISTORY
![](Android_Flag_Activity/65A29526-668E-46F9-B78F-5DE1BEE20406.png)
![FLAG_ACTIVITY_NO_HISTORY](https://t1.daumcdn.net/cfile/tistory/2522794950F17ACE22)

Activity Stack에서 [B] Activity에 "FLAG_ACTIVITY_NO_HISTORY" 속성을 적용하고 [B] -> [C] Activity를 호출 하는 경우, [C] Activity는 스택에서 제거 된다. 하지만 종료 시점은 [D] Activity가 onDestory() 되는 시점에 [C] Activity도 같이 onDestory() 된다.


기본원리

1-1) [A] -> [B] -> [C] - [D]  (적용 O) ([B]에 속성 추가)  

1-2) [A] -> [B] -> [C] -> [D]  ([C]가 스택에서 제거됨) ([D]에서 백키누름)  

1-3) [A] -> [B]  (결과)


2-1) [A] -> [B] -> [C] -> [C] -> [C]  (적용 O)  (3번째 [C] 에서 속성 추가)

2-2) [A] -> [B] -> [C] -> [C] -> [C]  (4번째 [C] 가 스택에서 제거됨) (5번째 [C]에서 백키누름) 

2-3) [A] -> [B] -> [C]  (결과)





출처: http://arabiannight.tistory.com/entry/286 [아라비안나이트]

#android/공부