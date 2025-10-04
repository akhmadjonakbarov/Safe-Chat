package uz.akbarovdev.safechat.presentations.auth.domain.repository

import uz.akbarovdev.safechat.presentations.auth.domain.models.UserResponse

interface AuthRepository {
    suspend fun login(): Result<UserResponse>
    suspend fun register(): Result<UserResponse>
}