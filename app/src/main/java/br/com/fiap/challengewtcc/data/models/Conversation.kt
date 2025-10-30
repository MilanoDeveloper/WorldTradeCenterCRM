package br.com.fiap.challengewtcc.data.models

data class Conversation(
    val id: String,
    val contactName: String,
    val lastMessagePreview: String,
    val unread: Int = 0
)