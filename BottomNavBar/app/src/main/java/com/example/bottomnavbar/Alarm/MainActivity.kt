package com.example.bottomnavbar.Alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.bottomnavbar.R
import java.util.*

class MainActivity : FragmentActivity() {
    var alarmTimeTextView: TextView? = null
    private var setAlarmButton: Button? = null
    private var cancelAlarmButton: Button? = null
    var alarmManager: AlarmManager? = null
    private var pendingIntent: PendingIntent? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mylayout)
        alarmTimeTextView = findViewById<TextView>(R.id.msg1)
        alarmTimeTextView?.text = "$timeHour:$timeMinute"
        alarmAlertTextView = findViewById<TextView>(R.id.alarmAlertTextView)
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager?
        val myIntent = Intent(this@MainActivity, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this@MainActivity, 0, myIntent, FLAG_IMMUTABLE)
        
        //Listener for CancelAlarmButton
        val cancelAlarmButtonListener = View.OnClickListener {
            alarmAlertTextView?.text = ""
            cancelAlarm()
        }
        cancelAlarmButton = findViewById<Button>(R.id.cancelAlarmButton)
        cancelAlarmButton!!.setOnClickListener(cancelAlarmButtonListener)
    }
    
    //Sets the Alarm, sets the TextView of AlarmTime
    private fun setAlarm() {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = timeHour
        calendar[Calendar.MINUTE] =
            timeMinute
        alarmManager?.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    // Cancel the Alarm and stop the Ringtone
    private fun cancelAlarm() {
        if (alarmManager != null) {
            AlarmReceiver.stopRingtone()
            alarmManager!!.cancel(pendingIntent)
        }
    }

    companion object {
        private var timeHour = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
        private var timeMinute = Calendar.getInstance()[Calendar.MINUTE]
        private var alarmAlertTextView: TextView? = null

        //To get the instance of Alarm Time TextView
        fun getAlarmAlertTextView(): TextView? {
            return alarmAlertTextView
        }
    }
}