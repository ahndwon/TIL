package online.ahndwon.criminalintentkotlin.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point

class PictureUtils {
    companion object {
        fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int) : Bitmap {
            var options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)

            val srcWidth = options.outWidth.toDouble()
            val srcHeight = options.outHeight.toDouble()

            var inSampleSize = 1

            if (srcHeight > destHeight || srcWidth > destWidth) {
                inSampleSize = if (srcWidth > srcHeight) {
                    Math.round(srcHeight / destHeight).toInt()
                } else {
                    Math.round(srcWidth / destWidth).toInt()
                }
            }

            options = BitmapFactory.Options()
            options.inSampleSize = inSampleSize
            return BitmapFactory.decodeFile(path, options)
        }

        fun getScaledBitmap(path: String, activity: Activity): Bitmap {
            val size = Point()
            activity.windowManager.defaultDisplay.getSize(size)
            return getScaledBitmap(path, size.x, size.y)
        }
    }
}