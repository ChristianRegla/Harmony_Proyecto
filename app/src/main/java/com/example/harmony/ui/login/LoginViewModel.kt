package com.example.harmony.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harmony.data.repository.AuthRepository
import com.example.harmony.data.repository.UserPreferencesRepository
import com.example.harmony.utils.ResultState
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val loginState: StateFlow<ResultState<String>> = _loginState.asStateFlow()

    private val _infoMessage = MutableStateFlow<String?>(null)
    val infoMessage: StateFlow<String?> = _infoMessage.asStateFlow()

    fun signInWithGoogleCredential(credential: AuthCredential) {
        _loginState.value = ResultState.Loading
        viewModelScope.launch {
            val result = authRepository.signInWithGoogleCredential(credential)

            result.onSuccess { userProfile ->
                userPreferencesRepository.saveUserSession(userProfile.nickname ?: "Harmony", userProfile.email)

                _infoMessage.value = "¡Bienvenido, ${userProfile.nickname}!"
                _loginState.value = ResultState.Success("Inicio de sesión con Google exitoso.")
            }.onFailure { exception ->
                _loginState.value = ResultState.Error(exception.localizedMessage ?: "Error desconocido en Google Sign-In")
            }
        }
    }

    fun iniciarSesion(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginState.value = ResultState.Error("Los campos no pueden estar vacios")
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginState.value = ResultState.Error("El formato del correo no es válido")
            return
        }
        _loginState.value = ResultState.Loading

        viewModelScope.launch {
            val result = authRepository.signInWithEmail(email, password)

            result.onSuccess { userProfile ->
                _infoMessage.value = ResultState.Success("Inicio de sesión exitoso.").toString()
                _loginState.value = ResultState.Success("Inicio de sesión exitoso.")
            }.onFailure { exception ->
                _loginState.value = ResultState.Error(exception.localizedMessage ?: "Error desconocido")
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