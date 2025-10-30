package br.com.fiap.challengewtcc.data.models

data class Client(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String,
    val score: Int,
    val status: String,
    val tags: List<String>,
    val lastPurchase: String,
    val totalSpent: Int,
    val notes: List<String>,
    val segment: String
)
