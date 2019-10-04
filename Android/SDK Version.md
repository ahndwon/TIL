SDK Version

# compileSdkVersion
컴파일 할 Android SDK Version.
complieSdkVersion을 변경해도 런타임 동작이 바뀌지 않는다.
(하지만 경고가 추가될 순 있다)
오직 컴파일 타임에만 사용되므로 APK에는 포함되지 않는다.

항상 최신SDK를 사용하는 것이 권장됨.
서포트 라이브러리를 쓰기 위해선 항상 최신 sdk 버전이여야함.


# minSdkVersion
플레이 스토어에서 설치 할 수 있는 사용자의 기기를 결정함

또한 개발 시, default lint에 영향을 끼침

의존성들이 minSdkVersion 보다 높아야됨

# targetSdkVersion
Android가 forward compatibility 를 제공하는 방법.
update 안하면 behavior가 변경되지 않음.



# 요약
`minSdkVersion <= targetSdkVersion <= compileSdkVersion`


- 이상적인 SdkVersion
`minSdkVersion (lowest possible) <= 
    targetSdkVersion == compileSdkVersion (latest SDK)`









출처 : [Picking your compileSdkVersion, minSdkVersion, and targetSdkVersion](https://medium.com/androiddevelopers/picking-your-compilesdkversion-minsdkversion-targetsdkversion-a098a0341ebd)