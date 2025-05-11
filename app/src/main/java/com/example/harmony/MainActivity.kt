package com.example.harmony

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harmony.databinding.ActivityMainBinding
import com.example.harmony.ui.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tituloHeader = getString(R.string.titulo_header_inicio)
        setupHeader(tituloHeader)

        // Manejo del container del chatbot
        binding.containerChatbotBackground.setOnClickListener {
            // val intent = Intent(this, ChatbotActivity::class.java)
            // startActivity(intent)
            CustomToast.showWelcomeWithIcon(this, "Bienvenido a Harmony", R.drawable.logo_harmony, Toast.LENGTH_SHORT)
        }

        // Manejo del container de la linea de ayuda
        binding.containerLineaDeAyudaBackground.setOnClickListener {
            val intent = Intent(this, LineaDeAyudaActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Manejo del container temporal de la configuracion de la cuenta
        binding.editarPerfilButton.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            Firebase.auth.signOut()
            // Cerrar sesión y redirigir a la pantalla de inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual para evitar que el usuario regrese a esta actividad al presionar el botón de retroceso
        }

        // Configuracion de la barra de navegacion inferior
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    true
                }
                R.id.menu_item2 -> {
                    val intent = Intent(this, RelajacionActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }
}