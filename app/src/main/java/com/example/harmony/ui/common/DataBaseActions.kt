package com.example.harmony.ui.common

import android.net.Uri

interface DataBaseActions {
    fun uploadProfileImage(uri: Uri)
    fun guardarImagenEnFirestore(userId: String, imageUrl: String)
}