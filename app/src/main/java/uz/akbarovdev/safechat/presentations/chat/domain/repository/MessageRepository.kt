package uz.akbarovdev.safechat.presentations.chat.domain.repository

import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message

interface MessageRepository {
    suspend fun getMessages(chatRoomId: Int): Handler<List<Message>>
}