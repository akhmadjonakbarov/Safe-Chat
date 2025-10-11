package uz.akbarovdev.safechat.presentations.home.data.web_socket

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class ChatWebSocketListener(
    private val onMessageReceived: (String) -> Unit
) : WebSocketListener() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d("WebSocket", "Connected ✅")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d("WebSocket", "📨 Message received: $text")
        scope.launch { onMessageReceived(text) }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("WebSocket", "Closed: $reason")
        scope.cancel()
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.e("WebSocket", "Error: ${t.message}")
        scope.cancel()
    }
}
