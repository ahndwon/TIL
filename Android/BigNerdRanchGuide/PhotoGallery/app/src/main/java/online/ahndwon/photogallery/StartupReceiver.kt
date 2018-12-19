package online.ahndwon.photogallery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StartupReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "received broadcst intent: ${intent.action}")
        val isOn = QueryPreferences.isAlarmOn(context)
        PollService.setServiceAlarm(context, isOn)
    }

    companion object {
        val TAG: String = StartupReceiver::class.java.name
    }

}