package uz.akbarovdev.safechat.presentations.chat.domain.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val message: String, @SerializedName("sender_id") val senderId: Int
)