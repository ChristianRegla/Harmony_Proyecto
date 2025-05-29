package com.example.harmony.ui.relax

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DataBaseActions
import com.example.harmony.ui.common.DrawerActions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RelaxViewModel(private val relaxModel: RelaxModel, private val context: Context) : ViewModel(), DrawerActions, DataBaseActions {
    private val _currentTitle = MutableStateFlow("Relax")
    val currentTitle: StateFlow<String> = _currentTitle
    private val db = FirebaseFirestore.getInstance()

    fun updateTitle(newTitle: String) {
        _currentTitle.value = newTitle
    }

    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo

    init {
        cargarApodoEnDrawerContent()
    }

    override fun cargarApodoEnDrawerContent() {
        viewModelScope.launch {
            _apodo.value = relaxModel.cargarApodoEnDrawerContent(context, db)
        }
    }

    override fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()

        navController.navigate("login") {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true // Buena práctica para destinos como login/home
        }
    }

    override fun uploadProfileImage(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun guardarImagenEnFirestore(userId: String, imageUrl: String) {
        TODO("Not yet implemented")
    }
}