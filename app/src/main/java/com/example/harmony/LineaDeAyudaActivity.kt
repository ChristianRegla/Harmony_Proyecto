package com.example.harmony

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harmony.databinding.ActivityLineaDeAyudaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.net.toUri

class LineaDeAyudaActivity : BaseActivity() {
    private lateinit var binding: ActivityLineaDeAyudaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linea_de_ayuda)
        binding = ActivityLineaDeAyudaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupHeader("Linea de Ayuda")

        binding.telefonoLineaDeAyuda.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:8009112000".toUri()
            startActivity(intent)
        }

        binding.telefonoLineaDeAyudaJalisco.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:075".toUri()
            startActivity(intent)
        }

        binding.telefonoEmergencias.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = "tel:911".toUri()
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.menu_item2 -> {
                    intent = Intent(this, RelajacionActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}