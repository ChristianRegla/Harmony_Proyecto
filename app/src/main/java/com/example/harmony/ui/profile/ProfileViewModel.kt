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

    private val _currentTitle = MutableStateFlow("Perfil")
    val currentTitle: StateFlow<String> = _currentTitle

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

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
                        perfil.value = snapshot.toObject(PerfilModel::class.java)?.copy(userID = userId)
                    } else {
                        perfil.value = null
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

        val imageRef = storage.reference.child("profile_images/$userId.jpg")

        imageRef.putFile(uri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    val imagenUrl = downloadUrl.toString()
                    guardarImagenEnFirestore(userId, imagenUrl)
                    _imagenUrl.value = imagenUrl
                    _isUploading.value = false
                    Log.d("Storage", "Imagen subida correctamente.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Storage", "Error subiendo imagen: ${e.message}")
                _uploadError.value = e.message
                _isUploading.value = false
            }
    }


    override fun guardarImagenEnFirestore(userId: String, imageUrl: String) {
        val userDocRef = firestore.collection("usuarios").document(userId)

        val userData = hashMapOf("imagenes" to imageUrl)

        userDocRef.set(userData, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("Firebase", "Imagen guardada correctamente en el campo 'imagenes'.")
            }
            .addOnFailureListener { e ->
                Log.e("Firebase", "Error al guardar la imagen en Firestore: ${e.message}")
            }
    }




    fun updateTitle(newTitle: String) {
        _currentTitle.value = newTitle
    }

    override fun cargarApodoEnDrawerContent() {
        viewModelScope.launch {
            _apodo.value = profileModel.cargarApodoEnDrawerContent()
        }
    }

    override fun cerrarSesion(navController: NavHostController) {
        FirebaseAuth.getInstance().signOut()
        navController.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }
}
