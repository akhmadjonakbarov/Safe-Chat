package uz.akbarovdev.safechat.presentations.home.domain.models

data class ChatRoom(
    val name: String,
    val users: List<User>
)
