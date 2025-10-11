package uz.akbarovdev.safechat.presentations.home.presenation.view_model

import uz.akbarovdev.safechat.presentations.auth.domain.models.User
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoom

data class HomeState(
    val loading: Boolean = false,
    val chatRooms: List<ChatRoom> = emptyList(),
    val users: List<User> = emptyList(),
    val query: String = "",

    )