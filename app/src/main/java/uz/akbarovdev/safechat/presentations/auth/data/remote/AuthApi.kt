package uz.akbarovdev.safechat.presentations.auth.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import uz.akbarovdev.safechat.presentations.auth.domain.models.LoginRequest
import uz.akbarovdev.safechat.presentations.auth.domain.models.RegisterRequest
import uz.akbarovdev.safechat.presentations.auth.domain.models.UserResponse

interface AuthApi {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): UserResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerReq: RegisterRequest
    ): UserResponse
}