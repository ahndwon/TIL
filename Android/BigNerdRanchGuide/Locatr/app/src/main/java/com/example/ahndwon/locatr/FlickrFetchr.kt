package com.example.ahndwon.locatr

import android.location.Location
import android.net.Uri
import android.util.Log
import online.ahndwon.photogallery.GalleryItem
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
        const val FETCH_RECENTS_METHOD = "flickr.photos.getRecent"
        const val SEARCH_METHOD = "flickr.photos.search"
        val ENDPOINT = Uri.parse("https://api.flickr.com/services/rest/")
            .buildUpon()
            .appendQueryParameter("api_key", API_KEY)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s, geo")
            .build()
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

            return out.toByteArray()
        } finally {
            connection.disconnect()
        }

    }


    @Throws(IOException::class)
    fun getUrlString(urlSpec: String): String {
        return String(getUrlBytes(urlSpec))
    }

    fun downloadGalleryItems(url: String) : ArrayList<GalleryItem>{
        try {
//            val url = Uri.parse("https://api.flickr.com/services/rest/")
//                .buildUpon()
//                .appendQueryParameter("method", "flickr.photos.getRecent")
//                .appendQueryParameter("api_key", API_KEY)
//                .appendQueryParameter("format", "json")
//                .appendQueryParameter("nojsoncallback", "1")
//                .appendQueryParameter("extras", "url_s")
//                .build().toString()

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

    fun fetchRecentPhotos(): ArrayList<GalleryItem> {
        val url = buildUrl(FETCH_RECENTS_METHOD, null)
        return downloadGalleryItems(url)
    }

    fun searchPhotos(query: String): ArrayList<GalleryItem> {
        val url = buildUrl(SEARCH_METHOD, query)
        return downloadGalleryItems(url)
    }

    fun searchPhotos(location: Location): ArrayList<GalleryItem> {
        val url = buildUrl(location)
        return downloadGalleryItems(url)
    }


    private fun buildUrl(method: String, query: String?) : String {
        val uriBuilder = ENDPOINT.buildUpon()
            .appendQueryParameter("method", method)

        if (method == SEARCH_METHOD) {
            uriBuilder.appendQueryParameter("text", query)
        }
        return uriBuilder.build().toString()
    }

    private fun buildUrl(location: Location) : String {
        return ENDPOINT.buildUpon()
            .appendQueryParameter("method", SEARCH_METHOD)
            .appendQueryParameter("lat", "" + location.latitude)
            .appendQueryParameter("lon", "" + location.longitude)
            .build().toString()
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
            val owner = photoJsonObject.getString("owner")
            val lat = photoJsonObject.getDouble("latitude")
            val lon = photoJsonObject.getDouble("longitude")
            val item = GalleryItem(caption, id, url, owner, lat, lon)

            items.add(item)
        }
    }
}