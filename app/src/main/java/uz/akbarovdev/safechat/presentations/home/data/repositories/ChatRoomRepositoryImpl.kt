package uz.akbarovdev.safechat.presentations.home.data.repositories

import kotlinx.coroutines.flow.Flow
import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.home.data.remote.ChatRoomApi
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom
import uz.akbarovdev.safechat.presentations.home.domain.repositories.ChatRoomRepository

import kotlinx.coroutines.flow.flow

class ChatRoomRepositoryImpl(
    private val chatRoomApi: ChatRoomApi
) : ChatRoomRepository {

    override suspend fun getChatRooms(): Handler<Flow<List<ChatRoom>>> {
        return try {
            val flow = flow {
                val response = chatRoomApi.getChatRooms()
                emit(response.chatrooms)
            }
            Handler.Success(flow)
        } catch (e: Exception) {
            Handler.Error("Failed to fetch chat rooms: ${e.localizedMessage}")
        }
    }

    override suspend fun createChatRoom(receiverId: Int): Int {
        val response = chatRoomApi.createChatRoom(receiverId)
        return if (response.isSuccessful) {
            response.body()?.chatroomId ?: -1
        } else {
            -1
        }
    }
}
