package com.example.bottomnavbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.bottomnavbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Tasks())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId) {

                R.id.menu -> replaceFragment(Menu())
                R.id.tasks -> replaceFragment(Tasks())
                R.id.calendar -> replaceFragment(Calendar())
                R.id.profile -> replaceFragment(Profile())

                else -> {}

            }

            true
        }

    }

    private fun replaceFragment (fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()

    }

}