package uz.akbarovdev.safechat.presentations.home.domain.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import uz.akbarovdev.safechat.presentations.auth.domain.models.User

@Serializable
data class ChatRoomResponse(
    val chatrooms: List<ChatRoom>
)

@Serializable
data class ChatRoom(
    val id: Int,
    @SerializedName("last_message") val lastMessage: String? = null,
    @SerializedName("un_read_message_count") val unReadMessageCount: Int = 0,
    val receiver: ChatRoomUser
)


@Serializable
data class CreatedChatRoomId(
    val chatroomId: Int
)


