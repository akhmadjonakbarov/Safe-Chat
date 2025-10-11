package uz.akbarovdev.safechat.presentations.auth.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,
    val token: String
)