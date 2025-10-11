package uz.akbarovdev.safechat.presentations.home.data.remote

import retrofit2.Response
import retrofit2.http.GET
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomResponse

interface ChatRoomApi {
    @GET("chatrooms")
    suspend fun getChatRooms(): ChatRoomResponse
}