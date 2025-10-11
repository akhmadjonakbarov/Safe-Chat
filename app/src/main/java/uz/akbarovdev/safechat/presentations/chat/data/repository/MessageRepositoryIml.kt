package uz.akbarovdev.safechat.presentations.chat.data.repository

import android.util.Log
import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.chat.data.remote.MessageApi
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message
import uz.akbarovdev.safechat.presentations.chat.domain.repository.MessageRepository

class MessageRepositoryIml(
    val messageApi: MessageApi
) : MessageRepository {
    override suspend fun getMessages(chatRoomId: Int): Handler<List<Message>> {
        return try {
            val response = messageApi.getMessages(chatRoomId)

            Log.d("AuthRepository", "Login response: $response")

            Handler.Success(response)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login error", e)
            Handler.Error(
                message = e.localizedMessage ?: "Unknown error occurred",
                code = null
            )
        }
    }
}