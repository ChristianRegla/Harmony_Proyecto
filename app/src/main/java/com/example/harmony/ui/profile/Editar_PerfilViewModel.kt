package com.example.harmony.ui.profile

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Editar_PerfilViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val uid = FirebaseAuth.getInstance().currentUser?.uid

    // Campos observables
    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre

    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _numero = MutableStateFlow("")
    val numero: StateFlow<String> = _numero

    private val _ciudad = MutableStateFlow("")
    val ciudad: StateFlow<String> = _ciudad

    private val _genero = MutableStateFlow("")
    val genero: StateFlow<String> = _genero

    private val _domicilio = MutableStateFlow("")
    val domicilio: StateFlow<String> = _domicilio

    init {
        cargarDatosUsuario()
    }
    private val _Title = MutableStateFlow("Editar Perfil")
    val curTitle: StateFlow<String> = _Title

    fun cargarDatosUsuario() {
        // Cargar datos de Firestore
        uid?.let { userId ->
            db.collection("usuarios").document(userId).get()
                .addOnSuccessListener { document ->
                    _nombre.value = document.getString("nombre") ?: ""
                    _apodo.value = document.getString("apodo") ?: ""
                    _numero.value = document.getString("numero") ?: ""
                    _ciudad.value = document.getString("ciudad") ?: ""
                    _genero.value = document.getString("genero") ?: ""
                    _domicilio.value = document.getString("domicilio") ?: ""
                }
        }

        // Cargar correo desde Auth
        val currentUser = FirebaseAuth.getInstance().currentUser
        _email.value = currentUser?.email ?: ""
    }

    fun actualizarDatos(
        nombre: String,
        apodo: String,
        numero: String,
        ciudad: String,
        genero: String,
        domicilio: String
    ) {
        uid?.let { userId ->
            val updates = mapOf(
                "nombre" to nombre,
                "apodo" to apodo,
                "numero" to numero,
                "ciudad" to ciudad,
                "genero" to genero,
                "domicilio" to domicilio
            )
            db.collection("usuarios").document(userId).update(updates)
        }
    }

    fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }
}