package com.example.harmony.data.repository

import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

// 1. Data Class para un retorno de datos claro
data class UserProfile(
    val uid: String,
    val email: String?,
    val nickname: String?
)

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Obtiene el usuario actualmente autenticado
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Inicia sesión con email y contraseña.
     * @return Result<UserProfile> que contiene el perfil del usuario en caso de éxito.
     */
    suspend fun signInWithEmail(email: String, password: String): Result<UserProfile> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.failure(Exception("Error de Firebase: Usuario no encontrado tras el login."))

            val userProfile = getUserProfile(user.uid)
            if (userProfile != null) {
                Result.success(userProfile)
            } else {
                Result.failure(Exception("Perfil de usuario no encontrado en la base de datos."))
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "signInWithEmail failed", e)
            Result.failure(e)
        }
    }

    /**
     * Inicia sesión o registra un usuario usando una credencial de Google.
     * @return Result<UserProfile> que contiene el perfil del usuario.
     */
    suspend fun signInWithGoogleCredential(credential: AuthCredential): Result<UserProfile> {
        return try {
            val authResult = auth.signInWithCredential(credential).await()
            val user = authResult.user ?: return Result.failure(Exception("Error de Firebase: Usuario de Google no encontrado."))

            // Comprueba si es un nuevo usuario
            val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false

            // Busca el perfil en Firestore. Si no existe, lo crea.
            var userProfile = getUserProfile(user.uid)
            if (isNewUser || userProfile == null) {
                Log.d("AuthRepository", "Creando nuevo perfil para usuario de Google: ${user.uid}")
                // Crea un perfil básico. El apodo puede ser parte del nombre de Google o se puede pedir después.
                val nickname = user.displayName?.split(" ")?.first() ?: "HarmonyUser"
                val newUserProfile = UserProfile(
                    uid = user.uid,
                    email = user.email,
                    nickname = nickname
                )
                createUserProfileInFirestore(newUserProfile)
                userProfile = newUserProfile
            }
            Result.success(userProfile)
        } catch (e: Exception) {
            Log.e("AuthRepository", "signInWithGoogle failed", e)
            Result.failure(e)
        }
    }

    /**
     * Registra un nuevo usuario con email, contraseña y datos de perfil.
     * @return Result<UserProfile> que contiene el nuevo perfil del usuario.
     */
    suspend fun signUp(email: String, password: String, profileData: Map<String, Any>): Result<UserProfile> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user ?: return Result.failure(Exception("Error de Firebase: No se pudo crear el usuario."))

            // Crea el perfil en Firestore con los datos proporcionados
            db.collection("usuarios").document(user.uid).set(profileData, SetOptions.merge()).await()

            val newUserProfile = UserProfile(
                uid = user.uid,
                email = email,
                nickname = profileData["apodo"] as? String
            )
            Result.success(newUserProfile)

        } catch (e: Exception) {
            Log.e("AuthRepository", "signUp failed", e)
            Result.failure(e)
        }
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun signOut() {
        auth.signOut()
    }

    /**
     * Función privada para obtener el perfil de un usuario desde Firestore.
     */
    private suspend fun getUserProfile(uid: String): UserProfile? {
        return try {
            val document = db.collection("usuarios").document(uid).get().await()
            if (document.exists()) {
                UserProfile(
                    uid = uid,
                    email = document.getString("email"),
                    nickname = document.getString("apodo")
                )
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("AuthRepository", "getUserProfile failed", e)
            null
        }
    }

    /**
     * Función privada para crear un documento de perfil en Firestore.
     */
    private suspend fun createUserProfileInFirestore(profile: UserProfile) {
        val userData = hashMapOf(
            "email" to profile.email,
            "apodo" to profile.nickname,
            // Puedes añadir otros campos por defecto aquí, como la fecha de creación
            // "fechaCreacion" to FieldValue.serverTimestamp()
        )
        db.collection("usuarios").document(profile.uid).set(userData, SetOptions.merge()).await()
    }
}