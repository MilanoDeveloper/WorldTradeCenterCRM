package br.com.fiap.challengewtcc.data.repository

import br.com.fiap.challengewtcc.data.remote.ApiClient
import br.com.fiap.challengewtcc.data.remote.request.LoginRequest
import br.com.fiap.challengewtcc.data.remote.request.RegisterRequest
import br.com.fiap.challengewtcc.data.remote.response.AuthResponse

class AuthRepository {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Result<AuthResponse> {

        return try {
            val response = ApiClient.authService.register(
                RegisterRequest(
                    name = name,
                    email = email,
                    password = password
                )
            )

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Resposta vazia do servidor"))
                }
            } else {
                Result.failure(Exception("Erro ao criar conta"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<AuthResponse> {

        return try {
            val response = ApiClient.authService.login(
                LoginRequest(
                    email = email,
                    password = password
                )
            )

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Resposta vazia do servidor"))
                }
            } else {
                Result.failure(Exception("Erro ao autenticar"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
