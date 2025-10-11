package uz.akbarovdev.safechat.app.navigation

import android.os.ResultReceiver
import kotlinx.serialization.Serializable
import uz.akbarovdev.safechat.presentations.auth.domain.models.User
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomUser

@Serializable
sealed interface Routes {
    @Serializable
    data object Home : Routes

    @Serializable
    data object Auth : Routes

    @Serializable
    data class Chat(val roomId: Int, val receiverId: Int, val receiverName: String) : Routes

    @Serializable
    data object Splash : Routes
}