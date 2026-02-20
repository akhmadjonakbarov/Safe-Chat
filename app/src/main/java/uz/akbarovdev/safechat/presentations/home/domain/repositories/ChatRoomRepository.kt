package uz.akbarovdev.safechat.presentations.home.domain.repositories

import kotlinx.coroutines.flow.Flow
import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom

interface ChatRoomRepository {
    suspend fun getChatRooms(): Handler<Flow<List<ChatRoom>>>
    suspend fun createChatRoom(receiverId: Int): Int
}