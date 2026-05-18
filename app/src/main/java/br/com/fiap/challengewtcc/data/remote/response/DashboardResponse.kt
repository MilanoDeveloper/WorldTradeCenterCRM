package br.com.fiap.challengewtcc.data.remote.response

data class DashboardResponse(
    val totalClients: Int,
    val activeCampaigns: Int,
    val totalNotifications: Int,
    val totalMessages: Int,
    val recentClients: List<DashboardClientResponse>,
    val recentNotifications: List<DashboardNotificationResponse>
)

data class DashboardClientResponse(
    val id: String,
    val name: String,
    val company: String
)

data class DashboardNotificationResponse(
    val id: String,
    val title: String,
    val createdAt: String
)