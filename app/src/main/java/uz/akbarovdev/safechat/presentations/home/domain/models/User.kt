package uz.akbarovdev.safechat.presentations.home.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val username: String,
    val email: String,

    )