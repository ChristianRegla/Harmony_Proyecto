package com.example.harmony.ui.helpline

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HelplineViewModel : ViewModel() {

    private val _currentTitle = MutableStateFlow("header_linea_ayuda")

    val currentTitle: StateFlow<String> = _currentTitle

    fun updateTitle(newTitle: String) {
        _currentTitle.value = newTitle
    }

    fun updateTitleToInicio(email: String, password: String, context: Context) {
        _currentTitle.value = "header_linea_ayuda"

    }
}