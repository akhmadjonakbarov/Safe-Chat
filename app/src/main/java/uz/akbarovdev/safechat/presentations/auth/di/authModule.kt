package uz.akbarovdev.safechat.presentations.auth.di


import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import uz.akbarovdev.safechat.presentations.auth.presentation.view_model.AuthViewModel

val authModule = module {
    viewModel { AuthViewModel() }
}