package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.challengewtcc.data.MockRepository
import br.com.fiap.challengewtcc.data.models.Client
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class CRMViewModel : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main + Job())
    val clients = MockRepository.clients
    private val _filters = MutableStateFlow(CRMFilters())
    val filters: StateFlow<CRMFilters> = _filters
    val filteredClients = combine(clients, _filters) { list, f ->
        var r = list
        if (f.search.isNotBlank()) {
            val q = f.search.lowercase()
            r = r.filter { it.name.lowercase().contains(q) || it.email.lowercase().contains(q) || it.phone.contains(f.search) }
        }
        if (f.tags.isNotEmpty()) {
            r = r.filter { c -> f.tags.any { it in c.tags } }
        }
        if (f.status != "all") {
            r = r.filter { it.status == f.status }
        }
        if (f.score != "all") {
            r = when (f.score) {
                "high" -> r.filter { it.score >= 80 }
                "medium" -> r.filter { it.score in 50..79 }
                "low" -> r.filter { it.score < 50 }
                else -> r
            }
        }
        if (f.segment != null) {
            r = r.filter { it.segment == f.segment }
        }
        r
    }.stateIn(scope, kotlinx.coroutines.flow.SharingStarted.Eagerly, emptyList())

    private val _selected = MutableStateFlow<Client?>(null)
    val selected: StateFlow<Client?> = _selected

    fun setSearch(q: String) = _filters.update { it.copy(search = q) }
    fun toggleTag(tag: String) = _filters.update { it.copy(tags = it.tags.toMutableSet().apply { if (contains(tag)) remove(tag) else add(tag) }.toSet()) }
    fun setStatus(s: String) = _filters.update { it.copy(status = s) }
    fun setScore(s: String) = _filters.update { it.copy(score = s) }
    fun setSegment(seg: String?) = _filters.update { it.copy(segment = seg) }
    fun clearFilters() = _filters.update { CRMFilters() }

    fun addNote(clientId: String, note: String) = MockRepository.addNote(clientId, note)
    fun updateClientTags(clientId: String, tags: List<String>) = MockRepository.updateTags(clientId, tags)
    fun updateClientStatus(clientId: String, status: String) = MockRepository.updateStatus(clientId, status)

    fun selectClient(c: Client) { _selected.value = c }
    fun clearSelection() { _selected.value = null }
}
