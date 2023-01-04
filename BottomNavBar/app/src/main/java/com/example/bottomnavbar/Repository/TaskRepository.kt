package com.example.bottomnavbar.Repository

import androidx.lifecycle.MutableLiveData
import com.example.bottomnavbar.Model.AddTaskModel
import com.google.firebase.database.*

class TaskRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Task")

    @Volatile private var INSTANCE : TaskRepository?= null

    fun getInstance() : TaskRepository {
        return INSTANCE ?: synchronized(this){

            val instance = TaskRepository()
            INSTANCE = instance
            instance
        }


    }


    fun loadUsers(taskList : MutableLiveData<List<AddTaskModel>>){

        databaseReference.addValueEventListener(object : ValueEventListener{
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

}