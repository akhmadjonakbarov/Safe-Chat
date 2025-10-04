package uz.akbarovdev.safechat.app.di

import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import uz.akbarovdev.safechat.app.SafeChatApplication

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as SafeChatApplication).applicationScope
    }
}