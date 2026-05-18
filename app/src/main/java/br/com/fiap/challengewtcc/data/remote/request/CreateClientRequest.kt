package br.com.fiap.challengewtcc.data.remote.request

data class CreateClientRequest(
    val name: String,
    val email: String,
    val phone: String,
    val company: String,
    val status: String,
    val tags: List<String>
)