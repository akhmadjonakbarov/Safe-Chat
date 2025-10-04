package uz.akbarovdev.safechat.presentations.home.domain.repositories

import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom

interface ChatRoomRepository {
    suspend fun getChatRooms(): List<ChatRoom>
}