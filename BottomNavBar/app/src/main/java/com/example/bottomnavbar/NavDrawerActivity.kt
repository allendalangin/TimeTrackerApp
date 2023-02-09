package com.example.bottomnavbar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.bottomnavbar.Authentication.Login
import com.example.bottomnavbar.Fragments.*
import com.example.bottomnavbar.databinding.ActivityNavdrawerBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class NavDrawerActivity : AppCompatActivity() {

    private lateinit var toggle : ActionBarDrawerToggle
    lateinit var drawer: DrawerLayout
    private lateinit var binding: ActivityNavdrawerBinding
    lateinit var toolbar : androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavdrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawer = findViewById(R.id.drawer)
        toolbar = findViewById(R.id.toolbar)
        val navigationView : NavigationView = findViewById(R.id.navigationView)
        toggle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(Tasks())

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {

            it.isChecked = true

            when(it.itemId){

                R.id.tasks -> replaceFragment(Tasks())
                R.id.calendar -> this.startActivity(Intent(this, CalendarActivity::class.java))
                R.id.profile -> replaceFragment(Profile())
                R.id.themes -> replaceFragment(Themes())
                R.id.guide -> replaceFragment(Guide())
                R.id.logout -> signOut()
            }

            true


        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)){

            return true


        }
        return super.onOptionsItemSelected(item)
    }

    private fun replaceFragment (fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navContainer, fragment)
        fragmentTransaction.commit()
        drawer.closeDrawers()
    }

    private fun signOut () {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, Login::class.java))
    }
}