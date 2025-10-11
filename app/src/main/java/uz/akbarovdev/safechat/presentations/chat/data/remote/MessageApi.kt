package uz.akbarovdev.safechat.presentations.chat.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import uz.akbarovdev.safechat.presentations.chat.domain.models.Message

interface MessageApi {
    @GET("chatrooms/{chatroom_id}/messages")
    suspend fun getMessages(
        @Path("chatroom_id") chatRoomId: Int
    ): List<Message>
}
