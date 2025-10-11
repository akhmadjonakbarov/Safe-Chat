package uz.akbarovdev.safechat.presentations.auth.di


import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import uz.akbarovdev.safechat.presentations.auth.data.repository.AuthRepositoryIml
import uz.akbarovdev.safechat.presentations.auth.domain.repository.AuthRepository
import uz.akbarovdev.safechat.presentations.auth.presentation.view_model.AuthViewModel

val authModule = module {
    singleOf(::AuthRepositoryIml) bind AuthRepository::class
    viewModel {
        AuthViewModel(
            applicationContext = get(), authRepository = get()
        )
    }
}