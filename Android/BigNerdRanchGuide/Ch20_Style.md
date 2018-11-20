# Ch20_Style_Theme
## 스타일 상속
스타일은 상속이 가능
다른 스타일로부터 상속받아 오버라이드 가능

```xml
<style name="BeatBoxButton">
    <item name="android:background">@color/dark_blue</item>
</style>

<style name="BeatBoxButton.Strong">
    <item name="android:textStyle">bold</item>
</style>


// 위 아래는 같다.
<style name="StrongBeatBoxButton" parent="@style/BeatBoxButton">
    <item name="android:textStyle">bold</item>
</style>

```


## 테마
스타일의 단점 -> 적용하기 원하는 각 위젯에 일일이 적용해야함
테마는 스타일과 달리 한 위젯이 아닌 앱 전체에 적용할 수 있다.

테마도 스타일이다.
테마는 스타일과는 다른 속성을 지정한다.

* **Theme.AppCompat** - 어두운 분위기의 테마
* **Theme.AppCompat.Light** - 밝은 분위기의 테마
* **Theme.AppCompat.Light.DarkActionBar** - 어두운 툴바를 갖는 밝은 분위기의 테마

## 테마 색상
* **colorPrimary** - 툴바의 배경색 등 주로 사용되는 주 색상
* **colorPrimaryDark** - colorPrimary보다 어두운 색, 상태 바의 색상으로 사용됨
* **colorAccent** - 앱의 강조되는 부분에 이용되는 색, colorPrimary과 대조인 색이여야 한다.


## 필요한 테마 찾기
정리된 문서가 없으므로 직접 XML를 뒤져야 함.

styles.xml  파일을 열고 Theme.AppCompat을 Cmd, 혹은 Ctrl 키를 누른채 마우스로 클릭 ->. values.xml에서 찾음







#android/실무에바로적용하는안드로이드