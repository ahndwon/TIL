# 알람
Api 21 이상부턴 AlarmManager를 사용시 WakefulBroadCastReceiver가 deprecated

## AlarmManager
AlarmManager + Service + WakefulBroadcastReceiver => WakefulBroadcastReceiver가 deprecated이고 doze에서 작동 안함

## AlarmManager
* AlarmManager(setExactAndWhileIdle) + JobIntentService + BroadcastReceiver  => doze에서 작동 안함

* AlarmManager(setAlarmClock) + JobIntentService + BroadcastReceiver  => doze에서 작동 함 (대신 알람 아이콘 보임)

23 이상부턴 doze mode
**Doze mode에서 실행 가능**
	* AlarmManager - setAllowWhileIdle(), setAndAllowWhileIdle()
	* setAlarmClock()
	* 

## *잠자기 모드 제한 사항*
### 앱이 Doze 상태에 있는 동안 다음과 같은 제한 사항이 적용됩니다.
* 네트워크 액세스가 정지됩니다.
* 시스템은 wake locks를 무시합니다.
* 표준 AlarmManager 알람(setExact() 및 setWindow() 포함)은 다음 유지관리 기간으로 연기됩니다.
	* 잠자기 모드 중 알람이 실행되도록 설정해야 하는 경우에는 setAndAllowWhileIdle() 또는 setExactAndAllowWhileIdle()을 사용합니다.
	* setAlarmClock()으로 설정된 알람은 정상적으로 실행됩니다. 시스템은 알람이 실행되기 직전에 잠자기 모드를 종료합니다.
* 시스템은 Wi-Fi 스캔을 수행하지 않습니다.
* 시스템은 동기화 어댑터 실행을 허용하지 않습니다.
* 시스템은 JobScheduler 실행을 허용하지 않습니다.


## Firebase Cloud Messaging
(Formerly GCM)
* 앱이 지속적인 네트워크 연결이 필요할 경우 사용

#android