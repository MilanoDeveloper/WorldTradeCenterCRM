package br.com.fiap.challengewtcc.data.remote.response

data class NotificationResponse(
    val id: String,
    val title: String,
    val message: String,
    val read: Boolean
)