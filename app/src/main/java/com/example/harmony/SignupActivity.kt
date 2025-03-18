package com.example.harmony

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.harmony.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import java.lang.Exception


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.crearCuentaButton.setOnClickListener {
            val username = binding.textUsernameInput.text.toString().trim()
            val email = binding.textEmailInput.text.toString().trim()
            val password = binding.textPasswordInput.text.toString().trim()

            // Validación que los campos no estén vacíos
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                val mensaje = getString(R.string.error_campos_vacios)
                CustomToast.showAlertWithIcon(this, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

            // Validación de formato de correo electrónico
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                val mensaje = getString(R.string.error_correo_invalido)
                CustomToast.showAlertWithIcon(this, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val mensaje = getString(R.string.cuenta_creada_exitosamente)
                        CustomToast.showInfoWithIcon(this, mensaje, R.drawable.ic_info, Toast.LENGTH_SHORT)

                        val user = auth.currentUser
                        user?.let{
                            // Guardamos el apodo en Cloud Firestore para consultarlo luego
                            val db = FirebaseFirestore.getInstance()
                            val userDocRef = db.collection("usuarios").document(it.uid)
                            val userData = hashMapOf("apodo" to username)

                            userDocRef.set(userData).addOnSuccessListener {
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                            }
                                .addOnFailureListener { e: Exception ->
                                    if(e is FirebaseFirestoreException){
                                        println("Error al guardar el apodo en Firestore: ${e.message}")
                                    } else{
                                        println("Error al guardar el apodo en Firestore: ${e.message}")
                                    }
                                    println("Error al guardar el apodo en Firestore: ${e.message}")
                                    }
                        }
                    } else {
                        val exception = task.exception
                        if(exception is FirebaseAuthInvalidCredentialsException){
                            val mensaje = getString(R.string.error_correo_invalido)
                            CustomToast.showAlertWithIcon(this, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
                        } else if(exception is FirebaseAuthUserCollisionException){
                            val mensaje = getString(R.string.correo_en_uso)
                            CustomToast.showAlertWithIcon(this, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
                        } else{
                            val mensaje = getString(R.string.error_al_crear_cuenta)
                            CustomToast.showAlertWithIcon(this, "$mensaje ${task.exception?.message}", R.drawable.ic_warning, Toast.LENGTH_SHORT)
                        }
                    }
                }
        }

        binding.textIniciarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Esto evita que el usuario regrese a SignUp Activity al presionar el botón de retroceso
        }
    }

    private fun registrarUsuario(username: String, email: String, password: String) {
        
    }
}