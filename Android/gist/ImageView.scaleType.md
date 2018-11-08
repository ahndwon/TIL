# ImageView.scaleType
ImageView 에 이미지를 최대한 원본 비율을 유지하면서 꽉차게 하고 싶은 경우
이미지 뷰에 다음 속성을 추가해야 한다.

```xml
android:adjustViewBounds="true"
android:scaleType="fitCenter"
```

**adjustViewBounds** 를 통해 이미지를 원본 비율을 유지하도록 하고
**scaleType**을 통해서 이미지를 어떻게 맞출지 결정해주면 된다.

scaleType은 ImageView의 형태와 이미지의 형태에 따라 맞춰서 사용해야 한다.

![](ImageView.scaleType/imageview-scaletype-examples.png)
[ImageView ScaleType](http://images.zoftino.com/development/android-dev/ui/imageview-scaletype-examples.png)!
#android/gist