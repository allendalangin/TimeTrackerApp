package com.example.bottomnavbar.Alarm

import android.os.Build
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.bottomnavbar.MainActivity
import com.example.bottomnavbar.R

object NotificationCreator {
    private const val LOG_TAG = "NotificationCreator"
    const val CHANNEL_ID = "TestAlarmManager"
    const val NOTIFICATION_TITLE = "Test Notification"
    @JvmStatic
    fun createLockScreenNotification(context: Context) {
        Log.d(LOG_TAG, "createNotification")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                NOTIFICATION_TITLE,
                NotificationManager.IMPORTANCE_HIGH
            )
            serviceChannel.enableVibration(true)
            serviceChannel.enableLights(true)
            serviceChannel.setShowBadge(true)
            val manager = context.getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
            val contentIntent = Intent(context, MainActivity::class.java)
            val contentPendingIntent =
                PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_MUTABLE)
            val fullScreenIntent = Intent(context, AlarmActivity::class.java)
            val fullScreenPendingIntent =
                PendingIntent.getActivity(context, 0, fullScreenIntent, PendingIntent.FLAG_MUTABLE)
            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(NOTIFICATION_TITLE)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notificationBuilder.build())
        }
    }

    fun createNotification(context: Context) {
        Log.d(LOG_TAG, "createNotification")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                NOTIFICATION_TITLE,
                NotificationManager.IMPORTANCE_HIGH
            )
            serviceChannel.enableVibration(true)
            serviceChannel.enableLights(true)
            serviceChannel.setShowBadge(true)
            val manager = context.getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
            val contentIntent = Intent(context, MainActivity::class.java)
            val contentPendingIntent =
                PendingIntent.getActivity(context, 0, contentIntent, PendingIntent.FLAG_MUTABLE)
            val fullScreenIntent = Intent(context, AlarmActivity::class.java)
            val fullScreenPendingIntent =
                PendingIntent.getActivity(context, 0, fullScreenIntent, PendingIntent.FLAG_MUTABLE)
            val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(NOTIFICATION_TITLE)
                .setContentIntent(contentPendingIntent)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(1, notificationBuilder.build())
        }
    }
}