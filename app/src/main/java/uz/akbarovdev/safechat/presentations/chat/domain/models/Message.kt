package uz.akbarovdev.safechat.presentations.chat.domain.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val message: String,
    @SerializedName("sender_id")
    val senderId: Int,
    @SerializedName("room_id")
    val roomId: Int,
    @SerializedName("file_url")
    val fileUrl: String? = null,
    val type: String,
    @SerialName("is_read")
    val isRead: Boolean
)

@Serializable
data class MessageCache(
    val id: Int,
    @SerialName("room_id") val roomId: Int,
    @SerialName("sender_id") val senderId: Int,
    @SerialName("text") val message: String,
    @SerialName("file_url")
    val fileUrl: String? = null,
    @SerialName("type")
    val type: String,
    @SerialName("file_name")
    val fileName: String? = null,
    @SerialName("is_read")
    val isRead: Boolean,
)


@Serializable
data class MessageRequest(
    val message: String,
    @SerialName("sender_id")
    val senderId: Int,
    val type: String = "text",
)
