package br.com.fiap.challengewtcc.data.remote

import br.com.fiap.challengewtcc.data.remote.request.SendMessageRequest
import br.com.fiap.challengewtcc.data.remote.response.ChatConversationResponse
import br.com.fiap.challengewtcc.data.remote.response.ChatMessageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ChatService {

    @GET("chat/conversations")
    suspend fun getConversations(): Response<List<ChatConversationResponse>>

    @GET("chat/conversations/{id}/messages")
    suspend fun getMessages(
        @Path("id") conversationId: String
    ): Response<List<ChatMessageResponse>>

    @POST("chat/conversations/{id}/messages")
    suspend fun sendMessage(
        @Path("id") conversationId: String,
        @Body request: SendMessageRequest
    ): Response<ChatMessageResponse>
}