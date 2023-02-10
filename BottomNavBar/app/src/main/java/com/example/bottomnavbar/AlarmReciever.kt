package com.example.bottomnavbar

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

public class AlarmReciever(
    addTaskActivity: AddTaskActivity,
    date: Calendar,
    taskTitle: String,
    notificationId: Int
) {

    fun setNotification(context: Context, date: Calendar, taskTitle: String, notificationId: Int) {
        val intent = Intent(context, BroadcastReciever::class.java).apply {
            putExtra("notificationId", notificationId)
            putExtra("taskTitle", taskTitle)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, date.timeInMillis, pendingIntent)
    }
}