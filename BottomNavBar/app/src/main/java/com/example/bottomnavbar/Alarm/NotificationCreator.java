package com.example.bottomnavbar.Alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.bottomnavbar.Fragments.Calendar;
import com.example.bottomnavbar.NavDrawerActivity;
import com.example.bottomnavbar.R;


public class NotificationCreator {
    private static final String LOG_TAG = "NotificationCreator";
    public static final String CHANNEL_ID = "TestAlarmManager";

    public static void createLockScreenNotification(Context context,String title, int req){
        Log.d(LOG_TAG,"createNotification");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    title ,
                    NotificationManager.IMPORTANCE_HIGH
            );
            serviceChannel.enableVibration(true);
            serviceChannel.enableLights(true);
            serviceChannel.setShowBadge(true);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

            Intent contentIntent = new Intent(context, AlarmActivity.class);
            PendingIntent contentPendingIntent = PendingIntent.getActivity(context, req, contentIntent,PendingIntent.FLAG_MUTABLE);

            Intent fullScreenIntent = new Intent(context, AlarmActivity.class);
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, req, fullScreenIntent,PendingIntent.FLAG_MUTABLE);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);

            notificationBuilder.setAutoCancel(true)
                    .setSmallIcon(R.drawable.project_interface)
                    .setContentTitle(title)
                    .setFullScreenIntent(fullScreenPendingIntent,true)
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notificationBuilder.build());
        }

    }

    public static void createNotification(Context context, String title){
        Log.d(LOG_TAG,"createNotification");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    title ,
                    NotificationManager.IMPORTANCE_HIGH
            );
            serviceChannel.enableVibration(true);
            serviceChannel.enableLights(true);
            serviceChannel.setShowBadge(true);

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);

            Intent contentIntent = new Intent(context, NavDrawerActivity.class);
            PendingIntent contentPendingIntent = PendingIntent.getActivity(context, 0, contentIntent,PendingIntent.FLAG_MUTABLE);

            Intent fullScreenIntent = new Intent(context, AlarmActivity.class);
            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0, fullScreenIntent,PendingIntent.FLAG_MUTABLE);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);

            notificationBuilder.setAutoCancel(true)
                    .setSmallIcon(R.drawable.project_interface)
                    .setContentTitle(title)
                    .setContentIntent(contentPendingIntent)
                    .setContentIntent(contentPendingIntent)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notificationBuilder.build());
        }

    }
}
