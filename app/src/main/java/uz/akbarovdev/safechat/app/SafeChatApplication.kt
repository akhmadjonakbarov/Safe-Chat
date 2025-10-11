package uz.akbarovdev.safechat.app

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uz.akbarovdev.safechat.app.di.appModule
import uz.akbarovdev.safechat.core.di.networkModule
import uz.akbarovdev.safechat.presentations.auth.di.authModule
import uz.akbarovdev.safechat.presentations.chat.di.chatModule
import uz.akbarovdev.safechat.presentations.home.di.homeModule

class SafeChatApplication : Application() {
    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SafeChatApplication)
            modules(

                appModule,
                networkModule,
                authModule,
                homeModule,
                chatModule,
            )
        }
    }
}