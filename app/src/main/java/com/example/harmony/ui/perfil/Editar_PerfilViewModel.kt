package com.example.harmony.ui.perfil

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Editar_PerfilViewModel : ViewModel() {
    private val _Title = MutableStateFlow("Editar Perfil")
    val curTitle: StateFlow<String> = _Title

    fun tituloActualizado(newTitle: String) {
        _Title.value = newTitle
    }
    fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }
}