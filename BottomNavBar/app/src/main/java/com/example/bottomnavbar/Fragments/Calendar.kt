package com.example.bottomnavbar.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bottomnavbar.CalendarActivity
import com.example.bottomnavbar.R
import com.example.bottomnavbar.databinding.FragmentCalendarBinding

class Calendar : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val intent = Intent(activity, CalendarActivity::class.java)
        startActivity(intent)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}