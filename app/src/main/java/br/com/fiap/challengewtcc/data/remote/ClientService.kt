package br.com.fiap.challengewtcc.data.remote

import br.com.fiap.challengewtcc.data.remote.request.CreateClientRequest
import br.com.fiap.challengewtcc.data.remote.response.ClientResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ClientService {

    @GET("clients")
    suspend fun getClients(): Response<List<ClientResponse>>

    @GET("clients/{id}")
    suspend fun getClientById(
        @Path("id") id: String
    ): Response<ClientResponse>

    @POST("clients")
    suspend fun createClient(
        @Body request: CreateClientRequest
    ): Response<ClientResponse>
}