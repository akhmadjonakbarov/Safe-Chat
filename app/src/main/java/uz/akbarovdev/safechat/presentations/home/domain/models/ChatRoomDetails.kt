package uz.akbarovdev.safechat.presentations.home.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class ChatRoomDetails(
    val roomId: Int,
    val receiverId: Int,
    val receiverName: String
)