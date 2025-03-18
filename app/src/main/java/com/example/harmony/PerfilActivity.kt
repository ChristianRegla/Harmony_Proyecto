package com.example.harmony

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.datastore.preferences.core.edit
import com.example.harmony.databinding.ActivityPerfilBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.stringPreferencesKey
import dataStore

class PerfilActivity : BaseActivity() {
    private lateinit var binding: ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupHeader("Perfil")

        // Recordatorio que aquí se obtienen los text views del apodo y el correo
        val nicknameTextView = binding.usuario
        val emailTextView = binding.correoUsuario

        // Aquí obtenemos el usuario actual de Firebase
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val db = FirebaseFirestore.getInstance()

        if (currentUser != null) {
            val userId = currentUser.uid
            val email = currentUser.email

            emailTextView.text = email

            // Aquí comenzamos a verificar si el apodo y el correo están guardados en el caché del dispositivo
            // Si no lo están, los obtenemos de Firestore y los guardamos en el caché
            // Si ya están guardados, los mostramos en la interfaz de usuario
            // Nos ayuda a ahorrarnos consultas cada vez que el usuario accede a la ventana de perfil :)
            runBlocking {
                val preferences = dataStore.data.first()
                val cachedNickname = preferences[stringPreferencesKey("nickname")]
                val cachedEmail = preferences[stringPreferencesKey("email")]

                // Si los datos están en el caché, los mostramos en la interfaz de usuario
                if (cachedNickname != null && cachedEmail != null) {
                    nicknameTextView.text = cachedNickname
                    emailTextView.text = cachedEmail
                } else {
                    // Si los datos no están en el caché, los obtenemos de Firestore y los guardamos en el caché
                    db.collection("usuarios").document(userId).get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                val nickname = document.getString("apodo")
                                nicknameTextView.text = nickname

                                val nicknameString = nickname?.toString() ?: "Apodo desconocido"
                                val emailString = email?.toString() ?: "Correo desconocido"

                                // Guardamos los datos en el caché del dispositivo
                                runBlocking {
                                    dataStore.edit { preferences ->
                                        preferences[stringPreferencesKey("nickname")] = nicknameString
                                        preferences[stringPreferencesKey("email")] = emailString
                                    }
                                }
                            } else {
                                // Si el apodo no existe, mostramos un mensaje
                                nicknameTextView.text = "Apodo no encontrado"
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Si ocurre un error al obtener el apodo, mostramos un mensaje
                            Log.e("PerfilActivity", "Error al obtener el apodo", exception)
                            nicknameTextView.text = "Error al obtener el apodo"
                        }
                }
            }
        } else {
            // Esto no debería nunca pasar pq pues tienes que estar logueado para acceder a la app
            // Peeeero por si acaso jeje
            nicknameTextView.text = "Usuario no autenticado"
        }

        // Configuración de la barra de navegación inferior
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item1 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.menu_item2 -> {
                    val intent = Intent(this, RelajacionActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }
                else -> false
            }
        }
    }
}