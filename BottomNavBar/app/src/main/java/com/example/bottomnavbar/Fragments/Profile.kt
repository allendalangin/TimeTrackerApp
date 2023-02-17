package com.example.bottomnavbar.Fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.bottomnavbar.AddTaskActivity
import com.example.bottomnavbar.EditProfileActvity
import com.example.bottomnavbar.R
import com.example.bottomnavbar.databinding.FragmentProfileBinding
import com.example.bottomnavbar.databinding.FragmentTasksBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding
    private lateinit var database: FirebaseDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        database = FirebaseDatabase.getInstance()
        val fAuth = FirebaseAuth.getInstance().currentUser
        val userId = fAuth?.uid
        val ref = database.getReference("User")
        val storage = FirebaseStorage.getInstance().reference


        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name = dataSnapshot.child(userId!!).child("name").getValue(String::class.java)
                val uid = dataSnapshot.child(userId!!).child("uid").getValue(String::class.java)
                val imageUrl = dataSnapshot.child(userId!!).child("profileImg").getValue(String::class.java)
                if (imageUrl != null) {
                    // Load the image into the ImageView using Glide
                    Glide.with(requireContext())
                        .load(imageUrl)
                        .into(binding.profilePic)
                }
                binding.username.text = name
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.editBtn.setOnClickListener() {
            val intent = Intent (activity, EditProfileActvity::class.java)
            activity?.startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}