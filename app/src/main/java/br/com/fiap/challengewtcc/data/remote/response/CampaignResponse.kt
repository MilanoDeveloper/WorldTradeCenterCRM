package br.com.fiap.challengewtcc.data.remote.response

data class CampaignResponse(
    val id: String,
    val title: String,
    val description: String,
    val status: String,
    val targetAudience: String,
    val startDate: String,
    val endDate: String
)