package br.com.fiap.challengewtcc.viewmodel

data class CRMFilters(
    val search: String = "",
    val tags: Set<String> = emptySet(),
    val status: String = "all",
    val score: String = "all",
    val segment: String? = null
)
