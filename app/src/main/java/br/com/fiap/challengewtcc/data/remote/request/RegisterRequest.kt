package br.com.fiap.challengewtcc.data.remote.request

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)
