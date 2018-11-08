# Ch16_Camera_Intent
## 외부 스토리지
SQLite 
만일 다른 애플리케이션과 공유할 파일을 저장하거나 또는 다른 앱에서 파일을 받는 경우에는 외부 스토리지에 저장해야 한다.

## 외부 스토리지 종류
모든 안드로이드 장치에는 최소한 하나의 외부 스토리지 위치가 존재함

* **기본 외부 스토리지**
`Environment.getExternalStorageDirectory()` 로 반환되는 폴더의 위치임
SD카드가 될 수도 있다.
* **기타 외부 스토리지**
기본 외부 스토리지와는 별도로 추가적인 외부 스토리지를 갖는 경우

장치에 따라선 교체 가능한 SD카드를 외부 스토리지를 사용하기도 한다.
그러나 요즘은 거의 모든 장치들은 교체 불가능한 내부 스토리지를 ‘외부’ 스토리지로 갖고 있다.


## 사진 위치 얻기
`context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)`



## 카메라 인텐트 사용하기
카메라 인텐트는 **MediaStore** 에 정의됨
**Media.ACTION_IMAGE_CAPTURE**을 통해 암시적 인텐트 요청 가능

### 외부 스토리지 퍼미션
외부 스토리지에 읽거나 쓰기 위해선 퍼미션이 필요함.
<uses-permission> 태그에 퍼미션을 명시해서 Android_Manifest에 추가해야함.

하지만 킷캣4.4에서 부턴 **Context.getExternalFilesDir(String)**를 통해서 앱에 국한된 폴더를 반환하는데 이 폴더에 대해서는 별도로 퍼미션을 줘야할 필요가 없다.

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"
    android:maxSdkVersion="18"/>
```

Api 18까지만 퍼미션이 필요하므로 maxSdkVersion 속성을 추가해준다.


## 인텐트 요청하기
**Media.ACTION_IMAGE_CAPTURE** 액션을 통해 요청한 결과는 
onActivityResult()에서 반환된다. **그러나** 전체 해상도가 아닌, 썸네일 이미지가 반환됨

원본 이미지를 얻기 위해선 인텐트의 엑스트라 데이터로 **Media.EXTRA_OUTPUT** 상수와  사진을 저장할 위치의 Uri를 전달해야 함.

```kotlin
val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
val uri = Uri.fromFile(photoFile)
captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri)

startActivityForResult(captureImage, REQUEST_PHOTO)
```

### android.os.FileUriExposedException
File의 경로를 uri로 변경하고 MediaPlayer나 Intent로 전달하려고 할 경우 발생하는 Exception
API 24이상에서 발생한다.
FileProvider를 이용하여 임시 권한을 할당해줘야 함


#### 해결 방법
1. **res_xml_provider_paths.xml**
xml을 통하여 권한을 주고자 하는 폴더를 지정할 수 있다
내장인지 외장인지, 데이터 영역인지 캐쉬영역인지 기본 위치를 지정하고,
path를 통하여 세세한 경로를 지정할 수 있다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <files-path name="name" path="path" /> <!-- Context.getFilesDir(). -->
    <cache-path name="name" path="path" /> <!-- getCacheDir(). -->
    <external-path name="name" path="path" /> <!-- Environment.getExternalStorageDirectory(). -->
    <external-files-path name="name" path="path" /> <!-- Context#getExternalFilesDir(String) Context.getExternalFilesDir(null). -->
    <external-cache-path name="name" path="path" /> <!-- Context.getExternalCacheDir(). -->
</paths>
```


내장 캐쉬 영역에 images 폴더에 권한을 주는 설정
```xml
<?xml version="1.0" encoding="utf-8"?>
<paths>
    <cache-path name="images" path="./images" /> <!-- getCacheDir(). -->
</paths>
```


2. **AndroidManifest.xml**
xml로 지정한 path를 AndroidManifest에 지정 시켜 줍니다.
```xml
<provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="${applicationId}.fileprovider"
    android:exported="false"
    android:grantUriPermissions="true">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/provider_paths" />
</provider>
```

3. **FileProvider 사용**
FileProver.getUrilForFile를 통해 파일의 Uri 를 받아온다.
`FLAG_GRANT_READ_URI_PERMISSION` 권한을 줘야지만 사용할 수 있다

```kotlin
val intent = Intent(Intent.ACTION_VIEW)
intent.setDataAndType(
FileProvider.getUriForFile(this, applicationContext.packageName + ".fileprovider", File(filePath))
, "audio/*")
intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
startActivity(intent)
```


## 비트맵의 크기 조정과 보여주기
### Bitmap 객체 로드하기
`Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getPath())`

BitmapFactory를 통해 Bitmap 객체를 얻을 수 있다.
원래 파일은 압축되었어도 Bitmap 객체는 압축되지 않아 용량이 과도하게 커질 수 있다.
그래서 비트맵의 크기를 조정해야 함.





#android/실무에바로적용하는안드로이드