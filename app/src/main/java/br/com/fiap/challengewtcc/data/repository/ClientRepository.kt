package br.com.fiap.challengewtcc.data.repository

import br.com.fiap.challengewtcc.data.remote.ApiClient
import br.com.fiap.challengewtcc.data.remote.request.CreateClientRequest
import br.com.fiap.challengewtcc.data.remote.response.ClientResponse

class ClientRepository {

    suspend fun getClients(): Result<List<ClientResponse>> {
        return try {
            val response = ApiClient.clientService.getClients()

            if (response.isSuccessful) {
                Result.success(response.body().orEmpty())
            } else {
                Result.failure(Exception("Erro ao buscar clientes"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getClientById(id: String): Result<ClientResponse> {
        return try {
            val response = ApiClient.clientService.getClientById(id)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Cliente não encontrado"))
                }
            } else {
                Result.failure(Exception("Erro ao buscar cliente"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createClient(request: CreateClientRequest): Result<ClientResponse> {
        return try {
            val response = ApiClient.clientService.createClient(request)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Erro ao criar cliente"))
                }
            } else {
                Result.failure(Exception("Erro ao criar cliente"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}