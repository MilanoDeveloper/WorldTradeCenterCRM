package br.com.fiap.challengewtcc.data.models

import java.time.Instant

data class Message(
    val id: String,
    val conversationId: String,
    val fromMe: Boolean,
    val text: String,
    val timestamp: Instant
)