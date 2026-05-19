package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.UserRole
import br.com.fiap.challengewtcc.data.remote.SessionManager
import br.com.fiap.challengewtcc.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val email: String = "",
    val password: String = "",
    val loading: Boolean = false,
    val error: String? = null,
    val loggedIn: Boolean = false,
    val token: String? = null,
    val userId: String? = null,
    val userName: String? = null,
    val role: UserRole = UserRole.OPERATOR
)

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    fun updateEmail(value: String) {
        _state.value = _state.value.copy(email = value)
    }

    fun updatePassword(value: String) {
        _state.value = _state.value.copy(password = value)
    }

    fun updateRole(role: UserRole) {
        _state.value = _state.value.copy(role = role)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            val result = repository.login(email, password)

            result
                .onSuccess { response ->
                    _state.value = _state.value.copy(
                        loading = false,
                        loggedIn = true,
                        token = response.token,
                        userId = response.user.id,
                        userName = response.user.name,
                        role = response.user.role
                    )
                    SessionManager.token = response.token
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        loading = false,
                        loggedIn = false,
                        error = exception.message
                    )
                }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            repository.register(name, email, password)
                .onSuccess { response ->
                    _state.value = _state.value.copy(
                        loading = false,
                        loggedIn = true,
                        token = response.token,
                        userId = response.user.id,
                        userName = response.user.name,
                        role = response.user.role
                    )
                    SessionManager.token = response.token
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        loading = false,
                        loggedIn = false,
                        error = exception.message
                    )
                }
        }
    }

    fun logout() {
        _state.value = AuthState()
    }
}
