package uz.akbarovdev.safechat.app.navigation

import kotlinx.serialization.Serializable

sealed interface Routes {
    @Serializable
    data object Home : Routes

    @Serializable
    data object Auth : Routes

    @Serializable
    data object Chat : Routes

    @Serializable
    data object Splash : Routes
}