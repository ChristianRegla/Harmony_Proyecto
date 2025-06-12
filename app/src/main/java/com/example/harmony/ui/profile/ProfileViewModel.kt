package com.example.harmony.ui.profile

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DataBaseActions
import com.example.harmony.ui.common.DrawerActions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ProfileViewModel(private val profileModel: ProfileModel, private val context: Context) : ViewModel(), DrawerActions, DataBaseActions{
    private val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    private val _imagenUrl = MutableStateFlow<String?>(null)
    val imagenUrl: StateFlow<String?> = _imagenUrl.asStateFlow()

    val perfil = MutableStateFlow<PerfilModel?>(null)

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    private val _uploadError = MutableStateFlow<String?>(null)
    val uploadError: StateFlow<String?> = _uploadError.asStateFlow()

    private val _apodo = MutableStateFlow("")
    val apodo: StateFlow<String> = _apodo

    init {
        loadUserProfile()
        cargarApodoEnDrawerContent()
    }

    private fun loadUserProfile() {
        auth.currentUser?.uid?.let { userId ->
            firestore.collection("usuarios").document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e("Firebase", "Error al escuchar cambios: ${error.message}")
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        val perfilData = snapshot.toObject(PerfilModel::class.java)
                        perfil.value = perfilData?.copy(userID = userId)

                        val imagen = snapshot.getString("imagenes") // <<< aquí lees "imagenes" explícitamente
                        _imagenUrl.value = imagen

                        Log.d("PerfilViewModel", "Imagen recuperada manualmente: $imagen")
                    } else {
                        perfil.value = null
                        _imagenUrl.value = null
                    }
                }
        }
    }

    fun updateProfile(apodo: String) {
        auth.currentUser?.uid?.let { userId ->
            perfil.value?.copy(apodo = apodo)?.let { updatedPerfil ->
                firestore.collection("usuarios").document(userId)
                    .set(updatedPerfil)
                    .addOnSuccessListener {
                        Log.d("Firebase", "Perfil actualizado con éxito.")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firebase", "Error al actualizar perfil: ${e.message}")
                    }
            }
        }
    }

    override fun uploadProfileImage(uri: Uri) {
        val userId = auth.currentUser?.uid ?: return
        _isUploading.value = true

        // En lugar de subir, solo guardar el nombre o ruta de la imagen local
        val imageName = uri.lastPathSegment ?: uri.toString() // o solo el nombre que asocies con la imagen
        guardarImagenEnFirestore(userId, imageName)
        _imagenUrl.value = imageName
        _isUploading.value = false
    }

    override fun guardarImagenEnFirestore(userId: String, imageName: String) {
        val userDocRef = firestore.collection("usuarios").document(userId)
        val userData = hashMapOf("imagenes" to imageName)

        userDocRef.set(userData, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Firebase", "Nombre de imagen guardado correctamente.")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error al guardar nombre de imagen: ${e.message}")
            }
    }

    override fun cargarApodoEnDrawerContent() {
        viewModelScope.launch {
            _apodo.value = profileModel.cargarApodoEnDrawerContent(context, firestore)
        }
    }

    override fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }
}