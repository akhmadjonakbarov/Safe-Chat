package uz.akbarovdev.safechat.presentations.home.data.repositories

import uz.akbarovdev.safechat.presentations.home.data.remote.ChatRoomApi
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom
import uz.akbarovdev.safechat.presentations.home.domain.repositories.ChatRoomRepository

class ChatRoomRepositoryIml(
    private val chatRoomApi: ChatRoomApi
) : ChatRoomRepository {
    override suspend fun getChatRooms(): List<ChatRoom> {
        TODO("Not yet implemented")
    }
}