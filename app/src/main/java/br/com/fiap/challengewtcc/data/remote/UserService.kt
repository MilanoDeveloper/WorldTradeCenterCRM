package br.com.fiap.challengewtcc.data.remote

import br.com.fiap.challengewtcc.data.remote.response.AuthUserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("users")
    suspend fun getUsers(): Response<List<AuthUserResponse>>
}
