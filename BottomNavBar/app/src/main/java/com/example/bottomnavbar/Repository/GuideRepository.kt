package com.example.bottomnavbar.Repository

import androidx.lifecycle.MutableLiveData
import com.example.bottomnavbar.Model.GuideModel
import com.google.firebase.database.*

class GuideRepository {

    private val databaseReference : DatabaseReference = FirebaseDatabase.getInstance().getReference("Guide")

    @Volatile private var INSTANCE : GuideRepository ?= null

    fun getInstance() : GuideRepository{

        return INSTANCE ?: synchronized(this){

            val instance = GuideRepository()
            INSTANCE = instance
            instance

        }

    }

    fun loadGuide(guideList : MutableLiveData<List<GuideModel>>){

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    val _guideList : List<GuideModel> = snapshot.children.map { dataSnapshot ->

                        dataSnapshot.getValue(GuideModel::class.java)!!

                    }

                    guideList.postValue(_guideList)

                }catch (e : Exception){


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }


}