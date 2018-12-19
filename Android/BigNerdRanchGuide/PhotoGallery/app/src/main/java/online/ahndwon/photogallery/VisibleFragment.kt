package online.ahndwon.photogallery

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.fragment.app.Fragment

abstract class VisibleFragment : Fragment() {
    companion object {
        val TAG: String = VisibleFragment::class.java.name
    }

    private val mOnShowNotification: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i(TAG, "canceling notification")
            resultCode = Activity.RESULT_CANCELED
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(PollService.ACTION_SHOW_NOTIFICATION)
        activity?.registerReceiver(mOnShowNotification, filter, PollService.PERM_PRIVATE, null)
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(mOnShowNotification)
    }
}