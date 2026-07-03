package com.example.alif.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ChatMessage(val text: String, val isUser: Boolean)

class AiTutorViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private var isInitialized = false

    fun initTopic(topic: String) {
        if (isInitialized) return
        isInitialized = true
        
        val initialText = if (topic == "General") {
            "Hello! I am your AI Arabic Tutor. How can I help you today?"
        } else {
            "You're studying $topic. What would you like help with?"
        }
        
        _messages.value = listOf(ChatMessage(initialText, false))
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        val userMsg = ChatMessage(text, true)
        _messages.value = _messages.value + userMsg
        _isLoading.value = true

        viewModelScope.launch {
            val responseText = try {
                generateContent(text)
            } catch (e: Exception) {
                "Sorry, I am having trouble connecting right now."
            }
            
            _messages.value = _messages.value + ChatMessage(responseText, false)
            _isLoading.value = false
        }
    }

    private suspend fun generateContent(prompt: String): String = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext "Please configure your Gemini API key in the secrets panel."
        }
        
        // Build conversation history
        val contents = _messages.value.map { msg ->
            Content(
                parts = listOf(Part(text = msg.text)),
                role = if (msg.isUser) "user" else "model"
            )
        } + Content(parts = listOf(Part(text = prompt)), role = "user")
        
        val systemInstruction = Content(
            parts = listOf(Part(text = "You are a helpful and encouraging Arabic language tutor. Keep answers short, simple, and suitable for beginners. Only answer questions related to Arabic words, letters, grammar, or quiz generation."))
        )

        val request = GenerateContentRequest(
            contents = contents,
            systemInstruction = systemInstruction
        )

        val response = RetrofitClient.service.generateContent(apiKey, request)
        response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "I don't have an answer for that."
    }
}
