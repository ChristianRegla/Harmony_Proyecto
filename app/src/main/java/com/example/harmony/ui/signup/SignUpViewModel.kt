package com.example.harmony.ui.signup

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.harmony.CustomToast
import com.example.harmony.R
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
import java.lang.Exception

class SignUpViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val db = FirebaseFirestore.getInstance()

    private val _signUpState = MutableStateFlow<ResultState<Unit>>(ResultState.Loading)
    val signUpState: StateFlow<ResultState<Unit>> = _signUpState

    fun crearCuenta(username: String, email: String, password: String, context: Context) {
        // Validación que los campos no estén vacíos
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            val mensaje = context.getString(R.string.error_campos_vacios)
            CustomToast.showAlertWithIcon(context, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
            return
        }

        // Validación de formato de correo electrónico
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            val mensaje = context.getString(R.string.error_correo_invalido)
            CustomToast.showAlertWithIcon(context, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
            return
        }

        _signUpState.value = ResultState.Loading

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val mensaje = context.getString(R.string.cuenta_creada_exitosamente)
                CustomToast.showInfoWithIcon(context, mensaje, R.drawable.ic_info, Toast.LENGTH_SHORT)

                val user = auth.currentUser
                user?.let {
                    // Guardamos el apodo en Cloud Firestore para consultarlo luego
                    val userDocRef = db.collection("usuarios").document(it.uid)
                    val userData = hashMapOf("apodo" to username)

                    userDocRef.set(userData).addOnSuccessListener {
                        val bienvenido = context.getString(R.string.bienvenido)
                        CustomToast.showWelcomeWithIcon(
                            context,
                            "$bienvenido $username",
                            R.drawable.logo_harmony,
                            Toast.LENGTH_SHORT
                        )
                        _signUpState.value = ResultState.Success(Unit)
                    }
                        .addOnFailureListener { e: Exception ->
                            if (e is FirebaseFirestoreException) {
                                println("Error al guardar el apodo en Firestore: ${e.message}")
                            } else {
                                println("Error al guardar el apodo en Firestore: ${e.message}")
                            }
                            println("Error al guardar el apodo en Firestore: ${e.message}")
                        }
                }
            } else {
                val exception = task.exception
                val mensaje = when (exception) {
                    is FirebaseAuthInvalidCredentialsException -> {
                        // Validamos si la contraseña es débil
                        if (exception.message?.contains("WEAK_PASSWORD") == true) {
                            context.getString(R.string.error_contraseña_debil)
                        } else {
                            // Validamos si el correo es inválido
                            context.getString(R.string.error_correo_invalido)
                        }
                    }
                    // Validamos si el correo ya está en uso
                    is FirebaseAuthUserCollisionException -> context.getString(R.string.correo_en_uso)
                    // Validamos si el usuario fue deshabilitado
                    is FirebaseAuthInvalidUserException -> context.getString(R.string.error_usuario_invalido)
                    // Validamos si el error de conexión es de red
                    is FirebaseNetworkException -> context.getString(R.string.error_sin_conexion)
                    // Validamos si hubo un error al crear la cuenta
                    else -> "${context.getString(R.string.error_al_crear_cuenta)} ${exception?.message}"
                }

                // Mostramos el mensaje de error correspondiente
                CustomToast.showAlertWithIcon(context, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
            }
        }
    }
}