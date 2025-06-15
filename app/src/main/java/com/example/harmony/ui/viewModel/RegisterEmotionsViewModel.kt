package com.example.harmony.ui.viewModel

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.harmony.utils.dataStore
import com.example.harmony.ui.common.DataBaseActions
import com.example.harmony.ui.common.DrawerActions
import com.example.harmony.ui.model.RegisterEmotionsModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterEmotionsViewModel(private val registerEmotionsModel: RegisterEmotionsModel, private val context: Context) : ViewModel(),
    DrawerActions, DataBaseActions {

    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo
    private val db = FirebaseFirestore.getInstance()

    init {
        cargarApodoEnDrawerContent()
    }

    override fun cargarApodoEnDrawerContent() {
        viewModelScope.launch {
            _apodo.value = registerEmotionsModel.cargarApodoEnDrawerContent(context, db)
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

    override fun uploadProfileImage(uri: Uri) {
        TODO("Not yet implemented")
    }

    override fun guardarImagenEnFirestore(userId: String, imageUrl: String) {
        TODO("Not yet implemented")
    }

}