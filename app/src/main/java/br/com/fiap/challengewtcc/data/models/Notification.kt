package br.com.fiap.challengewtcc.data.models

data class AppNotification(
    val id: String,
    val title: String,
    val body: String,
    val read: Boolean = false
)