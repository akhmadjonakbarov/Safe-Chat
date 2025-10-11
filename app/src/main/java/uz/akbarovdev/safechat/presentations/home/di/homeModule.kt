package uz.akbarovdev.safechat.presentations.home.di


import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import uz.akbarovdev.safechat.presentations.home.data.repositories.ChatRoomRepositoryImpl

import uz.akbarovdev.safechat.presentations.home.domain.repositories.ChatRoomRepository
import uz.akbarovdev.safechat.presentations.home.presenation.view_model.HomeViewModel

val homeModule = module {
    single<ChatRoomRepository> { ChatRoomRepositoryImpl(get()) }

    viewModel { HomeViewModel(get()) }
}
