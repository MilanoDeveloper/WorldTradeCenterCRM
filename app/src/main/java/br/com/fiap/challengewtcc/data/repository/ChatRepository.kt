package br.com.fiap.challengewtcc.data.repository

import br.com.fiap.challengewtcc.data.remote.ApiClient
import br.com.fiap.challengewtcc.data.remote.request.SendMessageRequest
import br.com.fiap.challengewtcc.data.remote.response.MessageResponse

class ChatRepository {

    suspend fun getMessages(
        user1: String,
        user2: String
    ): Result<List<MessageResponse>> {

        return try {

            val response = ApiClient.chatService
                .getMessages(user1, user2)

            if (response.isSuccessful) {

                Result.success(
                    response.body().orEmpty()
                )

            } else {

                Result.failure(
                    Exception("Erro ao buscar mensagens")
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun sendMessage(
        request: SendMessageRequest
    ): Result<MessageResponse> {

        return try {

            val response = ApiClient.chatService
                .sendMessage(request)

            if (response.isSuccessful) {

                response.body()?.let {

                    Result.success(it)

                } ?: Result.failure(
                    Exception("Mensagem vazia")
                )

            } else {

                Result.failure(
                    Exception("Erro ao enviar mensagem")
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}