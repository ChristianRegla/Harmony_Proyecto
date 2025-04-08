package com.example.harmony.ui.contacto

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DrawerActions
import com.example.harmony.ui.perfil.ProfileModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactanosViewModel(private val contactanosModel: ContactanosModel, private val context: Context) : ViewModel(), DrawerActions {
    private val _Title = MutableStateFlow("Contactanos")
    val curTitle: StateFlow<String> = _Title

    fun tituloActualizado(newTitle: String) {
        _Title.value = newTitle
    }
    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo

    init {
        cargarApodoEnDrawerContent()
    }

    override fun cargarApodoEnDrawerContent() {
        viewModelScope.launch {
            _apodo.value = contactanosModel.cargarApodoEnDrawerContent()
        }
    }
    override fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }
}