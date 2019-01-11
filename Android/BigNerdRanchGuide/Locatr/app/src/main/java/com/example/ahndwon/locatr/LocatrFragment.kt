package com.example.ahndwon.locatr

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.*
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import online.ahndwon.photogallery.GalleryItem
import java.io.IOException

class LocatrFragment : SupportMapFragment() {
    lateinit var mClient: GoogleApiClient
    lateinit var mMap: GoogleMap
    lateinit var mMapImage: Bitmap
    lateinit var mMapItem: GalleryItem
    lateinit var mCurrentLocation: Location

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

            getMapAsync { googleMap ->
                mMap = googleMap
//                updateUI()
            }
        }

    }

    private fun updateUI() {
        val itemPoint = LatLng(mMapItem.mLat, mMapItem.mLon)
        val myPoint = LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude)

        val itemBitmap = BitmapDescriptorFactory.fromBitmap(mMapImage)
        val itemMarker = MarkerOptions()
                .position(itemPoint)
                .icon(itemBitmap)

        val myMarker = MarkerOptions()
                .position(myPoint)

        mMap.clear()
        mMap.addMarker(itemMarker)
        mMap.addMarker(myMarker)

        val bounds = LatLngBounds.Builder()
                .include(itemPoint)
                .include(myPoint)
                .build()

        val margin = resources.getDimensionPixelSize(R.dimen.map_inset_margin)
        val update = CameraUpdateFactory.newLatLngBounds(bounds, margin)
        mMap.animateCamera(update)
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
        lateinit var mLocation: Location

        override fun doInBackground(vararg params: Location): Void? {
            mLocation = params[0]
            val fetchr = FlickrFetchr()
            val items = fetchr.searchPhotos(params[0])

            if (items.size == 0) {
                return null
            }

            mGalleryItem = items[0]

            try {
                val bytes = fetchr.getUrlBytes(mGalleryItem.url)
                mBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

            } catch (ioe: IOException) {
                Log.i(LocatrFragment::class.java.name, "Unable to download bitmap", ioe)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mMapImage = mBitmap
            mMapItem = mGalleryItem
            mCurrentLocation = mLocation
            updateUI()
        }
    }
}