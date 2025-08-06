// MainActivity.kt
package com.example.chatbot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.json.JSONArray
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()

    private val apiKey = "YOUR-OWN-API-KEY" // Replace with your key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatAdapter = ChatAdapter(messages)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = chatAdapter

        binding.sendButton.setOnClickListener {
            val userInput = binding.userInputEditText.text.toString()
            if (userInput.isNotBlank()) {
                addMessage(userInput, isUser = true)
                binding.userInputEditText.text.clear()
                sendToLLM(userInput)
            }
        }
    }

    private fun addMessage(text: String, isUser: Boolean) {
        messages.add(ChatMessage(text, isUser))
        chatAdapter.notifyItemInserted(messages.size - 1)
        binding.chatRecyclerView.scrollToPosition(messages.size - 1)
    }

    private fun sendToLLM(prompt: String) {
        val client = OkHttpClient()
        val json = JSONObject().apply {
            put("model", "mistralai/mistral-7b-instruct")
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", prompt)
                })
            })
        }
        val mediaType = "application/json".toMediaType()
        val body = json.toString().toRequestBody(mediaType)

        val request = Request.Builder()
            .url("https://openrouter.ai/api/v1/chat/completions")
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = client.newCall(request).execute()
                val responseBody = response.body
                val jsonResponse = responseBody?.string()

                if (!response.isSuccessful || jsonResponse.isNullOrEmpty()) {
                    withContext(Dispatchers.Main) {
                        addMessage("LLM Error: ${response.code} ${response.message}", isUser = false)
                    }
                    return@launch
                }

                try {
                    val result = JSONObject(jsonResponse)
                        .getJSONArray("choices")
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                    withContext(Dispatchers.Main) {
                        addMessage(result.trim(), isUser = false)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        addMessage("Unexpected response format:\n$jsonResponse", isUser = false)
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    addMessage("Network Error: ${e.localizedMessage}", isUser = false)
                }
            }
        }
    }
}