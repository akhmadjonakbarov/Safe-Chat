package uz.akbarovdev.safechat.presentations.home.data.web_socket

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okio.ByteString
import org.json.JSONObject
import uz.akbarovdev.safechat.core.enums.PreferenceKeys
import uz.akbarovdev.safechat.core.extension.sharedPreferences
import java.io.File

class ChatWebSocketClient {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private var listener: ChatWebSocketListener? = null

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _incomingMessages = MutableSharedFlow<String>(extraBufferCapacity = 64)
    val incomingMessages: SharedFlow<String> = _incomingMessages.asSharedFlow()

    fun connect(applicationContext: Context, chatRoomId: Int) {
        val token: String by applicationContext.sharedPreferences(PreferenceKeys.Token.name)
        if (_isConnected.value) return


        val request = Request.Builder()
            .url("ws://10.0.2.2:8000/api/v1/chatrooms/ws/chatrooms/$chatRoomId")
            .addHeader("Authorization", "Bearer $token")
            .build()

        listener = ChatWebSocketListener { message ->

            CoroutineScope(Dispatchers.IO).launch {
                _incomingMessages.emit(message)
            }
        }

        webSocket = client.newWebSocket(request, listener!!)
        _isConnected.value = true
        Log.d("WebSocketClient", "Connecting to chat room $chatRoomId...")
    }

    fun sendMessage(message: String) {
        if (_isConnected.value) {
            webSocket?.send(message)
        } else {
            Log.e("WebSocketClient", "Cannot send: not connected")
        }
    }

    suspend fun sendFile(file: File, senderId: Int, roomId: Int) {
        withContext(Dispatchers.IO) {
            val meta = JSONObject().apply {
                put("type", "image")
                put("filename", file.name)
                put("size", file.length())
                put("sender_id", senderId)
                put("room_id", roomId)
            }
            webSocket?.send(meta.toString())
            val fileBytes = file.readBytes()
            val byteString = ByteString.of(*fileBytes)
            webSocket?.send(byteString)

            Log.d("WebSocketClient", "✅ Sent file: ${file.name} (${file.length()} bytes)")
        }
    }

    fun close() {
        webSocket?.close(1000, "User left the chat")
        _isConnected.value = false
        Log.d("WebSocketClient", "WebSocket closed ❌")
    }
}
