package online.ahndwon.photogallery

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Gallery
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_photo_gallery.view.*

class PhotoGalleryFragment : Fragment() {
    companion object {
        val TAG: String = PhotoGalleryFragment::class.java.name

        fun newInstance(): PhotoGalleryFragment {
            return PhotoGalleryFragment()
        }
    }

    var mItems = ArrayList<GalleryItem>()
    var thumbnailDownloader: ThumbnailDownloader<PhotoHolder>? = null

    class PhotoHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val galleryImageView = view as ImageView

        fun bindDrawable(drawable: Drawable) {
            galleryImageView.setImageDrawable(drawable)
        }
    }

    inner class PhotoAdapter(val items: List<GalleryItem>) : RecyclerView.Adapter<PhotoHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            val inflater = LayoutInflater.from(activity)
            val view = inflater.inflate(R.layout.gallery_item, parent, false)
            return PhotoHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val galleryItem = items[position]
            val placeholder = resources.getDrawable(R.drawable.bill_up_close, null)
            holder.bindDrawable(placeholder)
            thumbnailDownloader?.queueThumbnail(holder, galleryItem.url)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        FetchItemsTask().execute()

        val responseHandler = Handler()
        thumbnailDownloader = ThumbnailDownloader(responseHandler)
        thumbnailDownloader?.mThumbnailDownloadListener = object: ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder> {
            override fun onThumbnailDownloaded(target: PhotoHolder, thumbnail: Bitmap) {
                val drawable = BitmapDrawable(resources, thumbnail)
                target.bindDrawable(drawable)
            }
        }
        thumbnailDownloader?.start()
        thumbnailDownloader?.looper
        Log.i(TAG, "Background thread started")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)

        view.photoRecyclerView.layoutManager = GridLayoutManager(view.context, 3)

        setupAdapter(view.photoRecyclerView)

        return view
    }

    private fun setupAdapter(recyclerView: RecyclerView) {
        if (isAdded) {
            recyclerView.adapter = PhotoAdapter(mItems)
        }
    }

    inner class FetchItemsTask : AsyncTask<Void, Void, ArrayList<GalleryItem>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<GalleryItem> {
            return FlickrFetchr().fetchItems()
        }

        override fun onPostExecute(result: ArrayList<GalleryItem>) {
            Log.d(TAG, "result : $result")
            mItems = result
            view?.photoRecyclerView?.let { setupAdapter(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        thumbnailDownloader?.clearQueue()
    }

    override fun onDestroy() {
        super.onDestroy()
        thumbnailDownloader?.quit()
        Log.i(TAG, "Background thread destroyed")
    }
}