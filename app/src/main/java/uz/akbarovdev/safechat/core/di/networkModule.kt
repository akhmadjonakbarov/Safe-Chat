package uz.akbarovdev.safechat.core.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import uz.akbarovdev.safechat.core.retrofits.SafeChatClient
import uz.akbarovdev.safechat.presentations.auth.data.remote.AuthApi
import uz.akbarovdev.safechat.presentations.chat.data.remote.MessageApi
import uz.akbarovdev.safechat.presentations.home.data.remote.ChatRoomApi


val networkModule = module {

    single(named("SafeChatClient")) {
        SafeChatClient.getClient(
            applicationContext = get()
        )
    }

    single<AuthApi> {
        get<Retrofit>(named("SafeChatClient")).create(AuthApi::class.java)
    }
    single<ChatRoomApi> {
        get<Retrofit>(named("SafeChatClient")).create(ChatRoomApi::class.java)
    }

    single<MessageApi> {
        get<Retrofit>(named("SafeChatClient")).create(MessageApi::class.java)
    }

}
