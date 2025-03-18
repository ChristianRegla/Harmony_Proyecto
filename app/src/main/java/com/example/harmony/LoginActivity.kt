package com.example.harmony

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.harmony.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = Firebase.auth

        binding.iniciarSesionButton.setOnClickListener {
            // Obtenemos el email y la contraseña del usuario y les quitamos los espacios en blanco con trim()
            val email = binding.textEmailInput.getText().toString().trim()
            val password = binding.textPasswordInput.getText().toString().trim()

            // Validación que los campos no estén vacíos
            if (email.isEmpty() || password.isEmpty()) {
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

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        user?.let{
                            val db = FirebaseFirestore.getInstance()
                            val userDocRef = db.collection("usuarios").document(it.uid)
                            userDocRef.get().addOnSuccessListener { document ->
                                if (document.exists()) {
                                    val apodo = document.getString("apodo")
                                    val bienvenida = getString(R.string.bienvenido)
                                    CustomToast.showWelcomeWithIcon(this, "$bienvenida $apodo", R.drawable.logo_harmony, Toast.LENGTH_SHORT)
                                    // Iniciamos el MainActivity luego de mostrar el Toast
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    val mensaje = getString(R.string.error_apodo)
                                    CustomToast.showAlertWithIcon(this, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
                                }
                            }
                        }

                    } else {
                        // If sign in fails, display a message to the user.
                        val mensaje = getString(R.string.error_al_iniciar_sesion)
                        CustomToast.showAlertWithIcon(this, mensaje, R.drawable.ic_warning, Toast.LENGTH_SHORT)
                    }
                }
        }

        binding.textCrearCuenta.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
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

    private fun iniciarSesion(email: String, correo: String) {

    }
}