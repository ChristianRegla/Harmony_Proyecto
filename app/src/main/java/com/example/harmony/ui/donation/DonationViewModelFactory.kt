package com.example.harmony.ui.donation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DonationViewModelFactory(
    private val donationModel: DonationModel,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DonationViewModel(donationModel, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}