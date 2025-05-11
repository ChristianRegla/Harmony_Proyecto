package com.example.harmony.ui.donation

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DrawerActions
import com.example.harmony.ui.helpline.HelplineModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DonationViewModel(private val donationModel: DonationModel, private val context: Context) : ViewModel(), DrawerActions {

    private val _currentTitle = MutableStateFlow("Donaciones")
    val currentTitle: StateFlow<String> = _currentTitle

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
            _apodo.value = donationModel.cargarApodoEnDrawerContent()
        }
    }

    override fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()

        navController.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }

    fun callPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$phoneNumber".toUri()
        }
        startActivity(context, intent, null)
    }
}