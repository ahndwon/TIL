# getElapsedTime()
## Date 로부터 경과 시간 텍스트 얻어내기

```kotlin

fun String.toDate(dateFormat: SimpleDateFormat): Date {
    dateFormat.timeZone= TimeZone.getDefault()
    return dateFormat.parse(this)
}

fun Date.getPassedTime(): String {
    val millis = Date()./time/- this./time/

	  val sec = millis / 1000
    val min = millis / (60 * 1000)
    val hour = millis / (60 * 60 * 1000)
    val day = millis / (24 * 60 * 60 * 1000)
    val week = millis / (7 * 24 * 60 * 60 * 1000)
    val month = (millis / (30.5 * 24 * 60 * 60 * 1000)).toInt()
    val year = day / 365

    return when {
        year >= 365 -> "${(day / 365)}년 전"
        month > 0 -> "${month}달 전"
        week > 0 -> "${week}주 전"
        day > 0 -> "${day}일 전"
        hour > 0 -> "${hour}시간 전"
        min > 0 -> "${min}분 전"
        else -> "${sec}초 전"
    }
}
```


#android/gist