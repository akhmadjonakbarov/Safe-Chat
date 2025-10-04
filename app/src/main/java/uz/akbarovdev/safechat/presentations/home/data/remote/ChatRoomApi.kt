package uz.akbarovdev.safechat.presentations.home.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface ChatRoomApi {
    @GET("/chatrooms")
    suspend fun getChatRooms(): Response<Unit>
}