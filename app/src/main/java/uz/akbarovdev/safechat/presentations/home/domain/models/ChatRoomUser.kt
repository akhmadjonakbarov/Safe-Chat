package uz.akbarovdev.safechat.presentations.home.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class ChatRoomUser(
    val id: Int,
    val email: String,
)