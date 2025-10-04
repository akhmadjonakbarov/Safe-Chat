package uz.akbarovdev.safechat.presentations.home.data.remote

import retrofit2.Response
import retrofit2.http.GET
import uz.akbarovdev.safechat.presentations.home.domain.models.UserListResponse

interface UserApi {
    @GET("/users")
    suspend fun getUsers(): Response<UserListResponse>

    @GET("/users?q")
    suspend fun searchUsers(): Response<Unit>
}