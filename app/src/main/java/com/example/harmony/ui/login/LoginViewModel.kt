package com.example.harmony.ui.login

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harmony.CustomToast
import com.example.harmony.R
import com.example.harmony.data.repository.AuthRepository
import com.example.harmony.utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()

    private val _loginState = MutableStateFlow<ResultState<Unit>>(ResultState.Loading)
    val loginState: StateFlow<ResultState<Unit>> = _loginState

    fun iniciarSesion(email: String, password: String, context: Context) {
        // Validación de campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            val mensaje = context.getString(R.string.error_campos_vacios)
            CustomToast.showAlertWithIcon(context, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
            return
        }

        // Validación del formato del correo
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val mensaje = context.getString(R.string.error_correo_invalido)
            CustomToast.showAlertWithIcon(context, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
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
                            val apodo = document.getString("apodo")
                            val bienvenida = context.getString(R.string.bienvenido)
                            CustomToast.showWelcomeWithIcon(
                                context,
                                "$bienvenida $apodo",
                                R.drawable.logo_harmony,
                                Toast.LENGTH_SHORT
                            )

                            // Se guarda el apodo en el caché
                            viewModelScope.launch {
                                context.dataStore.edit { preferences ->
                                    preferences[stringPreferencesKey("nickname")] = apodo ?: ""
                                }
                            }
                            _loginState.value = ResultState.Success(Unit)
                        } else {
                            val mensaje = context.getString(R.string.error_apodo)
                            CustomToast.showAlertWithIcon(
                                context,
                                mensaje,
                                R.drawable.ic_warning,
                                Toast.LENGTH_SHORT
                            )
                            _loginState.value = ResultState.Error(mensaje)
                        }
                    }
                }
            } else {
                val mensaje = context.getString(R.string.error_al_iniciar_sesion)
                CustomToast.showAlertWithIcon(context, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
                _loginState.value = ResultState.Error(mensaje)
            }
        }
    }
}