package uz.akbarovdev.safechat.presentations.auth.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String, val password: String,
)
