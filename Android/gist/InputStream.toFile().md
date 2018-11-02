# InputStream.toFile()
자바의 try catch문은 통한 파일 입출력을 코틀릔의 use를 통해 훨씬 간단하게 구현할 수 있다.

심지어 inputStream에서 outputStream으로 데이터를 보내는것도 직접 구현 할 필요없이 `copyTo()`를 사용하면 된다.



## 기존 코드
이미지를 storage에 보내기 위해 sendImage()를 만들었다. 
* 암시적 인텐트 -> 갤러리를 띄우고  -> 선택한 이미지의 Uri를 onActivityResult에서 얻음 -> sendImage() 를 통해 storage에 이미지 전송

contentResolver를 통해 inputStream을 얻고 File.createTempFile()로 임시 파일을 생성하고 outputstream을 열어준다. 그리고 copystream()으로  데이터를 전송.

**단점**
보일러플레이트가 존재하고 try catch문을 사용하여 코드의 가독성이 떨어진다.
inputStream과 outputStream을 직접 닫아줘야됨 -> Java 7 의 try with resource를 통해 해결은 가능

```kotlin
fun sendImage(...) {
	...
	
	val photoFile = createImageFile()

	val inputStream = contentResolver.openInputStream(imageUrl) ?: return
	val outputStream = FileOutputStream(photoFile)
	copyStream(inputStream, outputStream)

	outputStream.close()
	inputStream.close()

	...
}

@Throws(IOException::class)
private fun createImageFile(): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
    val imageFileName = "profile_" + timeStamp + "_"
    // Save a file: path for use with ACTION_VIEW intents
    return File.createTempFile(
            imageFileName, /* prefix */
            "" /* suffix */
    )
}


@Throws(IOException::class)
fun copyStream(input: InputStream, output: OutputStream) {
    val buffer = ByteArray(1024)
    var bytesRead: Int = 0
    while ((bytesRead) != -1) {
        output.write(buffer, 0, bytesRead)
        bytesRead = input.read(buffer)
    }
}

```



## use(), copyTo() 사용
확장 함수에서 use와 copyTo를 통해  보일러플레이트를 제거하고 훨씬 코드가 간결해졌다.

```kotlin

fun sendImage(...) {
	...

	val photoFile = createImageFile()

	val inputStream = contentResolver.openInputStream(imageUrl) ?: return
	inputStream.toFile(photoFile)

	...
}

fun InputStream.toFile(file: File) {
    file.outputStream().use { this.copyTo(it) }
}

```





#android/gist