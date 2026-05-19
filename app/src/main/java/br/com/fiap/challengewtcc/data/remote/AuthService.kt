package br.com.fiap.challengewtcc.data.remote

import br.com.fiap.challengewtcc.data.remote.request.LoginRequest
import br.com.fiap.challengewtcc.data.remote.request.RegisterRequest
import br.com.fiap.challengewtcc.data.remote.response.AuthResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>
}
