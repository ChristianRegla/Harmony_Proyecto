package com.example.harmony.ui.profile

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.harmony.ui.common.DrawerActions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ProfileViewModel(private val profileModel: ProfileModel, private val context: Context) : ViewModel(), DrawerActions {

    private val _currentTitle = MutableStateFlow("Perfil")
    val currentTitle: StateFlow<String> = _currentTitle

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    val perfil = MutableStateFlow<PerfilModel?>(null)

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading.asStateFlow()

    private val _uploadError = MutableStateFlow<String?>(null)
    val uploadError: StateFlow<String?> = _uploadError.asStateFlow()

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        auth.currentUser?.uid?.let { userId ->
            firestore.collection("usuarios").document(userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        // Manejar el error
                        return@addSnapshotListener
                    }

                    if (snapshot != null && snapshot.exists()) {
                        perfil.value = snapshot.toObject(PerfilModel::class.java)?.copy(userID = userId)

                    } else {
                        // El documento no existe o está vacío
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
                        // Actualización exitosa
                    }
            }
        }
    }

    fun uploadProfileImage(imageUri: Uri) {
        viewModelScope.launch {
            try {
                _isUploading.value = true
                _uploadError.value = null

                val userId = auth.currentUser?.uid ?: return@launch
                val storageRef = storage.reference.child("profile_images/${userId}/${UUID.randomUUID()}")
                val uploadTask = storageRef.putFile(imageUri)

                uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful){
                        task.exception?.let { throw it }
                    }
                    storageRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val downloadUri = task.result.toString()
                        updateProfileImageUrl(downloadUri.toString())
                    } else {
                        _uploadError.value = "Error al subir la imagen"
                    }
                    _isUploading.value = false
                }
            } catch (e: Exception) {
                _uploadError.value = "Error al cargar la imagen: ${e.localizedMessage}"
                _isUploading.value = false
            }
        }
    }

    private fun updateProfileImageUrl(imageUrl: String) {
        auth.currentUser?.uid?.let { userId ->
            firestore.collection("usuarios").document(userId)
                .update("profileImageUrl", imageUrl)
                .addOnFailureListener { e ->
                    // Manejar el error
                }
        }
    }

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