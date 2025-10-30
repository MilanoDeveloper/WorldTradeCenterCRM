/*
package br.com.fiap.challengewtcc.ui.theme.screens.crm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.MockRepository
import br.com.fiap.challengewtcc.data.models.Client
import br.com.fiap.challengewtcc.viewmodel.CRMViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class CRMViewModel : ViewModel() {
    val clients: StateFlow<List<Client>> = MockRepository.clients
    fun addNote(clientId: String, note: String) = MockRepository.addNote(clientId, note)
    fun updateTags(clientId: String, tags: List<String>) = MockRepository.updateTags(clientId, tags)
    fun updateStatus(clientId: String, status: String) = MockRepository.updateStatus(clientId, status)
    fun clientById(id: String): StateFlow<Client?> =
        CRMViewModel.clients.map { list -> list.find { it.id == id } }
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)
}
*/
