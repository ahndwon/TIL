# Ch28_WebView
## 웹뷰
브라우저를 실행하는 대신 앱의 내부에서 웹 콘텐트를 보여주고 싶은 경우에 사용
-> 직접 생성한 HTML을 보여준다거나, 웹 브라우저의 사용을 피하고 싶은 경우



## 구성 변경을 액티비티가 처리
```xml
<manifest>
	<activity
		android:name=".PhotoPageActivity"
		android:configChanges="keyboardHidden|orientation|screenSize" />
</manifest>
```
* 소프트 키보드가 열리거나 닫힘, 장치 방향이 바뀜, 화면 크기가 변경됨 등의 이유로 장치의 구성 변경 -> 액티비티가 그 변경을 스스로 처리

## 구성 변경 처리의 위험성
1. 리소스 수식자가 더 이상 자동으로 동작하지 않는다. -> 개발자가 직접 뷰를 다시 로드해야함.
2. Activity.onSaveInstanceState()의 오버라이딩을 소홀히 할 수 있다. 메모리 부족으로 인해 액티비티가 소멸 및 재생성되는것을 대비하기 위해 오버라이딩이 필요하다.


## 자바스크립트 객체 추가하기
```java
mWebView.addJavascriptInterface( new Object() {
	@JavascriptInterface
	public void send(String message) {
		Log.i(TAG, "Received message: " + message);
	}
}, "androidObject");
```

생성한 객체 사용
```html
<input type="button" value="In WebView!"
	onClick="sendToAndroid('In Android Land')" />

<script type="text/javascript.
	function sendToAndroid(message) {
		androidObject.send(message);
	}
</script>
```

그러나 이렇게 사용하면 수상한 웹페이지가 앱을 건드릴수 이쓰므로 위험하다.

## 킷캣의 WebView 기능 향상
Api 19 부터 기능이 향상됨.
새로운 Webview는 Chromium 오픈 소스 프로젝트를 기반으로 한다.
안드로이드 크롬 앱과 동일한 렌더링 엔진을 사용하여 일관된 인터페이스와 기능을 갖는 웹 페이지 사용 가능
[웹뷰와 크롬 차이](http://developer.chrome.com/multidevice/webview/overview)












#android/실무에바로적용하는안드로이드