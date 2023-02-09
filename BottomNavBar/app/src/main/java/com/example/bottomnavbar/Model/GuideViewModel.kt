package com.example.bottomnavbar.Model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bottomnavbar.Repository.GuideRepository

class GuideViewModel : ViewModel() {

    private val repository : GuideRepository
    private val _allGuide = MutableLiveData<List<GuideModel>>()
    val allGuide : LiveData<List<GuideModel>> = _allGuide

    init {

        repository = GuideRepository().getInstance()
        repository.loadGuide(_allGuide)

    }

}