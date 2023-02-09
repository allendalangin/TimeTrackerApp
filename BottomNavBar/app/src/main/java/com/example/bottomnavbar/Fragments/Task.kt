package com.example.bottomnavbar.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavbar.Adapter.TaskAdapter
import com.example.bottomnavbar.AddTaskActivity
import com.example.bottomnavbar.Authentication.Login
import com.example.bottomnavbar.Model.AddTaskModel
import com.example.bottomnavbar.Model.AddTaskViewModel
import com.example.bottomnavbar.Model.UserModel
import com.example.bottomnavbar.R
import com.example.bottomnavbar.UpdateTaskActivity
import com.example.bottomnavbar.databinding.FragmentTasksBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.google.firebase.auth.FirebaseUser as FirebaseUser1


class Tasks : Fragment(){
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentTasksBinding
    private var taskList = ArrayList<AddTaskModel>()
    private lateinit var taskViewModel : AddTaskViewModel
    private lateinit var taskRecyclerView: RecyclerView
    lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        taskRecyclerView = view.findViewById(R.id.taskView)
        taskRecyclerView.layoutManager = LinearLayoutManager(context)
        taskRecyclerView.setHasFixedSize(true)
        taskAdapter = TaskAdapter(requireContext(),taskList)
        taskRecyclerView.adapter = taskAdapter


        taskViewModel = ViewModelProvider(this)[AddTaskViewModel::class.java]

        taskViewModel.allUsers.observe(viewLifecycleOwner, Observer {

            taskAdapter.updateTaskList(it as ArrayList<AddTaskModel>)
        })
        val database = FirebaseDatabase.getInstance()
        val fAuth = FirebaseAuth.getInstance().currentUser
        val userId = fAuth?.uid
        val ref = database.getReference("User")

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val name = dataSnapshot.child(userId!!).child("name").getValue(String::class.java)
                    val uid = dataSnapshot.child(userId!!).child("uid").getValue(String::class.java)
                    val nameSplitOne = name?.split(" ")?.get(0)
                    val nameSplitTwo = name?.split(" ")?.get(1)
                    val nameOutput = "$nameSplitOne $nameSplitTwo!"

                    binding.username.text = nameOutput
                    }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })



        binding.all.setOnClickListener() {
            val toast = Toast.makeText(activity, "All", Toast.LENGTH_SHORT)
            toast.show()

        }

        binding.completed.setOnClickListener() {
            val toast = Toast.makeText(activity, "Done", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.ongoing.setOnClickListener() {
            val toast = Toast.makeText(activity, "Ongoing", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.addTask.setOnClickListener() {
            val intent = Intent (activity, AddTaskActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}