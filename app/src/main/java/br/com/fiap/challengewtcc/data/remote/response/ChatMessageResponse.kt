package br.com.fiap.challengewtcc.data.remote.response

data class ChatMessageResponse(
    val id: String,
    val senderId: String,
    val senderName: String,
    val message: String,
    val createdAt: String
)