package com.example.harmony.ui.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.harmony.utils.Constants
import com.example.harmony.ui.model.MessageModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.ai.client.generativeai.type.content
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var messagesListener: ListenerRegistration? = null

    val messageList = mutableStateListOf<MessageModel>()

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash-lite",
        apiKey = Constants.apiKey,
        generationConfig = GenerationConfig.Builder().apply {
            temperature = 0.3f
            topK = 20
            topP = 0.8f
        }.build(),
        systemInstruction = content {
            text(
                """
                Eres Harmony, un asistente de apoyo emocional certificado en primeros auxilios psicológicos.
        
                ## REGLAS CRÍTICAS:
                1. **NUNCA** diagnostiques condiciones mentales
                2. **SIEMPRE** deriva a profesionales para temas médicos
                3. **DETECTA** señales de riesgo suicida y actúa inmediatamente
        
                ## PROTOCOLO DE CRISIS (OBLIGATORIO):
                Si detectas: "quiero morirme", "no vale la pena", "hacerme daño":
                "🚨 Veo que estás pasando por algo muy difícil. Tu vida tiene valor. 
                Por favor contacta inmediatamente: Línea Nacional de Prevención del Suicidio 01-800-273-8255
                ¿Tienes a alguien de confianza cerca ahora mismo?"
        
                ## RESPUESTAS ESTRUCTURADAS:
                - Validación emocional primero
                - Pregunta de seguimiento
                - Técnica o recurso si aplica
                - Emoji sutil (máximo 1)
        
                ## RECHAZAR ELEGANTEMENTE:
                Para temas no emocionales: "Mi especialidad es el apoyo emocional. ¿Cómo te sientes con respecto a [tema relacionado]?"
        
                LONGITUD: Máximo 80 palabras por respuesta.
                TONO: Profesional-cálido, como un psicólogo experimentado.
                
                ## Palabras clave para rechazar adecuadamente:
                    [Receta, Juego, Película, Deporte, Política, Religión, Sexo, Droga, Violencia]
                    Respuesta: "Ese tema está fuera de mi especialidad. ¿Quieres hablar de algún desafío emocional que estés enfrentando?"
            """.trimIndent()
            )
        }
    )

    init {
        setupMessagesListener()
    }

    override fun onCleared() {
        super.onCleared()
        messagesListener?.remove()
    }

    private fun setupMessagesListener() {
        val userId = auth.currentUser?.uid ?: return

        // Cancelar listener anterior si existe
        messagesListener?.remove()

        messagesListener = db.collection("usuarios")
            .document(userId)
            .collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    // Manejar error
                    return@addSnapshotListener
                }

                snapshot?.let {
                    val messages = it.documents.mapNotNull { doc ->
                        doc.toObject(MessageModel::class.java)?.apply {
                            id = doc.id
                        }
                    }
                    messageList.clear()
                    messageList.addAll(messages)
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun sendMessage(question: String) {
        val userId = auth.currentUser?.uid ?: return

        viewModelScope.launch {
            try {
                val userMessage = MessageModel(
                    message = question,
                    role = "user",
                    timestamp = System.currentTimeMillis()
                )
                saveMessage(userId, userMessage)
                messageList.add(userMessage)

                val typingMessage = MessageModel(
                    message = "Escribiendo...",
                    role = "model",
                    timestamp = System.currentTimeMillis()
                )
                messageList.add(typingMessage)

                val chat = generativeModel.startChat(
                    history = messageList.filterNot { it == typingMessage }.map {
                        content(it.role) { text(it.message) }
                    }
                )
                val response = chat.sendMessage(question)

                // Reemplazamos "Escribiendo..." por la respuesta
                messageList.remove(typingMessage)
                val modelMessage = MessageModel(
                    message = response.text ?: "No se pudo obtener una respuesta",
                    role = "model",
                    timestamp = System.currentTimeMillis()
                )
                saveMessage(userId, modelMessage)
                messageList.add(modelMessage)

            }catch (e : Exception){
                messageList.removeIf { it.message == "Escribiendo..." }
                val errorMessage = MessageModel(
                    message = "Error: ${e.message}",
                    role = "model",
                    timestamp = System.currentTimeMillis()
                )
                saveMessage(userId, errorMessage)
            }
        }
    }

    private fun saveMessage(userId: String, message: MessageModel) {
        db.collection("usuarios")
            .document(userId)
            .collection("messages")
            .add(message.toMap())
            .addOnFailureListener { e ->
                Log.e("ChatViewModel", "Error saving message", e)
            }
    }

    fun deleteChatHistory() {
        val userId = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                val messagesCollection = db.collection("usuarios")
                    .document(userId)
                    .collection("messages")

                val querySnapshot = messagesCollection.get().await()
                if (!querySnapshot.isEmpty) {
                    val batch = db.batch()
                    for (document in querySnapshot) {
                        batch.delete(document.reference)
                    }
                    batch.commit().await()
                }
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error deleting chat history", e)
            }
        }
    }
}