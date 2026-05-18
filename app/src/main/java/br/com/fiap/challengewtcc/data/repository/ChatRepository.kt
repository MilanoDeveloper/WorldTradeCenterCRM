package br.com.fiap.challengewtcc.data.repository

import br.com.fiap.challengewtcc.data.remote.ApiClient
import br.com.fiap.challengewtcc.data.remote.request.SendMessageRequest
import br.com.fiap.challengewtcc.data.remote.response.ChatConversationResponse
import br.com.fiap.challengewtcc.data.remote.response.ChatMessageResponse

class ChatRepository {

    suspend fun getConversations(): Result<List<ChatConversationResponse>> {
        return try {
            val response = ApiClient.chatService.getConversations()

            if (response.isSuccessful) {
                Result.success(response.body().orEmpty())
            } else {
                Result.failure<List<ChatConversationResponse>>(
                    Exception("Erro ao buscar conversas")
                )
            }
        } catch (e: Exception) {
            Result.failure<List<ChatConversationResponse>>(e)
        }
    }

    suspend fun getMessages(
        conversationId: String
    ): Result<List<ChatMessageResponse>> {
        return try {
            val response = ApiClient.chatService.getMessages(conversationId)

            if (response.isSuccessful) {
                Result.success(response.body().orEmpty())
            } else {
                Result.failure<List<ChatMessageResponse>>(
                    Exception("Erro ao buscar mensagens")
                )
            }
        } catch (e: Exception) {
            Result.failure<List<ChatMessageResponse>>(e)
        }
    }

    suspend fun sendMessage(
        conversationId: String,
        request: SendMessageRequest
    ): Result<ChatMessageResponse> {
        return try {
            val response = ApiClient.chatService.sendMessage(
                conversationId,
                request
            )

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure<ChatMessageResponse>(
                        Exception("Erro ao enviar mensagem")
                    )
                }
            } else {
                Result.failure<ChatMessageResponse>(
                    Exception("Erro ao enviar mensagem")
                )
            }
        } catch (e: Exception) {
            Result.failure<ChatMessageResponse>(e)
        }
    }
}