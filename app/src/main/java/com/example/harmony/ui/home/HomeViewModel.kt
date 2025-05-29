package com.example.harmony.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DataBaseActions
import com.example.harmony.ui.common.DrawerActions
import com.example.harmony.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val homeModel: HomeModel, private val context: Context) : ViewModel(), DrawerActions, DataBaseActions {

    private val _currentTitle = MutableStateFlow("Inicio")
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
            _apodo.value = homeModel.cargarApodoEnDrawerContent(context, db)
        }
    }

    override fun cerrarSesion(navController: NavHostController) {
        viewModelScope.launch {
            // Eliminar el apodo del caché
            context.dataStore.edit { preferences ->
                preferences.remove(stringPreferencesKey("nickname"))
                preferences.remove(stringPreferencesKey("email"))
            }

            FirebaseAuth.getInstance().signOut()
            kotlinx.coroutines.delay(100)

            navController.navigate("login") {
                popUpTo(navController.graph.findStartDestination().id) { // Pop up to the start of the graph
                    inclusive = true
                }
                launchSingleTop = true // Asegura que no haya múltiples "login"
            }
        }
    }

    override fun uploadProfileImage(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun guardarImagenEnFirestore(userId: String, imageUrl: String) {
        TODO("Not yet implemented")
    }
}