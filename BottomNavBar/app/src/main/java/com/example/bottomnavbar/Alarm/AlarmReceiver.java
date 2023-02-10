package com.example.bottomnavbar.Alarm;

import static com.example.bottomnavbar.Alarm.NotificationCreator.createLockScreenNotification;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.legacy.content.WakefulBroadcastReceiver;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    private static Ringtone mRingtone  = null;

    @Override
    public void onReceive(final Context context, Intent intent) {

        createLockScreenNotification(context);
        //This will start playing the Alarm Tone
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mRingtone = RingtoneManager.getRingtone(context, uri);
        mRingtone.play();

        //This will send a notification message
        ComponentName comp = new ComponentName(context.getPackageName(),
                AlarmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }

    //To stop the currently playing Alarm Tone
    public static void stopRingtone() {
        mRingtone.stop();
    }
}
