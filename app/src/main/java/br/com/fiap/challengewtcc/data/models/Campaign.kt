package br.com.fiap.challengewtcc.data.models

data class Campaign(
    val id: String,
    val name: String,
    val status: String, // ex.: Ativa, Pausada, Encerrada
    val sent: Int,
    val opened: Int,
    val clicked: Int
)