package com.example.harmony

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harmony.databinding.ActivityMainBinding
import com.example.harmony.databinding.ActivityRelajacionBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class RelajacionActivity : BaseActivity() {
    private lateinit var binding: ActivityRelajacionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelajacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupHeader("Relajación")

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.menu_item2 -> {
                    true
                }

                else -> false
            }
        }
    }
}
