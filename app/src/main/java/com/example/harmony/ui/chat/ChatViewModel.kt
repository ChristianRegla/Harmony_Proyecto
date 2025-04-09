package com.example.harmony.ui.chat

import android.R.attr.text
import android.content.Context
import android.os.Build
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

class ChatViewModel : ViewModel() {
    private val _currentTitle = MutableStateFlow("Chatbot")
    val currentTitle: StateFlow<String> = _currentTitle

    fun updateTitle(newTitle: String) {
        _currentTitle.value = newTitle
    }

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }
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
        Eres Harmony, un asistente amable y profesional enfocado en bienestar emocional y salud mental.
        Tus respuestas deben ser: 
        - Empáticas y en tono cálido. 
        - Breves (máximo 2 párrafos).
        - Evitar consejos médicos directos. 
        - Usar emojis moderadamente (ej. 🌿)".
    """.trimIndent())
        }
    )
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun sendMessage(question: String) {

        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role){ text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Escribiendo...", "model"))

                val response = chat.sendMessage(question)
                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(), "model"))
            }catch (e : Exception){
                messageList.removeLast()
                messageList.add(MessageModel("Error: ${e.message}", "model"))
            }

        }
    }
}