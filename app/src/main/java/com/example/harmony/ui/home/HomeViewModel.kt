package com.example.harmony.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _currentTitle = MutableStateFlow("Inicio")
    val currentTitle: StateFlow<String> = _currentTitle

    fun updateTitle(newTitle: String) {
        _currentTitle.value = newTitle
    }

    fun updateTitleToInicio(email: String, password: String, context: Context) {
        _currentTitle.value = "Inicio"

    }
}