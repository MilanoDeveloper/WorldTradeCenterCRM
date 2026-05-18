package br.com.fiap.challengewtcc.data.remote.request

data class SendMessageRequest(
    val senderId: String,
    val message: String
)