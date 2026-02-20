package uz.akbarovdev.safechat.presentations.home.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.akbarovdev.safechat.presentations.home.domain.models.User
import uz.akbarovdev.safechat.presentations.home.domain.models.UserListResponse

interface UserApi {
    @GET("/users")
    suspend fun getUsers(): Response<UserListResponse>

    @GET("users/search")
    suspend fun searchUsers(
        @Query("username") query: String
    ): Response<List<User>>
}