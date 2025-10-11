package uz.akbarovdev.safechat.presentations.auth.data.repository

import android.util.Log
import uz.akbarovdev.safechat.core.wrapper.Handler
import uz.akbarovdev.safechat.presentations.auth.data.remote.AuthApi
import uz.akbarovdev.safechat.presentations.auth.domain.models.LoginRequest
import uz.akbarovdev.safechat.presentations.auth.domain.models.RegisterRequest
import uz.akbarovdev.safechat.presentations.auth.domain.models.UserResponse
import uz.akbarovdev.safechat.presentations.auth.domain.repository.AuthRepository
import uz.akbarovdev.safechat.presentations.auth.domain.models.User

class AuthRepositoryIml(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(loginReq: LoginRequest): Handler<User> {
        return try {
            // Log the request
            Log.d("AuthRepository", "Login request: $loginReq")

            val response: UserResponse = authApi.login(loginReq) // Call API

            // Log the response
            Log.d("AuthRepository", "Login response: $response")

            Handler.Success(response.user)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Login error", e)
            Handler.Error(
                message = e.localizedMessage ?: "Unknown error occurred",
                code = null
            )
        }
    }

    override suspend fun register(registerReq: RegisterRequest): Handler<User> {
        return try {
            // Log the request
            Log.d("AuthRepository", "Register request: $registerReq")

            val response: UserResponse = authApi.register(registerReq)

            // Log the response
            Log.d("AuthRepository", "Register response: $response")

            Handler.Success(response.user)
        } catch (e: Exception) {
            Log.e("AuthRepository", "Register error", e)
            Handler.Error(
                message = e.localizedMessage ?: "Unknown error occurred",
                code = null
            )
        }
    }
}
