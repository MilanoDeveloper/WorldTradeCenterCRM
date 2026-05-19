package br.com.fiap.challengewtcc.data.repository

import android.util.Log
import br.com.fiap.challengewtcc.data.remote.ApiClient
import br.com.fiap.challengewtcc.data.remote.response.NotificationResponse

class NotificationRepository {

    suspend fun getNotifications(
        userId: String
    ): Result<List<NotificationResponse>> {

        return try {

            val response =
                ApiClient.notificationService.getNotifications(userId)

            Log.d("NOTIFICATION_API", response.body().toString())

            if (response.isSuccessful) {
                Result.success(response.body().orEmpty())
            } else {
                Result.failure(
                    Exception("Erro ao buscar notificações")
                )
            }

        } catch (e: Exception) {

            Log.e("NOTIFICATION_API", e.message ?: "erro")

            Result.failure(e)
        }
    }

    suspend fun getUnreadCount(
        userId: String
    ): Result<Int> {

        return try {

            val response = ApiClient.notificationService
                .getUnreadCount(userId)

            if (response.isSuccessful) {

                Result.success(
                    response.body() ?: 0
                )

            } else {

                Result.failure(
                    Exception("Erro ao buscar contador")
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }

    suspend fun markAsRead(
        id: String
    ): Result<Unit> {

        return try {

            val response = ApiClient.notificationService
                .markAsRead(id)

            if (response.isSuccessful) {

                Result.success(Unit)

            } else {

                Result.failure(
                    Exception("Erro ao marcar notificação")
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}