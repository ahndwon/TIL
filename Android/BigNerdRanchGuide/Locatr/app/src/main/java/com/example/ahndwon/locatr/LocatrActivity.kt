package com.example.ahndwon.locatr

import android.support.v4.app.Fragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class LocatrActivity : SingleFragmentActivity() {
    companion object {
        const val REQUEST_ERROR = 0
    }

    override fun createFragment(): Fragment {
        return LocatrFragment.newInstance()
    }

    override fun onResume() {
        super.onResume()
        val availability = GoogleApiAvailability.getInstance()
        val errorCode = availability.isGooglePlayServicesAvailable(this)

        if (errorCode != ConnectionResult.SUCCESS) {
            val errorDialog = availability.getErrorDialog(this, errorCode, REQUEST_ERROR) {
                finish()
            }
            errorDialog.show()
        }
    }
}
