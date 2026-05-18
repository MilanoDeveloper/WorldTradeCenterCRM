package br.com.fiap.challengewtcc.data.remote.response

import br.com.fiap.challengewtcc.data.UserRole

data class AuthResponse(
    val token: String,
    val user: AuthUserResponse
)

data class AuthUserResponse(
    val id: String,
    val name: String,
    val email: String,
    val role: UserRole
)