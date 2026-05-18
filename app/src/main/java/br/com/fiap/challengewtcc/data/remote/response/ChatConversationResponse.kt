package br.com.fiap.challengewtcc.data.remote.response

data class ChatConversationResponse(
    val id: String,
    val title: String,
    val lastMessage: String,
    val updatedAt: String
)