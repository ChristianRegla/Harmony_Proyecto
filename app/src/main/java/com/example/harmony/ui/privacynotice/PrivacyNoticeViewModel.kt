package com.example.harmony.ui.privacynotice

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DrawerActions
import com.google.firebase.auth.FirebaseAuth
import dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PrivacyNoticeViewModel(private val privacyNoticeModel: PrivacyNoticeModel, private val context: Context) : ViewModel(), DrawerActions {

    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo

    init {
        cargarApodoEnDrawerContent()
    }

    override fun cargarApodoEnDrawerContent() {
        viewModelScope.launch {
            _apodo.value = privacyNoticeModel.cargarApodoEnDrawerContent()
        }
    }

    override fun cerrarSesion(navController: NavHostController) {
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