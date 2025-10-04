package uz.akbarovdev.safechat.presentations.auth.data.repository

import uz.akbarovdev.safechat.presentations.auth.domain.models.UserResponse
import uz.akbarovdev.safechat.presentations.auth.domain.repository.AuthRepository

class AuthRepositoryIml: AuthRepository {
    override suspend fun login(): Result<UserResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun register(): Result<UserResponse> {
        TODO("Not yet implemented")
    }
}