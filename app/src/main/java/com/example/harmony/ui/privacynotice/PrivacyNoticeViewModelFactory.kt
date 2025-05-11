package com.example.harmony.ui.privacynotice

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PrivacyNoticeViewModelFactory(
    private val privacyNoticeModel: PrivacyNoticeModel,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PrivacyNoticeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PrivacyNoticeViewModel(privacyNoticeModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}