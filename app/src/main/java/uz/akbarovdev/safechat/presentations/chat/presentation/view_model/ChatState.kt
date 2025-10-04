package uz.akbarovdev.safechat.presentations.chat.presentation.view_model

import uz.akbarovdev.safechat.presentations.chat.domain.models.Message

data class ChatState(
    val messages: List<Message> = emptyList()
)