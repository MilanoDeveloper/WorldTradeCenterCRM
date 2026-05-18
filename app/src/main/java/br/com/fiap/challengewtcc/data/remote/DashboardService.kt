package br.com.fiap.challengewtcc.data.remote

import br.com.fiap.challengewtcc.data.remote.response.DashboardResponse
import retrofit2.Response
import retrofit2.http.GET

interface DashboardService {

    @GET("dashboard")
    suspend fun getDashboard(): Response<DashboardResponse>
}