package online.ahndwon.photogallery

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Gallery
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_photo_gallery.view.*

class PhotoGalleryFragment : VisibleFragment() {
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
        setHasOptionsMenu(true)
        updateItems()

//        activity?.let { PollService.setServiceAlarm(it, true) }

        activity?.let {
            val intent = PollService.newIntent(it)
            it.startService(intent)
        }


        val responseHandler = Handler()
        thumbnailDownloader = ThumbnailDownloader(responseHandler)
        thumbnailDownloader?.mThumbnailDownloadListener =
                object : ThumbnailDownloader.ThumbnailDownloadListener<PhotoHolder> {
                    override fun onThumbnailDownloaded(target: PhotoHolder, thumbnail: Bitmap) {
                        val drawable = BitmapDrawable(resources, thumbnail)
                        target.bindDrawable(drawable)
                    }
                }
        thumbnailDownloader?.start()
        thumbnailDownloader?.looper
        Log.i(TAG, "Background thread started")
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_photo_gallery, menu)

        val searchItem = menu?.findItem(R.id.menu_item_search)
        val searchView = searchItem?.actionView as SearchView?

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Log.d(PhotoGalleryFragment::class.java.name, " QueryTextChange $newText")
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d(PhotoGalleryFragment::class.java.name, " QueryTextSubmit $query")
                activity?.let { QueryPreferences.setStoredQuery(it, query) }
                updateItems()
                return true
            }

        })

        searchView?.setOnSearchClickListener {
            val query = activity?.let { it1 -> QueryPreferences.getStoredQuery(it1) }
            searchView.setQuery(query, false)
        }

        val toggleItem = menu?.findItem(R.id.menu_item_toggle_polling)
        activity?.let {
            if (PollService.isServiceAlarm(it)) {
                toggleItem?.setTitle(R.string.stop_polling)
            } else toggleItem?.setTitle(R.string.start_polling)
        }

    }

    fun updateItems() {
        val query = activity?.let { QueryPreferences.getStoredQuery(it) }
        FetchItemsTask(query).execute()
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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_item_clear -> {
                activity?.let {
                    QueryPreferences.setStoredQuery(it, null)
                }
                updateItems()
                return true
            }

            R.id.menu_item_toggle_polling -> {
                activity?.let {
                    val shouldStartAlarm = !PollService.isServiceAlarm(it)
                    PollService.setServiceAlarm(it, shouldStartAlarm)
                    it.invalidateOptionsMenu()
                    return true
                }
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class FetchItemsTask(val query: String?) : AsyncTask<Void, Void, ArrayList<GalleryItem>>() {
        override fun doInBackground(vararg params: Void?): ArrayList<GalleryItem> {
            return if (query == null) {
                FlickrFetchr().fetchRecentPhotos()
            } else {
                FlickrFetchr().searchPhotos(query)
            }
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