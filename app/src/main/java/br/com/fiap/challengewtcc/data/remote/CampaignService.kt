package br.com.fiap.challengewtcc.data.remote

import br.com.fiap.challengewtcc.data.remote.request.CreateCampaignRequest
import br.com.fiap.challengewtcc.data.remote.response.CampaignResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CampaignService {

    @GET("campaigns")
    suspend fun getCampaigns(): Response<List<CampaignResponse>>

    @GET("campaigns/{id}")
    suspend fun getCampaignById(
        @Path("id") id: String
    ): Response<CampaignResponse>

    @POST("campaigns")
    suspend fun createCampaign(
        @Body request: CreateCampaignRequest
    ): Response<CampaignResponse>
}