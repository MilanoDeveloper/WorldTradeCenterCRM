package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.remote.response.DashboardResponse
import br.com.fiap.challengewtcc.data.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DashboardState(
    val loading: Boolean = false,
    val dashboard: DashboardResponse? = null,
    val error: String? = null
)

class DashboardViewModel : ViewModel() {

    private val repository = DashboardRepository()

    private val _state = MutableStateFlow(DashboardState())
    val state: StateFlow<DashboardState> = _state.asStateFlow()

    fun loadDashboard() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            repository.getDashboard()
                .onSuccess { dashboard ->
                    _state.value = _state.value.copy(
                        loading = false,
                        dashboard = dashboard
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
}