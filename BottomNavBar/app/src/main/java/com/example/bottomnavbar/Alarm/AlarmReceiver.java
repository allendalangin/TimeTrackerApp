package com.example.bottomnavbar.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver  extends BroadcastReceiver {
    private static final String LOG_TAG = "AlarmReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG,"onReceive");
     String title = intent.getStringExtra("title");
     int id = intent.getIntExtra("id",0);

        //NotificationCreator.createNotification(context,title);
        NotificationCreator.createLockScreenNotification(context,title,id);
    }
}
