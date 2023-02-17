package com.example.bottomnavbar.Alarm

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bottomnavbar.NavDrawerActivity
import com.example.bottomnavbar.databinding.ActivityAlarmBinding
import com.example.bottomnavbar.databinding.ActivityAlarmInterfaceBinding
import com.example.bottomnavbar.databinding.ActivityNewTaskBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.random.Random

class AlarmInterface: AppCompatActivity() {

    private lateinit var binding: ActivityAlarmInterfaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityAlarmInterfaceBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
        val databaseReference = FirebaseDatabase.getInstance().getReference("Tips")
        val max = 12 // maximum value
        val random = Random
        val number = random.nextInt(max + 1)
        val query = databaseReference.orderByChild("id").equalTo(number.toString())

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val title = dataSnapshot.child("title").getValue(String::class.java)
                val content = dataSnapshot.child("content").getValue(String::class.java)
                binding.title.setText(title)
                binding.content.setText(content)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, "onCancelled: " + databaseError.message)
            }
        })

        binding.toHomepage.setOnClickListener() {
            this.startActivity(Intent(this, NavDrawerActivity::class.java))
        }


    }
}
