package com.example.bottomnavbar.Alarm;

import static android.content.Context.ALARM_SERVICE;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class AlarmManagerCreator {
    private static final String LOG_TAG = "AlarmManagerCreator";
    public static final int ALARM_CODE = 1234;

    public static void createAlarmManager(Context context , Calendar cal, String title, int id){
        Log.d(LOG_TAG,"createAlarmManager");
        Intent intent ;
        PendingIntent pending;

        intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("id",id);
        pending=PendingIntent.getBroadcast(
                context,
                id,
                intent,
                PendingIntent.FLAG_MUTABLE);


        AlarmManager alarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(),
                    pending);
        }
    }

    public static void createEndAlarmManager(Context context , Calendar cal, String title, int id){
        Log.d(LOG_TAG,"createAlarmManager");
        Intent intent ;
        PendingIntent pending;

        intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title",title);
        intent.putExtra("id",id);
        pending=PendingIntent.getBroadcast(
                context,
                ALARM_CODE,
                intent,
                PendingIntent.FLAG_MUTABLE);


        AlarmManager alarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(),
                    pending);
        }
    }

    public static void cancelAlarmManager(Context context , Calendar cal, String title, int id){
        AlarmManager alarmManager=(AlarmManager)context.getSystemService(ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title",title);
        PendingIntent pending =PendingIntent.getBroadcast(
                context,
                ALARM_CODE,
                intent,
                PendingIntent.FLAG_MUTABLE);

        alarmManager.cancel(pending);
    }
}
