package br.com.fiap.challengewtcc.data.remote

import br.com.fiap.challengewtcc.data.models.User
import retrofit2.http.GET

interface UserService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
