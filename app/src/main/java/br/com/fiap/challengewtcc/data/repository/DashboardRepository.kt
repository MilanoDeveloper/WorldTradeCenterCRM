package br.com.fiap.challengewtcc.data.repository

import br.com.fiap.challengewtcc.data.remote.ApiClient
import br.com.fiap.challengewtcc.data.remote.response.DashboardResponse

class DashboardRepository {

    suspend fun getDashboard(): Result<DashboardResponse> {
        return try {
            val response = ApiClient.dashboardService.getDashboard()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure<DashboardResponse>(
                        Exception("Erro ao carregar dashboard")
                    )
                }
            } else {
                Result.failure<DashboardResponse>(
                    Exception("Erro ao carregar dashboard")
                )
            }
        } catch (e: Exception) {
            Result.failure<DashboardResponse>(e)
        }
    }
}