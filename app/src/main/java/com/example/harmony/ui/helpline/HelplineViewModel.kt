package com.example.harmony.ui.helpline

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HelplineViewModel : ViewModel() {

    private val _currentTitle = MutableStateFlow("Linea de Ayuda")
    val currentTitle: StateFlow<String> = _currentTitle

    fun updateTitle(newTitle: String) {
        _currentTitle.value = newTitle
    }

    fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()

        navController.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }
}