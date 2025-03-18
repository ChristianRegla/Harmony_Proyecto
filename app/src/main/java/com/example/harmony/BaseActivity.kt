package com.example.harmony

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

open class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    protected fun setupHeader(title: String) {
        val customHeader = findViewById<android.view.View?>(R.id.custom_header)
        Log.d("BaseActivity", "custom_header: $customHeader")

        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        Log.d("BaseActivity", "Toolbar: $toolbar")

        if (toolbar != null) {
            setSupportActionBar(toolbar)

            drawerLayout = findViewById(R.id.drawer_layout)
            val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            val navigationView: NavigationView = findViewById(R.id.nav_view)
            navigationView.setNavigationItemSelectedListener(this)

            val headerTitle: TextView = findViewById(R.id.header_title)
            headerTitle.text = title
            Log.d("BaseActivity", "headerTitle: $headerTitle")

            val menuIcon: ImageView = findViewById(R.id.menu_icon)
            menuIcon.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            Log.d("BaseActivity", "menuIcon: $menuIcon")

            val rightButton: ImageView = findViewById(R.id.right_button)
            rightButton.setOnClickListener {
                // val intent = Intent(this, ChatbotActivity::class.java)
                // startActivity(intent)
            }
            Log.d("BaseActivity", "rightButton: $rightButton")

        } else {
            Log.e("BaseActivity", "Toolbar is null!")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}