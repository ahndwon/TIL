package online.ahndwon.photogallery

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {

    companion object {
        val TAG: String = NotificationReceiver::class.java.name
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        val requestCode = intent.getIntExtra(PollService.REQUEST_CODE, 0)
        val notification = intent.getParcelableExtra<Notification>(PollService.NOTIFICATION)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(requestCode, notification)
    }
}