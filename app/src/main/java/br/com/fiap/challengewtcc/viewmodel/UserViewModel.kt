package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.UserRole
import br.com.fiap.challengewtcc.data.remote.ApiClient
import br.com.fiap.challengewtcc.data.remote.response.AuthUserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<AuthUserResponse>>(emptyList())
    val users: StateFlow<List<AuthUserResponse>> = _users

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadUsers(currentUserRole: UserRole) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val response = ApiClient.userService.getUsers()
                if (response.isSuccessful) {
                    val allUsers = response.body() ?: emptyList()
                    
                    // Lógica de Isolamento:
                    // 1. Operador vê apenas CLIENTES
                    // 2. Cliente vê apenas OPERADORES
                    _users.value = if (currentUserRole == UserRole.OPERATOR) {
                        allUsers.filter { it.role == UserRole.CLIENT }
                    } else {
                        allUsers.filter { it.role == UserRole.OPERATOR }
                    }
                    
                    _error.value = null
                } else {
                    _error.value = "Erro ao carregar usuários: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}
