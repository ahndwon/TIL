package online.ahndwon.photogallery

import android.net.Uri
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FlickrFetchr {
    val items = ArrayList<GalleryItem>()

    companion object {
        val TAG = FlickrFetchr::class.java.name
        const val API_KEY = "9a0e0796dc9cbd9d447201e5209a3892"
    }

    @Throws(IOException::class)
    fun getUrlBytes(urlSpec: String): ByteArray {
        val url = URL(urlSpec)

        val connection = url.openConnection() as HttpURLConnection


        try {
            val out = ByteArrayOutputStream()
            val inputStream = connection.inputStream

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                throw IOException(connection.responseMessage + ": with " + urlSpec)
            }

            out.use { inputStream.copyTo(it)}

//            var bytesRead = 0
//            val buffer = ByteArray(1024) { 0 }
//            while ((bytesRead) >= 0) {
//                bytesRead = inputStream.read(buffer)
//                out.write(buffer, 0, bytesRead)
//            }
//            out.close()
            return out.toByteArray()
        } finally {
            connection.disconnect()
        }

    }


    @Throws(IOException::class)
    fun getUrlString(urlSpec: String): String {
        return String(getUrlBytes(urlSpec))
    }

    fun fetchItems() : ArrayList<GalleryItem>{
        try {
            val url = Uri.parse("https://api.flickr.com/services/rest/")
                .buildUpon()
                .appendQueryParameter("method", "flickr.photos.getRecent")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("formant", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("extras", "url_s")
                .build().toString()

            val jsonString = getUrlString(url)

            Log.i(TAG, "Received JSON: $jsonString")

            val jsonBody = JSONObject(jsonString)
            parseItems(items, jsonBody)
        } catch (je: JSONException) {
            Log.e(TAG, "Failed to parse Json", je)
        } catch (ioe: IOException) {
            Log.e(TAG, "Failed to fetch items", ioe)
        }

        return items
    }

    @Throws(IOException::class)
    fun parseItems(items: ArrayList<GalleryItem>, jsonBody: JSONObject) {
        val photosJsonObject = jsonBody.getJSONObject("photos")
        val photoJsonArray = photosJsonObject.getJSONArray("photo")

        for (i in 0.until(photoJsonArray.length())) {
            val photoJsonObject = photoJsonArray.getJSONObject(i)

            val id = photoJsonObject.getString("id")
            val caption = photoJsonObject.getString("title")

            if (!photoJsonObject.has("url_s")) {
                continue
            }
            val url = photoJsonObject.getString("url_s")
            val item = GalleryItem(caption, id, url)

            items.add(item)
        }
    }
}