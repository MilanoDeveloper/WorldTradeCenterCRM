package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.remote.request.CreateClientRequest
import br.com.fiap.challengewtcc.data.remote.response.ClientResponse
import br.com.fiap.challengewtcc.data.repository.ClientRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.onFailure

data class ClientState(
    val loading: Boolean = false,
    val clients: List<ClientResponse> = emptyList(),
    val selectedClient: ClientResponse? = null,
    val error: String? = null
)

class ClientViewModel : ViewModel() {

    private val repository = ClientRepository()

    private val _state = MutableStateFlow(ClientState())
    val state: StateFlow<ClientState> = _state.asStateFlow()

    fun loadClients() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            repository.getClients()
                .onSuccess { clients ->
                    _state.value = _state.value.copy(
                        loading = false,
                        clients = clients
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = exception.message
                    )
                }
        }
    }

    fun loadClientById(id: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            repository.getClientById(id)
                .onSuccess { client ->
                    _state.value = _state.value.copy(
                        loading = false,
                        selectedClient = client
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = exception.message
                    )
                }
        }
    }

    fun createClient(request: CreateClientRequest) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            repository.createClient(request)
                .onSuccess {
                    loadClients()
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = exception.message
                    )
                }
        }
    }
}