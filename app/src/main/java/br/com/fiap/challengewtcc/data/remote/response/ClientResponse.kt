package br.com.fiap.challengewtcc.data.remote.response

data class ClientResponse(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val company: String,
    val status: String,
    val tags: List<String>
)