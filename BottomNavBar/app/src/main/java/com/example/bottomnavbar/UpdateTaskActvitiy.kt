package com.example.bottomnavbar

import android.app.*
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavbar.Model.AddTaskModel
import com.example.bottomnavbar.databinding.ActivityNewTaskBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class UpdateTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityNewTaskBinding
    private lateinit var database: DatabaseReference
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        showData()

        fAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Task")
        val startCalendar = Calendar.getInstance()
        val endCalendar = Calendar.getInstance()
        val startDatePicker = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                startCalendar.set(Calendar.YEAR, year)
                startCalendar.set(Calendar.MONTH, month)
                startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateStartDate(startCalendar)
            },
            startCalendar.get(Calendar.YEAR),
            startCalendar.get(Calendar.MONTH),
            startCalendar.get(Calendar.DAY_OF_MONTH)
        )
        startDatePicker.datePicker.minDate = System.currentTimeMillis()-1000

        val startTimePicker = TimePickerDialog(
            this,
            { view, hour, minute ->
                startCalendar.set(Calendar.HOUR_OF_DAY, hour)
                startCalendar.set(Calendar.MINUTE, minute)
                updateStartTime(startCalendar)
            },
            startCalendar.get(Calendar.HOUR_OF_DAY),
            startCalendar.get(Calendar.MINUTE),
            false
        )

        val endDatePicker = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                endCalendar.set(Calendar.YEAR, year)
                endCalendar.set(Calendar.MONTH, month)
                endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateEndDate(endCalendar)
            },
            endCalendar.get(Calendar.YEAR),
            endCalendar.get(Calendar.MONTH),
            endCalendar.get(Calendar.DAY_OF_MONTH)
        )
        endDatePicker.datePicker.minDate = System.currentTimeMillis()-1000

        val endTimePicker = TimePickerDialog(
            this,
            { view, hour, minute ->
                endCalendar.set(Calendar.HOUR_OF_DAY, hour)
                endCalendar.set(Calendar.MINUTE, minute)
                updateEndTime(endCalendar)
            },
            endCalendar.get(Calendar.HOUR_OF_DAY),
            endCalendar.get(Calendar.MINUTE),
            false
        )

        this.binding.closeTask.setOnClickListener() {
            val intent = Intent(this, NavDrawerActivity::class.java)
            this.startActivity(intent)
        }

        this.binding.saveTask.setOnClickListener {

            val task = this.binding.taskInput.text.toString()
            val taskIntent = intent
            val taskId = taskIntent.getStringExtra("taskId").toString()
            val description = this.binding.descInput.text.toString()
            val startDate = startCalendar.timeInMillis.toString()
            val endDate = endCalendar.timeInMillis.toString()

            when {
                TextUtils.isEmpty(task) -> Toast.makeText(this, "Please enter a task.", Toast.LENGTH_SHORT).show()
                TextUtils.isEmpty(description) -> Toast.makeText(this, "Please enter a description.", Toast.LENGTH_SHORT).show()
                startDate > endDate -> Toast.makeText(this, "Start time must be earlier than end time!", Toast.LENGTH_SHORT).show()
                else -> {
                    database.child(taskId).child("task").setValue(binding.taskInput.text.toString())
                    database.child(taskId).child("description").setValue(binding.descInput.text.toString())
                    database.child(taskId).child("startDate").setValue(startDate)
                    database.child(taskId).child("startDate").setValue(endDate)

                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()

                    this.startActivity(Intent(this, NavDrawerActivity::class.java))
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
        val myFormat = "MM dd, yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.startDateView.text = sdf.format(taskCalendar.time)
    }

    private fun updateEndDate(taskCalendar: java.util.Calendar) {
        val myFormat = "MM dd, yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.endDateView.text = sdf.format(taskCalendar.time)
    }


    private fun updateStartTime(taskCalendar: java.util.Calendar) {
        val myFormat = "hh:mm a"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.startTimeView.text = sdf.format(taskCalendar.time)
    }

    private fun updateEndTime(taskCalendar: java.util.Calendar) {
        val myFormat = "hh:mm a"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.endTimeView.text = sdf.format(taskCalendar.time)
    }

    fun stringToLong(string: String): Long {
        return try {
            string.toLong()
        } catch (e: NumberFormatException) {
            0
        }
    }

    private fun showData() {
        val startCalendar = Calendar.getInstance()
        val endCalendar = Calendar.getInstance()
        val taskIntent = intent
        val taskName = taskIntent.getStringExtra("task")
        val descName = taskIntent.getStringExtra("description")
        val startDate = taskIntent.getStringExtra("startDate")
        val endDate = taskIntent.getStringExtra("endDate")
        val startDateInMillis = startDate?.let { stringToLong(it) }
        val endDateInMillis = endDate?.let { stringToLong(it) }
        val timeFormat = "hh:mm a"
        val dateFormat = "MM dd, yyyy"
        val timesdf = SimpleDateFormat(timeFormat, Locale.US)
        val datesdf = SimpleDateFormat(dateFormat, Locale.US)

        startCalendar.timeInMillis = startDateInMillis!!
        endCalendar.timeInMillis = endDateInMillis!!

        binding.taskInput.setText(taskName)
        binding.descInput.setText(descName)
        binding.startTimeView.text = timesdf.format(startCalendar.time)
        binding.endTimeView.text = timesdf.format(endCalendar.time)
        binding.startDateView.text = datesdf.format(startCalendar.time)
        binding.endDateView.text = datesdf.format(endCalendar.time)
    }


}
