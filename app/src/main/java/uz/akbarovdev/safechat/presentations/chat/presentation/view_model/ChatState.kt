package uz.akbarovdev.safechat.presentations.chat.presentation.view_model

import uz.akbarovdev.safechat.core.enums.MessageType
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomUser
import java.io.File

data class ChatState(
    val messageText: String = "",
    val messages: List<Message> = emptyList(),
    val receiver: ChatRoomUser? = null,
    val messageType: MessageType = MessageType.Image,
    val selectFile: File? = null,
)