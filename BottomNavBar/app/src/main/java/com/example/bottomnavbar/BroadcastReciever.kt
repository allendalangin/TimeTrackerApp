package com.example.bottomnavbar

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat

class BroadcastReciever : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = intent.getIntExtra("notificationId", 0)
        val taskTitle = intent.getStringExtra("taskTitle")

        val notification = NotificationCompat.Builder(context, "1")
            .setSmallIcon(R.drawable.project_interface)
            .setContentTitle(taskTitle)
            .setContentText("It's time to start your task.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}