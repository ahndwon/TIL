SurfaceView

# SurfaceView
**서피스뷰 (SurfaceView)**
커스텀뷰를 생성하여 `onDraw()` 메소드를 오버라이드 할 시엔 모든 그리는 작업이 UI 스레드에서 이루어진다. UI 스레드에서 작업이 일어나면 매 화면 리프레쉬 마다 작업이 끝나야 하므로 제한이 생긴한다.

이 제한을 해결하는 방법 중 하나가 drawing 작업을 `SurfaceView`를 사용하여 다른 스레드에가 작업하도록 하는 것이다.

	* 뷰 계층 (view hierarchy)에 존재하는 모든 뷰는 UI 스레드에서 하나의 `Surface`에서 렌더링 된다.
	* 안드로이드 프레임워크 컨텍스트 상에서, `Surface`는 컨텐츠가 유저의 스크린에 표시되는 `lower-level drawing surface`이다.
	* `SurfaceView`는 별도의 `Surface`를 가지는 뷰계층에 존재하는 뷰이다. 다른 스레드에서 여기에 그릴 수 있다.

## 서피스 뷰에 그리는 방법
1. 스레드를 시작
2. SurfaceView의 캔버스를 LOCK
3. Draw
4. Surface에 post


[image:66A4BA29-02C2-4C5F-8063-B032D94C4CBC-728-000002A784A798C0/35a2326c7a27bb29.png]
![View Hierarchy](https://codelabs.developers.google.com/codelabs/advanced-android-training-surfaceviews/img/35a2326c7a27bb29.png)



[Advanced Android 11.2: SurfaceView objects](https://codelabs.developers.google.com/codelabs/advanced-android-training-surfaceviews/index.html?index=..%2F..advanced-android-training#0)










#네이버-인턴