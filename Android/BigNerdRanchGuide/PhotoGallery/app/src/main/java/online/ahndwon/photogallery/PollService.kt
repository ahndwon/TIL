package online.ahndwon.photogallery

import android.app.*
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.SystemClock
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class PollService : IntentService(TAG) {
    companion object {
        val TAG = "PollService"
        private const val POLL_INTERVAL = AlarmManager.INTERVAL_FIFTEEN_MINUTES
        const val ACTION_SHOW_NOTIFICATION = "online.ahndwon.photogallery.SHOW_NOTIFICATION"
        const val PERM_PRIVATE = "online.ahndwon.photogallery.PRIVATE"
        const val REQUEST_CODE = "REQUEST_CODE"
        const val NOTIFICATION = "NOTIFICATION"

        fun newIntent(context: Context): Intent {
            return Intent(context, PollService::class.java)
        }

        fun setServiceAlarm(context: Context, isOn: Boolean) {
            val i = PollService.newIntent(context)
            val pi = PendingIntent.getService(context, 0, i, 0)

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            if (isOn) {
                alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(),
                    POLL_INTERVAL, pi
                )
            }
            QueryPreferences.setAlarmOn(context, isOn)
        }

        fun isServiceAlarm(context: Context): Boolean {
            val intent = PollService.newIntent(context)
            val pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_NO_CREATE)
            return pi != null
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        if (!isNetworkAvailableAndConnected()) {
            return
        }

        val query = QueryPreferences.getStoredQuery(this)
        val lastResultId = QueryPreferences.getLastResultId(this)
        val items = if (query == null) {
            FlickrFetchr().fetchRecentPhotos()
        } else FlickrFetchr().searchPhotos(query)

        if (items.size == 0) return

        val resultId = items[0].id

        if (resultId == lastResultId) {
            Log.i(TAG, "got a old result : $resultId")
        } else {
            Log.i(TAG, "got a new result : $resultId")

            val i = PhotoGalleryActivity.newIntent(this)
            val pi = PendingIntent.getActivity(this, 0, i, 0)
            val notification = NotificationCompat.Builder(this)
                .setTicker(resources.getString(R.string.new_pictures_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.new_pictures_title))
                .setContentText(resources.getString(R.string.new_pictures_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()

//            val notificationManager = NotificationManagerCompat.from(this)
//            notificationManager.notify(0, notification)
//            sendBroadcast(Intent(ACTION_SHOW_NOTIFICATION), PERM_PRIVATE)
            showBackgroundNotifcation(0, notification)
        }
        QueryPreferences.setLastResultId(this, resultId)
    }

    private fun showBackgroundNotifcation(requestCode: Int, notification: Notification) {
        val intent = Intent(ACTION_SHOW_NOTIFICATION)
        intent.putExtra(REQUEST_CODE, requestCode)
        intent.putExtra(NOTIFICATION, notification)
        sendOrderedBroadcast(intent, PERM_PRIVATE, null, null, Activity.RESULT_OK, null, null)
    }

    private fun isNetworkAvailableAndConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isNetworkAvailable = cm.activeNetworkInfo != null
        return isNetworkAvailable && cm.activeNetworkInfo.isConnected
    }
}