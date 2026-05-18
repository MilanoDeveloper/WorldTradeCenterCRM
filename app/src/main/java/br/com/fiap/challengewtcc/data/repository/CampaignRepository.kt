package br.com.fiap.challengewtcc.data.repository

import br.com.fiap.challengewtcc.data.remote.ApiClient
import br.com.fiap.challengewtcc.data.remote.request.CreateCampaignRequest
import br.com.fiap.challengewtcc.data.remote.response.CampaignResponse

class CampaignRepository {

    suspend fun getCampaigns(): Result<List<CampaignResponse>> {
        return try {
            val response = ApiClient.campaignService.getCampaigns()

            if (response.isSuccessful) {
                Result.success(response.body().orEmpty())
            } else {
                Result.failure<List<CampaignResponse>>(
                    Exception("Erro ao buscar campanhas")
                )
            }
        } catch (e: Exception) {
            Result.failure<List<CampaignResponse>>(e)
        }
    }

    suspend fun getCampaignById(id: String): Result<CampaignResponse> {
        return try {
            val response = ApiClient.campaignService.getCampaignById(id)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure<CampaignResponse>(
                        Exception("Campanha não encontrada")
                    )
                }
            } else {
                Result.failure<CampaignResponse>(
                    Exception("Erro ao buscar campanha")
                )
            }
        } catch (e: Exception) {
            Result.failure<CampaignResponse>(e)
        }
    }

    suspend fun createCampaign(request: CreateCampaignRequest): Result<CampaignResponse> {
        return try {
            val response = ApiClient.campaignService.createCampaign(request)

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure<CampaignResponse>(
                        Exception("Erro ao criar campanha")
                    )
                }
            } else {
                Result.failure<CampaignResponse>(
                    Exception("Erro ao criar campanha")
                )
            }
        } catch (e: Exception) {
            Result.failure<CampaignResponse>(e)
        }
    }
}