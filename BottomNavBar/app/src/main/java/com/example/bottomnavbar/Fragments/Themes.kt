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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.DialogFragment
import com.example.bottomnavbar.Authentication.Login
import com.example.bottomnavbar.NavDrawerActivity
import com.example.bottomnavbar.R
import com.example.bottomnavbar.databinding.FragmentCustomDialogBinding
import com.example.bottomnavbar.databinding.FragmentThemesBinding
import com.example.bottomnavbar.databinding.PersonalizationBinding
import com.google.firebase.database.FirebaseDatabase


class Themes : AppCompatActivity() {

    private lateinit var binding: FragmentThemesBinding
    private var currentTheme = "AppTheme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = FragmentThemesBinding.inflate(layoutInflater)
        setContentView(this.binding.root)


       binding.saveTask.setOnClickListener() {
           when (binding.ratingRadioGroup.checkedRadioButtonId) {
               R.id.Blue -> {
                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
               }
               R.id.Pink -> {
                       AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               }
           }
           val intent = Intent(this, NavDrawerActivity::class.java)
           startActivity(intent)
       }

        binding.closeTask.setOnClickListener() {
            this.startActivity(Intent(this, NavDrawerActivity::class.java))
        }
       }
    }



