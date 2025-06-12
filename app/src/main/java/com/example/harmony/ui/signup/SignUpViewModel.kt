package com.example.harmony.ui.signup

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harmony.CustomToast
import com.example.harmony.R
import com.example.harmony.data.repository.AuthRepository
import com.example.harmony.data.repository.UserPreferencesRepository
import com.example.harmony.utils.ResultState
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _signUpState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val signUpState: StateFlow<ResultState<String>> = _signUpState.asStateFlow()

    fun crearCuenta(
        username: String,
        email: String,
        password: String
    ) {
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _signUpState.value = ResultState.Error("Por favor, complete todos los campos")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _signUpState.value = ResultState.Error("Por favor, ingrese un correo electrónico válido")
            return
        }

        if(password.length < 6) {
            _signUpState.value = ResultState.Error("La contraseña debe tener al menos 6 caracteres")
            return
        }

        _signUpState.value = ResultState.Loading

        viewModelScope.launch {
            val result = authRepository.signUp(email, password, mapOf("apodo" to username))

            result.onSuccess { userProfile ->
                userPreferencesRepository.saveUserSession(userProfile.nickname ?: "Harmony", userProfile.email)
                _signUpState.value = ResultState.Success("Cuenta creada exitosamente")
            }.onFailure { exception ->
                val errorMessage = when (exception) {
                    is FirebaseAuthUserCollisionException -> "El correo electrónico ya está en uso"
                    is FirebaseAuthInvalidCredentialsException -> "El correo electrónico no es válido"
                    else -> exception.localizedMessage ?: "Error al crear la cuenta"
                }
                _signUpState.value = ResultState.Error(errorMessage)

            }
        }
    }

    fun resetSignUpState() {
        _signUpState.value = ResultState.Idle
    }
}