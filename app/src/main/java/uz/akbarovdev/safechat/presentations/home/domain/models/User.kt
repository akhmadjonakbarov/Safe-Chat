package uz.akbarovdev.safechat.presentations.home.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class User(
    val username: String,
    val email: String
)
