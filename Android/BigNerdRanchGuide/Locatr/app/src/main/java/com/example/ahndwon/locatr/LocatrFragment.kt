package com.example.ahndwon.locatr

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.ImageView
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import online.ahndwon.photogallery.GalleryItem
import java.io.IOException

class LocatrFragment : Fragment() {
    lateinit var mImageView: ImageView
    lateinit var mClient: GoogleApiClient

    companion object {
        fun newInstance(): LocatrFragment {
            return LocatrFragment()
        }

        const val REQUEST_CODE_ACCESS_FINE_LOCATION = 444

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.let {
            mClient = GoogleApiClient.Builder(it)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                    override fun onConnected(p0: Bundle?) {
                        activity?.invalidateOptionsMenu()
                    }

                    override fun onConnectionSuspended(p0: Int) {

                    }

                })
                .build()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_locatr, container, false)
        mImageView = view.findViewById(R.id.image)
        return view
    }


    override fun onStart() {
        super.onStart()
        activity?.invalidateOptionsMenu()
        mClient.connect()
    }

    override fun onStop() {
        super.onStop()
        mClient.disconnect()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_locatr, menu)

        val searchItem = menu?.findItem(R.id.action_locate)
        searchItem?.isEnabled = mClient.isConnected
    }

    private fun findImage() {
        val request = LocationRequest.create()
        request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        request.numUpdates = 1
        request.interval = 0
        view?.context?.let {
            checkLocationPermission(it)
        }

        LocationServices.FusedLocationApi
            .requestLocationUpdates(
                mClient, request
            ) { location ->
                Log.i(LocatrFragment::class.java.name, "Got a fix: $location")
                SearchTask().execute(location)
            }
    }

    private fun checkLocationPermission(context: Context): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_ACCESS_FINE_LOCATION
            )

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_locate -> {
                findImage()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private inner class SearchTask : AsyncTask<Location, Void, Void>() {
        lateinit var mGalleryItem: GalleryItem
        lateinit var mBitmap: Bitmap
        override fun doInBackground(vararg params: Location): Void? {
            val fetchr = FlickrFetchr()
            val items = fetchr.searchPhotos(params[0])

            if (items.size == 0) {
                return null
            }

            mGalleryItem = items[0]

            try {
                val bytes = fetchr.getUrlBytes(mGalleryItem.url)
                mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            } catch (ioe : IOException) {
                Log.i(LocatrFragment::class.java.name, "Unable to download bitmap", ioe)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mImageView.setImageBitmap(mBitmap)
        }
    }
}