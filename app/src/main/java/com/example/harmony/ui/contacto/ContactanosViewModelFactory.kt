package com.example.harmony.ui.contacto

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.harmony.ui.contacto.ContactanosModel
import com.example.harmony.ui.contacto.ContactanosViewModel

class ContactanosViewModelFactory(
    private val contactanosModel: ContactanosModel,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactanosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactanosViewModel(contactanosModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}