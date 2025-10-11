package uz.akbarovdev.safechat.presentations.home.utils

import uz.akbarovdev.safechat.app.navigation.Routes
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomDetails

fun ChatRoomDetails.toChatRoom(): Routes.Chat {
    return Routes.Chat(
        this.roomId,
        this.receiverId,
        this.receiverName
    )
}

fun Routes.Chat.toChatRoomDetails(): ChatRoomDetails {
    return ChatRoomDetails(
        this.roomId,
        this.receiverId,
        this.receiverName
    )
}