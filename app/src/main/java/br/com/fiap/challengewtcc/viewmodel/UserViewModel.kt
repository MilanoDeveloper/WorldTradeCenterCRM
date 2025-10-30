package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.models.User
import br.com.fiap.challengewtcc.data.remote.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading


    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadUsers() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _users.value = ApiClient.users.getUsers()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

}
