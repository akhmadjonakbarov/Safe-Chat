package uz.akbarovdev.safechat.presentations.chat.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import uz.akbarovdev.safechat.presentations.chat.data.repository.MessageRepositoryIml
import uz.akbarovdev.safechat.presentations.chat.domain.repository.MessageRepository
import uz.akbarovdev.safechat.presentations.chat.presentation.view_model.ChatViewModel
import uz.akbarovdev.safechat.presentations.home.data.web_socket.ChatWebSocketClient

val chatModule = module {
    singleOf(::MessageRepositoryIml) bind MessageRepository::class
    singleOf(::ChatWebSocketClient)
    viewModelOf(::ChatViewModel)
}