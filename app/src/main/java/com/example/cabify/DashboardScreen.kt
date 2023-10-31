package com.example.cabify

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class DashboardScreen : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_screen)

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Dashboard")
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.titleMarginStart = 250
        toolbar.setNavigationIcon(R.drawable.ic_density_medium_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setSupportActionBar(toolbar)

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.myProfile -> {
                    toolbar.setTitle("MyProfile")
                    replaceFrameWithFragment(ProfileFragment())
                }
                R.id.rate -> {
                    toolbar.setTitle("Rate Us")
                    replaceFrameWithFragment(RateFragment())
                }
//                R.id.wallet -> Toast.makeText(this, "My Wallet Clicked", Toast.LENGTH_SHORT).show()
//                R.id.activity -> Toast.makeText(this, "Activity Clicked", Toast.LENGTH_SHORT).show()
                R.id.share -> shareApp()
//                R.id.settings -> Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show()
                R.id.about -> {
                    toolbar.setTitle("About Us")
                    replaceFrameWithFragment(AboutFragment())
                }
                R.id.logout -> {
                    exitAlert()
                }

            }
            val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        replaceFrameWithFragment(HomeFragment())

        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.home -> {
                    replaceFrameWithFragment(HomeFragment())
                    toolbar.setTitle("Dashboard")
                    toolbar.titleMarginStart = 250
                }
                R.id.rate -> {
                    toolbar.setTitle("Rate Us")
                    replaceFrameWithFragment(RateFragment())
                    toolbar.titleMarginStart = 280
                }
                R.id.profile -> {
                    replaceFrameWithFragment(ProfileFragment())
                    toolbar.setTitle("My Profile")
                    toolbar.titleMarginStart = 250
                }
                R.id.logout -> exitAlert()
            }
            true
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            shareApp()
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    private fun replaceFrameWithFragment(fragment: Fragment) {
        val fragManager = supportFragmentManager
        val fragTransaction = fragManager.beginTransaction()
        fragTransaction.replace(R.id.frameLayout, fragment)
        fragTransaction.commit()
    }

     fun exitAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to logout ?")
        builder.setTitle("Logout Alert!")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes") {
                dialog, which -> logoutRedirect()
        }
        builder.setNegativeButton("No") {
                dialog, which -> dialog.cancel()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun logoutRedirect() {
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
    }

    fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT,"https://drive.google.com/file/d/1eoH8RwKV_TtW6mXETQFhNrBHguD4gqGa/view?usp=sharing")
        startActivity(Intent.createChooser(intent, "Share Link!"))
    }
}