package com.example.harmony.ui.home

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val homeModel: HomeModel, private val context: Context) : ViewModel() {

    private val _currentTitle = MutableStateFlow("Inicio")
    val currentTitle: StateFlow<String> = _currentTitle

    fun updateTitle(newTitle: String) {
        _currentTitle.value = newTitle
    }

    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo

    init {
        cargarApodo()
    }

    private fun cargarApodo() {
        viewModelScope.launch {
            _apodo.value = homeModel.cargarApodoEnDrawerContent()
        }
    }

    fun cerrarSesion(navController: NavController) {
        viewModelScope.launch {
            // Eliminar el apodo del caché
            context.dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey("nickname"))
            }

            // Lógica de cierre de sesión de Firebase
            FirebaseAuth.getInstance().signOut()

            // Navegar a la pantalla de inicio de sesión
            navController.navigate("login") {
                popUpTo("main") { inclusive = true }
            }
        }
    }
}