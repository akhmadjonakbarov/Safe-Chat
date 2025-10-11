package uz.akbarovdev.safechat.presentations.chat.presentation.view_model

import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomUser
import java.io.File

sealed interface ChatAction {
    data class OnConnectChat(val chatRoomId: Int, val receiver: ChatRoomUser) : ChatAction
    data object OnSendMessage : ChatAction
    data object OnSendFile : ChatAction
    data class OnSelectFile(val file: File) : ChatAction
    data class OnInputMessage(val message: String) : ChatAction
    data object OnLoadMessages : ChatAction
}