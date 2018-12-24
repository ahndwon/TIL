# Email_Intent
유저가 메일을 보내도록 암시적 인텐트를 사용할 경우
`Intent.ACTION_SEND` 를 사용 할 경우 이메일앱 외에도 다른 앱들이 선택 목록에 뜨게 된다.
오직 이메일 앱만 뜨게 하기 위해선
`Intent.ACTION_SENDTO`를 사용하고 메일 대상을 uri로 만들어서 인텐트를 생성한후 `Intent.createChooser(Intent, String)`으로 startActivity() 를 해주면 된다.

### 사용법
```kotlin
val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:email@email.com"))
startActivity(Intent.createChooser(intent, "Send Email"))
```




#android/gist