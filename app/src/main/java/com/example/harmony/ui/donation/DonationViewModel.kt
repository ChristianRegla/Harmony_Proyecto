package com.example.harmony.ui.donation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DataBaseActions
import com.example.harmony.ui.common.DrawerActions
import com.example.harmony.ui.helpline.HelplineModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DonationViewModel(private val donationModel: DonationModel, private val context: Context) : ViewModel(), DrawerActions, DataBaseActions {
    private val firestore = FirebaseFirestore.getInstance()

    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo

    init {
        cargarApodoEnDrawerContent()
    }

    override fun cargarApodoEnDrawerContent() {
        viewModelScope.launch {
            _apodo.value = donationModel.cargarApodoEnDrawerContent(context, firestore)
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