package com.example.harmony.ui.login

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harmony.R
import com.example.harmony.data.repository.AuthRepository
import com.example.harmony.utils.ResultState
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val auth: FirebaseAuth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()

    private val _loginState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val loginState: StateFlow<ResultState<String>> = _loginState.asStateFlow()

    private val _infoMessage = MutableStateFlow<String?>(null)
    val infoMessage: StateFlow<String?> = _infoMessage.asStateFlow()

    fun signInWithGoogleCredential(credential: AuthCredential, context: Context) {
        _loginState.value = ResultState.Loading
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // El resto de la lógica para obtener el apodo es igual
                val user = auth.currentUser
                user?.let { firebaseUser ->
                    val userDocRef = db.collection("usuarios").document(firebaseUser.uid)
                    userDocRef.get().addOnSuccessListener { document ->
                        if (document.exists()) {
                            val apodo = document.getString("apodo") ?: "Harmony"
                            val bienvenida = context.getString(R.string.bienvenido)

                            _infoMessage.value = "$bienvenida, $apodo!"

                            viewModelScope.launch {
                                context.dataStore.edit { preferences ->
                                    preferences[stringPreferencesKey("nickname")] = apodo
                                    preferences[stringPreferencesKey("email")] = firebaseUser.email ?: ""
                                }
                            }
                            _loginState.value = ResultState.Success("Inicio de sesión con Google exitoso.")
                        } else {
                            // Maneja el caso donde el usuario de Google no tiene documento en Firestore
                            // Podrías crearlo aquí o redirigir a una pantalla para crear el perfil.
                            Log.w("LoginViewModel", "Usuario de Google autenticado pero sin documento en Firestore.")
                            _loginState.value = ResultState.Success("Inicio de sesión exitoso. Completa tu perfil.")
                        }
                    }
                }
            } else {
                val mensaje = context.getString(R.string.error_al_iniciar_sesion_google) // Usa un string de error específico
                _loginState.value = ResultState.Error(task.exception?.localizedMessage ?: mensaje)
            }
        }
    }

    fun iniciarSesion(email: String, password: String, context: Context) {
        // Validación de campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            _loginState.value = ResultState.Error(context.getString(R.string.error_campos_vacios))
            return
        }

        // Validación del formato del correo
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginState.value = ResultState.Error(context.getString(R.string.error_correo_invalido))
            return
        }
        _loginState.value = ResultState.Loading

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                user?.let { firebaseUser ->
                    val userDocRef = db.collection("usuarios").document(firebaseUser.uid)
                    userDocRef.get().addOnSuccessListener { document ->
                        if (document.exists()) {
                            val apodo = document.getString("apodo") ?: "Harmony"
                            val bienvenida = context.getString(R.string.bienvenido)
                            _infoMessage.value = "$bienvenida, $apodo!"

                            // Se guarda el apodo en el caché
                            viewModelScope.launch {
                                context.dataStore.edit { preferences ->
                                    preferences[stringPreferencesKey("nickname")] = apodo
                                }
                            }
                            _loginState.value = ResultState.Success("Inicio de sesión exitoso.")
                        } else {
                            _loginState.value = ResultState.Error(context.getString(R.string.error_apodo))
                        }
                    }
                }
            } else {
                _loginState.value = ResultState.Error(context.getString(R.string.error_al_iniciar_sesion))
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = ResultState.Idle
    }

    fun onInfoMessageShown() {
        _infoMessage.value = null
    }
}