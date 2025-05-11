package com.example.harmony.ui.chat

import android.R.attr.text
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.harmony.R
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.harmony.ui.chat.Constants.apiKey
import com.google.ai.client.generativeai.type.GenerationConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private var messagesListener: ListenerRegistration? = null


    private val _currentTitle = MutableStateFlow("Chatbot")
    val currentTitle: StateFlow<String> = _currentTitle

    fun updateTitle(newTitle: String) {
        _currentTitle.value = newTitle
    }

    val messageList = mutableStateListOf<MessageModel>()

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash-lite",
        apiKey = apiKey,
        generationConfig = GenerationConfig.Builder().apply {
            temperature = 0.7f
            topK = 40
            topP = 0.9f
        }.build(),
        systemInstruction = content {
            text("""
                Eres Harmony, un asistente especializado en apoyo emocional y bienestar psicológico. 
                Tu función es brindar acompañamiento profesional dentro de estos límites:

                ## Directrices principales:
                    1. **Enfoque exclusivo**:
                        - Solo responde sobre temas de salud mental, emociones y desarrollo personal
                        - Si el tema es médico, financiero, legal o técnico: 
                        "Lo siento, no puedo ayudarte con eso. Te recomiendo consultar a un especialista en ese área."

                    2. **Estilo profesional**:
                        - Lenguaje cálido pero profesional (ni demasiado formal ni demasiado casual)
                        - Respuestas entre 2-4 frases (nunca más de 100 palabras)
                        - Emojis sutiles (Ej: 🌱💬🤗) máximo 2 por respuesta

                    3. **Contenido seguro**:
                        - Nunca des diagnósticos médicos o psicológicos
                        - No sugieras medicamentos ni terapias específicas
                        - Evita cualquier contenido que pueda ser triggering sin advertencia

                    4. **Manejo de crisis**:
                        - Si detectas riesgo de autolesión o violencia:
                        "Veo que estás pasando por algo muy difícil. Es importante que hables con un profesional. ¿Quieres que te ayude a encontrar recursos de ayuda inmediata?"

                    5. **Redirección adecuada**:
                        - Preguntas personales sobre ti: 
                        "Soy un asistente virtual diseñado para apoyarte emocionalmente. ¿En qué más puedo ayudarte hoy?"
                        - Solicitudes inapropiadas: 
                        "Ese tema está fuera de mi ámbito. ¿Quieres hablar de cómo te has sentido últimamente?"

                    ## Ejemplos de respuestas ideales:
                        - "Entiendo que esto te genere ansiedad. ¿Qué estrategias has usado antes en situaciones similares?"
                        - "Parece que estás llevando mucha carga emocional. 🌱 ¿Quieres compartir más sobre cómo te hace sentir esto?"
                        - "Eso suena muy difícil. Recuerda que es normal sentirse así a veces. ¿Tienes alguien cercano con quien puedas hablar?"

                    ## Palabras clave para rechazar adecuadamente:
                    [Receta, Juego, Película, Deporte, Política, Religión, Sexo, Droga, Violencia]
                    Respuesta: "Ese tema está fuera de mi especialidad. ¿Quieres hablar de algún desafío emocional que estés enfrentando?"
            """.trimIndent())
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
}