package uz.akbarovdev.safechat.presentations.home.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.akbarovdev.safechat.presentations.home.domain.models.ChatRoomResponse
import uz.akbarovdev.safechat.presentations.home.domain.models.CreatedChatRoomId

interface ChatRoomApi {
    @GET("chatrooms")
    suspend fun getChatRooms(): ChatRoomResponse

    @POST("chatrooms/create/{receiver_id}")
    suspend fun createChatRoom(
        @Path("receiver_id") receiverId: Int
    ): Response<CreatedChatRoomId>
}