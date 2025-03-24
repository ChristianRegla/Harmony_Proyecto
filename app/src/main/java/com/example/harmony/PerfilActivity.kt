package com.example.harmony

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.datastore.preferences.core.edit
import com.example.harmony.databinding.ActivityPerfilBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.stringPreferencesKey
import dataStore
import java.util.Locale

class PerfilActivity : BaseActivity() {
    private lateinit var binding: ActivityPerfilBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var idiomaSeleccionadoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupHeader("Perfil")

        sharedPreferences = getSharedPreferences("MisPreferencias", MODE_PRIVATE)
        idiomaSeleccionadoTextView = binding.idiomaSeleccionado

        // Mostramos el idioma actual que está seleccionado
        val idiomaActual = sharedPreferences.getString("idioma", "es")
        actualizarIdiomaSeleccionado(idiomaActual.toString())

        // Manejamos el clic en la opción de Idioma
        val idiomaLayout = binding.idiomaLayout
        idiomaLayout.setOnClickListener {
            mostrarDialogoIdioma()
        }



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

                                val apodo_desconocido = getString(R.string.apodo_desconocido)
                                val correo_desconocido = getString(R.string.correo_desconocido)
                                val nicknameString = nickname?.toString() ?: apodo_desconocido
                                val emailString = email?.toString() ?: correo_desconocido

                                // Guardamos los datos en el caché del dispositivo
                                runBlocking {
                                    dataStore.edit { preferences ->
                                        preferences[stringPreferencesKey("nickname")] = nicknameString
                                        preferences[stringPreferencesKey("email")] = emailString
                                    }
                                }
                            } else {
                                // Si el apodo no existe, mostramos un mensaje
                                val apodo_no_encontrado = getString(R.string.apodo_no_encontrado)
                                nicknameTextView.text = apodo_no_encontrado
                            }
                        }
                        .addOnFailureListener { exception ->
                            // Si ocurre un error al obtener el apodo, mostramos un mensaje
                            val error_apodo = getString(R.string.error_apodo)
                            nicknameTextView.text = error_apodo
                        }
                }
            }
        } else {
            // Esto no debería nunca pasar pq pues tienes que estar logueado para acceder a la app
            // Peeeero por si acaso jeje
            val usuario_no_autenticado = getString(R.string.usuario_no_autenticado)
            nicknameTextView.text = usuario_no_autenticado
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

    private fun mostrarDialogoIdioma() {
        val idiomaSpanish = getString(R.string.espa_ol)
        val idiomaEnglish = getString(R.string.ingles)
        val idiomas = arrayOf(idiomaSpanish, idiomaEnglish)
        val codigosIdioma = arrayOf("es", "en")

        val builder = android.app.AlertDialog.Builder(this)
        val seleccionIdioma = getString(R.string.seleccionaUnIdioma)
        builder.setTitle(seleccionIdioma)
        builder.setItems(idiomas) { _, which ->
            val codigoIdioma = codigosIdioma[which]
            guardarIdioma(codigoIdioma)
            actualizarIdioma(codigoIdioma)
            actualizarIdiomaSeleccionado(codigoIdioma)
            recreate()
        }
        builder.show()
    }

    private fun guardarIdioma(codigoIdioma: String) {
        val editor = sharedPreferences.edit()
        editor.putString("idioma", codigoIdioma)
        editor.apply()
    }

    private fun actualizarIdioma(codigoIdioma: String) {
        val locale = Locale(codigoIdioma)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.locale = locale
        baseContext.resources.updateConfiguration(configuration, baseContext.resources.displayMetrics)
    }

    private fun actualizarIdiomaSeleccionado(codigoIdioma: String) {
        val idioma = if(codigoIdioma == "es") getString(R.string.espa_ol) else getString(R.string.ingles)
        idiomaSeleccionadoTextView.text = idioma
    }

    override fun attachBaseContext(newBase: Context?) {
        val sharedPreferences = newBase?.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
        val idioma = sharedPreferences?.getString("idioma", "es") ?: "es"
        val locale = Locale(idioma)
        Locale.setDefault(locale)
        val configuration = Configuration(newBase?.resources?.configuration)
        configuration.setLocale(locale)
        val context = newBase?.createConfigurationContext(configuration)
        super.attachBaseContext(context)
    }
}