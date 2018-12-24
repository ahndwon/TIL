package com.mabopractice.app.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mabopractice.app.R
import com.mabopractice.app.net.retrieveError
import com.mabopractice.app.ui.activity.StoreActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


val Fragment.supportActivity: AppCompatActivity?
    get() = activity as? AppCompatActivity

fun ActionBar.setBackButton(isOn: Boolean) {
    setDisplayHomeAsUpEnabled(isOn)
    setHomeButtonEnabled(isOn)
}

fun ActionBar.setup() {
    this.setDisplayShowTitleEnabled(false)
    this.setLogo(R.drawable.ic_mabo_text)
}

fun ActionBar.setFragmentTitle(view: TextView, title: String) {
    view.text = title
    this.setDisplayShowTitleEnabled(false)
}

fun ActionBar?.setTitleAndBackButton(view: TextView?, title: String, isButtonOn: Boolean) {
    this?.setLogo(null)
    this?.setBackButton(isButtonOn)
    view?.let { this?.setFragmentTitle(it, title) }
}

fun Activity.setStatusBarColor(color: Int, setIconDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.window.statusBarColor = ContextCompat.getColor(this, color)
    }

    this.setStatusBarIconColor(setIconDark)
}

fun Activity.setStatusBarIconColor(isDark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor = this.window.decorView
        if (isDark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            // We want to change tint color to white again.
            // You can also record the flags in advance so that you can turn UI back completely if
            // you have set other flags before, such as translucent or full screen.
            decor.systemUiVisibility = 0
        }
    }
}


@Throws(IOException::class)
private fun createTempImageFile(): File {
    val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
    val imageFileName = "profile_" + timeStamp + "_"

    return File.createTempFile(
            imageFileName,
            ""
    )
}

fun InputStream.toFile(file: File) {
    file.outputStream().use { this.copyTo(it) }
}

fun Activity.getPhotoByUri(path: Uri): File {
    val photoFile = createTempImageFile()
    val inputStream = contentResolver.openInputStream(path) ?: return photoFile
    inputStream.toFile(photoFile)
    return photoFile
}


fun Fragment.selectImageInAlbum() {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.type = "image/*"
    this.activity?.packageManager?.let {
        if (intent.resolveActivity(it) != null) {
            startActivityForResult(intent, GALLERY)
        }
    }
}

fun convertDpToPixel(context: Context, dp: Float): Float {
    val resources = context.resources
    val metrics = resources.displayMetrics
    return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

fun convertPixelsToDp(context: Context, px: Int): Int {
    return Math.round(px / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.pxToDp(px: Float): Int {
    val scale = this.resources.displayMetrics.density
    return (px * scale + 0.5f).toInt()
}


operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    this.add(disposable)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

    })
}

fun EditText.validate(message: String, validator: (String) -> Boolean) {
    this.afterTextChanged {
        this.error = if (validator(it)) null else message
    }
    this.error = if (validator(this.text.toString())) null else message
}

fun String.isValidEmail(): Boolean = this.isEmpty() ||
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun checkPasswordLength(string: String): Boolean =
        string.isEmpty() || string.length in PASSWORD_LENGTH_RANGE

fun checkNicknameLength(string: String): Boolean =
        string.isEmpty() || string.length in NICKNAME_LENGTH_RANGE

fun String.toDate(dateFormat: SimpleDateFormat): Date {
    dateFormat.timeZone = TimeZone.getDefault()
    return dateFormat.parse(this)
}

fun Date.getPassedTime(): String {
    val millis = Date().time - this.time

    val sec = millis / 1000
    val min = millis / (60 * 1000)
    val hour = millis / (60 * 60 * 1000)
    val day = millis / (24 * 60 * 60 * 1000)
    val week = millis / (7 * 24 * 60 * 60 * 1000)
    val month = (millis / (30.5 * 24 * 60 * 60 * 1000)).toInt()
    //val year = days / 365

    return when {
        day >= 365 -> "${(day / 365)}년 전"
        month > 0 -> "${month}달 전"
        week > 0 -> "${week}주 전"
        day > 0 -> "${day}일 전"
        hour > 0 -> "${hour}시간 전"
        min > 0 -> "${min}분 전"
        else -> "${sec}초 전"
    }
}

@Suppress("DEPRECATION")
fun TimePicker.getSupportHour(): Int {
    return if (Build.VERSION.SDK_INT >= 23)
        this.hour
    else
        this.currentHour
}

@Suppress("DEPRECATION")
fun TimePicker.getSupportMinute(): Int {
    return if (Build.VERSION.SDK_INT >= 23)
        this.minute
    else
        this.currentMinute
}

@Suppress("DEPRECATION")
fun TimePicker.setSupportHour(hour: Int) {
    if (Build.VERSION.SDK_INT >= 23)
        this.hour = hour
    else
        this.currentHour = hour
}

@Suppress("DEPRECATION")
fun TimePicker.setSupportMinute(minute: Int) {
    if (Build.VERSION.SDK_INT >= 23)
        this.minute = minute
    else
        this.currentMinute = minute
}

@Suppress("DEPRECATION")
fun fromHtml(source: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(source)
    }
}

fun Calendar.getHour(): Int {
    return this.get(Calendar.HOUR_OF_DAY)
}

fun Calendar.getMinute(): Int {
    return this.get(Calendar.MINUTE)
}

inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
fun String.fromCloudFront(): String = this.replace("mabopractice.s3.ap-northeast-2.amazonaws.com", "cdn.mabopractice.com")


fun Context.showStoreDialog() {
    alert(message = getString(R.string.store_mabo_ko)) {
        positiveButton(getString(R.string.yes_ko)) {
            startActivity<StoreActivity>()
        }
        negativeButton(getString(R.string.no_ko)) {}
    }.show()
}

fun Date.getCalendar(): Calendar {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal
}

fun Context.handleError(e: Throwable) {
    val error = retrieveError(this, e)
    toast(error.description)
}