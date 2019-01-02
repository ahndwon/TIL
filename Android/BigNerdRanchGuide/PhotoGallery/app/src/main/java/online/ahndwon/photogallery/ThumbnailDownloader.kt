package online.ahndwon.photogallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap

class ThumbnailDownloader<T>(private var mResponseHandler: Handler) : HandlerThread(TAG) {
    private val mRequestMap = ConcurrentHashMap<T, String>()
    private var mRequestHandler: Handler? = null
    var mThumbnailDownloadListener : ThumbnailDownloadListener<T>? = null

    companion object {
        val TAG: String = ThumbnailDownloader::class.java.name
        const val MESSAGE_DOWNLOAD = 0
    }

    fun queueThumbnail(target: T, url: String?) {
        Log.i(TAG, "Got a URL: $url")

        if (url == null) {
            mRequestMap.remove(target)
        } else {
            mRequestMap[target] = url
            mRequestHandler?.obtainMessage(MESSAGE_DOWNLOAD, target)
                ?.sendToTarget()
        }
    }

    fun clearQueue() {
        mRequestHandler?.removeMessages(MESSAGE_DOWNLOAD)
    }

    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mRequestHandler = Handler() { message ->
            if (message.what == MESSAGE_DOWNLOAD) {
                val target = message.obj as T
                handleRequest(target)
                true
            } else false
        }
    }

    private fun handleRequest(target: T) {
        try {
            val url = mRequestMap[target] ?: return

            val bitmapBytes = FlickrFetchr().getUrlBytes(url)
            val bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.size)
            Log.i(ThumbnailDownloader::class.java.name, "Bitmap Created")

            mResponseHandler.post {
                Log.i(ThumbnailDownloader::class.java.name, "post")
                if (mRequestMap[target] == null) {
                    return@post
                }
                mRequestMap.remove(target)
                Log.i(ThumbnailDownloader::class.java.name, "mThumbnailDownloadListener : $mThumbnailDownloadListener")
                mThumbnailDownloadListener?.onThumbnailDownloaded(target, bitmap)
            }
        } catch (ioe: IOException) {
            Log.i(ThumbnailDownloader::class.java.name, "Error downloading image", ioe)
        }
    }

    interface ThumbnailDownloadListener<T> {
        fun onThumbnailDownloaded(target: T, thumbnail: Bitmap)
    }
}