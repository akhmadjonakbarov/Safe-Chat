package uz.akbarovdev.safechat.presentations.auth.data.remote

import retrofit2.Response
import retrofit2.http.GET
import uz.akbarovdev.safechat.presentations.auth.domain.models.UserResponse

interface AuthApi {

    @GET("/auth/login")
    suspend fun login(): Response<UserResponse>

    @GET("/auth/register")
    suspend fun register(): Response<UserResponse>
}