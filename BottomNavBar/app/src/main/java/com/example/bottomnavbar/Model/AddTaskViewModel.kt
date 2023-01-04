package com.example.bottomnavbar.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavbar.Repository.TaskRepository

class AddTaskViewModel : ViewModel() {

    private val repository : TaskRepository
    private val _allUsers = MutableLiveData<List<AddTaskModel>>()
    val allUsers : LiveData<List<AddTaskModel>> = _allUsers


    init {

        repository = TaskRepository().getInstance()
        repository.loadUsers(_allUsers)

    }

}