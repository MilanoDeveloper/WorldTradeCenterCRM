package br.com.fiap.challengewtcc.data.remote

import br.com.fiap.challengewtcc.data.remote.response.NotificationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface NotificationService {

    @GET("notifications/{userId}")
    suspend fun getNotifications(
        @Path("userId") userId: String
    ): Response<List<NotificationResponse>>

    @GET("notifications/{userId}/count")
    suspend fun getUnreadCount(
        @Path("userId") userId: String
    ): Response<Int>

    @PATCH("notifications/{id}/read")
    suspend fun markAsRead(
        @Path("id") id: String
    ): Response<Unit>
}