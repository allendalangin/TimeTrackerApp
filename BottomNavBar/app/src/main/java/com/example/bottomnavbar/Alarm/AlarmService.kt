package com.example.bottomnavbar.Alarm

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.bottomnavbar.R

/**
 * Created by Fawad on 12/7/2016.
 */
class AlarmService : IntentService("AlarmService") {
    private var alarmNotificationManager: NotificationManager? = null
    override fun onHandleIntent(intent: Intent?) {
        sendNotification("Wake Up! Wake Up!")
    }

    //Use to send the Alarm Notification to the user
    private fun sendNotification(msg: String) {
        Log.d("AlarmService", "Preparing to send notification...: $msg")
        alarmNotificationManager = this
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        val contentIntent: PendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java), FLAG_IMMUTABLE
        )
        val alamNotificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(
            this
        ).setContentTitle("Alarm").setSmallIcon(R.drawable.project_interface)
            .setStyle(NotificationCompat.BigTextStyle().bigText(msg))
            .setContentText(msg)
        alamNotificationBuilder.setContentIntent(contentIntent)
        alarmNotificationManager?.notify(1, alamNotificationBuilder.build())
        Log.d("AlarmService", "Notification sent.")
    }
}