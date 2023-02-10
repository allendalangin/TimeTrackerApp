package com.example.bottomnavbar

import android.app.*
import android.app.Notification
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavbar.Alarm.AlarmReceiver
import com.example.bottomnavbar.Alarm.MainActivity
import com.example.bottomnavbar.Model.AddTaskModel
import com.example.bottomnavbar.databinding.ActivityNewTaskBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class AddTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNewTaskBinding
    private lateinit var database: DatabaseReference
    private lateinit var fAuth: FirebaseAuth
    val dateFormat = "MM dd, yyyy"
    val timeFormat = "hh:mm a"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        createNotificationChannel()
        val startCalendar = java.util.Calendar.getInstance()
        val endCalendar = java.util.Calendar.getInstance()

        val startDatePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                startCalendar.set(java.util.Calendar.YEAR, year)
                startCalendar.set(java.util.Calendar.MONTH, month)
                startCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth)
                updateStartDate(startCalendar)
            },
            startCalendar.get(java.util.Calendar.YEAR),
            startCalendar.get(java.util.Calendar.MONTH),
            startCalendar.get(java.util.Calendar.DAY_OF_MONTH)
        )
        startDatePicker.datePicker.minDate = System.currentTimeMillis()-1000

        val startTimePicker = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                startCalendar.set(java.util.Calendar.HOUR_OF_DAY, hour)
                startCalendar.set(java.util.Calendar.MINUTE, minute)
                updateStartTime(startCalendar)
            },
            startCalendar.get(java.util.Calendar.HOUR_OF_DAY),
            startCalendar.get(java.util.Calendar.MINUTE),
            false
        )

        val endDatePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                endCalendar.set(java.util.Calendar.YEAR, year)
                endCalendar.set(java.util.Calendar.MONTH, month)
                endCalendar.set(java.util.Calendar.DAY_OF_MONTH, dayOfMonth)
                updateEndDate(endCalendar)
            },
            endCalendar.get(java.util.Calendar.YEAR),
            endCalendar.get(java.util.Calendar.MONTH),
            endCalendar.get(java.util.Calendar.DAY_OF_MONTH)
        )
        endDatePicker.datePicker.minDate = System.currentTimeMillis()-1000

        val endTimePicker = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                endCalendar.set(java.util.Calendar.HOUR_OF_DAY, hour)
                endCalendar.set(java.util.Calendar.MINUTE, minute)
                updateEndTime(endCalendar)
            },
            endCalendar.get(java.util.Calendar.HOUR_OF_DAY),
            endCalendar.get(java.util.Calendar.MINUTE),
            false
        )

        this.binding.closeTask.setOnClickListener() {
            val intent = Intent(this, NavDrawerActivity::class.java)
            this.startActivity(intent)
        }

        this.binding.saveTask.setOnClickListener {
            fAuth = FirebaseAuth.getInstance()
            database = FirebaseDatabase.getInstance().getReference("Task").push()
            val task = this.binding.taskInput.text.toString()
            val description = this.binding.descInput.text.toString()
            val startDateOnly = this.binding.startDateView.text.toString()
            val startTime = this.binding.startTimeView.text.toString()
            val startDate = startCalendar.timeInMillis.toString()
            val endDateOnly = this.binding.endDateView.text.toString()
            val endTime = this.binding.endTimeView.text.toString()
            val endDate = endCalendar.timeInMillis.toString()
            val taskOwner = this.fAuth.currentUser?.uid.toString()
            val creationDate = startCalendar.time.toString()
            val taskId = database.key.toString()
            val Task = AddTaskModel(task, taskOwner, description, creationDate, startDate, endDate, taskId)

            when {
                TextUtils.isEmpty(task) -> Toast.makeText(this, "Please enter a task.", Toast.LENGTH_SHORT).show()
                TextUtils.isEmpty(description) -> Toast.makeText(this, "Please enter a description.", Toast.LENGTH_SHORT).show()
                startDateOnly == "Start Date" -> Toast.makeText(this, "Please enter a start date.", Toast.LENGTH_SHORT).show()
                startTime == "Start Time" -> Toast.makeText(this, "Please enter a start time.", Toast.LENGTH_SHORT).show()
                endDateOnly == "End Date" -> Toast.makeText(this, "Please enter an end date.", Toast.LENGTH_SHORT).show()
                endTime == "End Time" -> Toast.makeText(this, "Please enter a end time.", Toast.LENGTH_SHORT).show()
                startDate > endDate -> Toast.makeText(this, "Start time must be earlier than end time!", Toast.LENGTH_SHORT).show()
                else ->
                    database.setValue(Task).addOnSuccessListener {

                        val intent = Intent(applicationContext, Notification::class.java)
                        intent.putExtra("titleExtra", task)
                        intent.putExtra("messageExtra", description)

                        val pendingIntent = PendingIntent.getBroadcast(
                            applicationContext,
                            notificationID,
                            intent,
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                        )

                        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            startCalendar.timeInMillis,
                            pendingIntent
                        )

                    this.binding.taskInput.text.clear()
                    this.binding.descInput.text.clear()
                    Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show()

                    this.startActivity(Intent(this, NavDrawerActivity::class.java))

                }.addOnFailureListener {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        this.binding.startDateView.setOnClickListener() {
            startDatePicker.show()
        }

        this.binding.startTimeView.setOnClickListener() {
            startTimePicker.show()
        }

        this.binding.endDateView.setOnClickListener() {
            endDatePicker.show()
        }

        this.binding.endTimeView.setOnClickListener() {
                endTimePicker.show()
        }
    }

    private fun updateStartDate(taskCalendar: java.util.Calendar) {
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        binding.startDateView.text = sdf.format(taskCalendar.time)
    }

    private fun updateEndDate(taskCalendar: java.util.Calendar) {
        val sdf = SimpleDateFormat(dateFormat, Locale.US)
        binding.endDateView.text = sdf.format(taskCalendar.time)
    }

    private fun updateStartTime(taskCalendar: java.util.Calendar) {
        val sdf = SimpleDateFormat(timeFormat, Locale.US)
        binding.startTimeView.text = sdf.format(taskCalendar.time)
    }

    private fun updateEndTime(taskCalendar: java.util.Calendar) {
        val sdf = SimpleDateFormat(timeFormat, Locale.US)
        binding.endTimeView.text = sdf.format(taskCalendar.time)
    }

    private fun scheduleNotification()
    {
    }

    private fun showAlert(time: Long, title: String, message: String)
    {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                        "\nMessage: " + message +
                        "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_ ->}
            .show()
    }

    private fun createNotificationChannel()
    {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

}


