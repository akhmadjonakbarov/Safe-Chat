package uz.akbarovdev.safechat.presentations.home.presenation.view_model


import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom
import uz.akbarovdev.safechat.presentations.home.domain.models.User

data class HomeState(
    val loading: Boolean = false,
    val chatRooms: List<ChatRoom> = emptyList(),
    val users: List<User> = emptyList(),
    val query: String = "",
    val createdChatRoomId: Int = -1

)