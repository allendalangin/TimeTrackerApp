package com.example.bottomnavbar.Fragments

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.bottomnavbar.Authentication.Login
import com.example.bottomnavbar.NavDrawerActivity
import com.example.bottomnavbar.R
import com.example.bottomnavbar.databinding.FragmentCustomDialogBinding
import com.google.firebase.database.FirebaseDatabase


class Themes : DialogFragment() {

    private lateinit var binding: FragmentCustomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       binding.submitButton.setOnClickListener() {
           dialog?.dismiss()
       }

        binding.cancelButton.setOnClickListener() {
            val intent = Intent(activity, NavDrawerActivity::class.java)
            startActivity(intent)

        }

    }
}



