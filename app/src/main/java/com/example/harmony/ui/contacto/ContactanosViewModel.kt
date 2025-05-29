package com.example.harmony.ui.contacto

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DataBaseActions
import com.example.harmony.ui.common.DrawerActions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactanosViewModel(private val contactanosModel: ContactanosModel, private val context: Context) : ViewModel(), DrawerActions, DataBaseActions {
    private val _Title = MutableStateFlow("Contactanos")
    val curTitle: StateFlow<String> = _Title
    private val firestore = FirebaseFirestore.getInstance()

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
            _apodo.value = contactanosModel.cargarApodoEnDrawerContent(context, firestore)
        }
    }
    override fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }

    override fun uploadProfileImage(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun guardarImagenEnFirestore(userId: String, imageUrl: String) {
        TODO("Not yet implemented")
    }
}