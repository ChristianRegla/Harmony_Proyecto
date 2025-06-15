package com.example.harmony.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harmony.R
import com.example.harmony.data.repository.AuthRepository
import com.example.harmony.data.repository.UserPreferencesRepository
import com.example.harmony.utils.ResultState
import com.example.harmony.utils.UiText
import com.google.firebase.auth.AuthCredential
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<ResultState<UiText>>(ResultState.Idle)
    val loginState: StateFlow<ResultState<UiText>> = _loginState.asStateFlow()

    private val _infoMessage = MutableStateFlow<UiText?>(null)
    val infoMessage: StateFlow<UiText?> = _infoMessage.asStateFlow()

    fun signInWithGoogleCredential(credential: AuthCredential) {
        _loginState.value = ResultState.Loading
        viewModelScope.launch {
            val result = authRepository.signInWithGoogleCredential(credential)

            result.onSuccess { userProfile ->
                val nickname = userProfile.nickname ?: "Harmony"
                userPreferencesRepository.saveUserSession(nickname, userProfile.email)

                _infoMessage.value = UiText.StringResource(R.string.bienvenido, nickname)
                _loginState.value = ResultState.Success(UiText.StringResource(R.string.login_success))
            }.onFailure { exception ->
                val errorMessage = exception.localizedMessage?.let { UiText.DynamicString(it) }
                    ?: UiText.StringResource(R.string.error_unknown)
                _loginState.value = ResultState.Error(errorMessage)
            }
        }
    }

    fun iniciarSesion(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginState.value = ResultState.Error(UiText.StringResource(R.string.error_campos_vacios))
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _loginState.value = ResultState.Error(UiText.StringResource(R.string.error_correo_invalido))
            return
        }
        _loginState.value = ResultState.Loading

        viewModelScope.launch {
            val result = authRepository.signInWithEmail(email, password)

            result.onSuccess {
                _infoMessage.value = UiText.StringResource(R.string.bienvenido, it.nickname ?: "Harmony")
                _loginState.value = ResultState.Success(UiText.StringResource(R.string.login_success))
            }.onFailure { exception ->
                val errorMessage = exception.localizedMessage?.let { UiText.DynamicString(it) }
                    ?: UiText.StringResource(R.string.error_unknown)
                _loginState.value = ResultState.Error(errorMessage)
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