# Ch18_Asset
## 리소스가 형태가 아닌 에셋
리소스 형태로 음원 저장 가능
Res/raw 폴더에 음원을 저장하고 ID로 리소스 형태로 가져와서 사용할 수 있다.

그러나 리소스는 폴더 형태로 관리할 수 없다.
-> 에셋의 장점

에셋 시스템은 앱에 포함되는 작은 파일 시스템과 같음. 에셋을 사용하면 우리가 원하는 폴더 구조로 만들어 파일을 저장하고 사용할 수 있다.



## 에셋 파일 액세스하기
에셋 파일의 경로에 있는 파일 열 때는 File 객체를 사용하면 안 되고 AssetManager를 사용해야 한다.

```java
String assetPath = sound.getAssetPath();
InputStream soundData = mAssets.open(assetPath);
```

자바의 InputStream 과 동일하게 표준 InputStream으로 데이터를 읽을 수 있다.
그러나 일부 API에서는 InputStream 대신 FileDescriptor를 사용해야 한다.
-> Assetmanager.openFd(String) 사용

```java
String assetPath = sound.getAssetPath();

// AssetFileDescriptor는 FileDescriptor와 다름
AssetFileDescriptor assedFd = mAssets.openFd(assetPath);

// 그러나 필요하면 통상적인 FileDescriptopr를 쉽게 얻을 수 있음
FileDescriptor fd = assetFd.getFileDescriptor();
```




#android/실무에바로적용하는안드로이드