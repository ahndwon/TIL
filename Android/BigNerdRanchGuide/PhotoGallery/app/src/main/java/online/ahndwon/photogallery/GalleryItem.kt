package online.ahndwon.photogallery

import android.net.Uri

class GalleryItem(val caption: String, val id: String, val url: String, val owner: String) {


    override fun toString(): String {
        return caption
    }

    fun getPhotoPageUri(): Uri {
        return Uri.parse("http://flickr.com/photos/")
            .buildUpon()
            .appendPath(owner)
            .appendPath(id)
            .build()
    }
}