package com.example.bottomnavbar.Repository

import androidx.lifecycle.MutableLiveData
import com.example.bottomnavbar.Model.AddTaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TaskRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Task")
    private val fAuth : FirebaseAuth = FirebaseAuth.getInstance()

    @Volatile private var INSTANCE : TaskRepository?= null

    fun getInstance() : TaskRepository {
        return INSTANCE ?: synchronized(this){

            val instance = TaskRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(taskList : MutableLiveData<List<AddTaskModel>>){
        val uid = fAuth.currentUser?.uid.toString()
        val query = databaseReference.orderByChild("taskOwner").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _taskList : List<AddTaskModel> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(AddTaskModel::class.java)!!

                    }

                    taskList.postValue(_taskList)

                }catch (e : Exception){


                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    }

    fun getFilteredData(userId: String, callback: (List<AddTaskModel>) -> Unit) {
        val query = databaseReference.child("taskId").orderByChild("uid").equalTo(userId)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataList = mutableListOf<AddTaskModel>()
                dataSnapshot.children.forEach { snapshot ->
                    val data = snapshot.getValue(AddTaskModel::class.java)
                    dataList.add(data!!)
                }
                callback(dataList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }

}