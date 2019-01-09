# Ch31_Location
## 위치와 라이브러리
안드로이드의 표준 라이브러리 패키지인 android.location는 기본적인 위치 API를 제공하지만 안드로이드 초창기부터 제공되어 정확도도 떨어지고 위치를 찾느라 배터리도 많이 소모된다. -> Google Play Service를 이용하는 것이 낫다.


## Google Play Service
구글 플레이 스토어가 설치된 장치에서만 사용 가능하다.
**API종류**
1. **Fused Location Provider Api** - 위치 추적
2. **Geofencing Api** - 사용자가 특정 범위에 진입하거나 벗어나는 경우 이를 알려주는 Api
3. **Activity Recognition Api** - 가속도계 센서와 기기의 자율학승 기능을 통해 사용자 움직임을 추적하는 확동 인식 Api

### 구글 플레이 사용 가능 확인
기존의 GooglePlayServicesUtil.isGooglePlayServiceAvailable(Context)는 deprecated 되었으므로 GoogleApiAvailability를 이용해야 한다.

```kotlin
val availability = GoogleApiAvailability.getInstance()
val errorCode = availability.isGooglePlayServicesAvailable(this)

if (errorCode != ConnectionResult.SUCCESS) {
    val errorDialog = availability.getErrorDialog(this, errorCode, REQUEST_ERROR) {
finish()
    }
errorDialog.show()
}
```

### 위치 관련 퍼미션
* android.permission.ACCESS_FINE_LOCATION - GPS
* android.permission.ACCESS_COARSE_LOCATION - 기지국, Wifi 액세스 포인트


#android/실무에바로적용하는안드로이드