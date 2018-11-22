# Ch21_XML_drawables
## 레이어 리스트
Layer-list를 사용하면 사용하면 두 개의 XML Drawable을 하나로 결합할 수 있다

```xml
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape xmlns:android="http://schemas.android.com/apk/res/android"
            android:shape="oval">
            <solid android:color="@color/red" />
        </shape>
    </item>

    <item>
        <shape android:shape="oval">
            <stroke
                android:width="4dp"
                android:color="@color/dark_red" />
        </shape>
    </item>
</layer-list>
```


## XML Drawable을 사용해야 하는 이유
XML drawable은 유연성이 좋다. 따라서 여러 가지 목적으로 사용될 수 있다.
-> 레이어 리스트 drawable과 형태 drawable을 조합해서 사용하면 이미지 편집기를 사용하지 않고도 복잡한 배경을 만들 수 있다.


## 9-patch 이미지
9-patch 이미지는 뒤에 .9.png로 확장자가 끝난다.
영역을 선택해서 확장될 곳을 지정할 수 있다.



## Mipmap 이미지
리소스 수식자와  drawable은 편리하지만 단점이 있다. 
구글 플레이 스토어에 업로드하는 APK 파일에는 화면 밀도마다 우리 프로젝트의 drawable 디렉터리들에 저장했던 모든 이미지가 포함된다.
-> 과대 포장을 해소하기 위해 각 화면 밀도마다 별도의 APK를 생성할 수도 있다. Mdpi APK 또는 hdpi APK 등이다. 


그러나 모든 화면 밀도의 론처 아이콘을 유지할 때는 다르다.
폰마다 화면 밀도가 다르고 또 런처에 따라 보여주는 아이콘의 크기가 다르기 때문이다.
-> mimap이미지 사용


APK 분할을 사용해도 mipmap은 apk로부터 발췌되지 않는다.


#android/실무에바로적용하는안드로이드