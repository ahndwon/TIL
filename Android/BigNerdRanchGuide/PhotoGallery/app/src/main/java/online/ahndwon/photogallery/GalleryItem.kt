package online.ahndwon.photogallery

class GalleryItem(val caption: String, val id : String, val url: String) {


    override fun toString(): String {
        return caption
    }
}