package online.ahndwon.photogallery

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

class PollService : IntentService(TAG) {
    companion object {
        val TAG = "PollService"

        fun newIntent(context: Context): Intent {
            return Intent(context, PollService::class.java)
        }
    }
    override fun onHandleIntent(intent: Intent?) {
        if (!isNetworkAvailableAndConnected()) {
            return
        }
    }

    private fun isNetworkAvailableAndConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isNetworkAvailable = cm.activeNetworkInfo != null
        return isNetworkAvailable && cm.activeNetworkInfo.isConnected
    }
}