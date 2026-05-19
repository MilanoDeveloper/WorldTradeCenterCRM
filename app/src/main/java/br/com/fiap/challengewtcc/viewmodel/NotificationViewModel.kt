package br.com.fiap.challengewtcc.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.remote.response.NotificationResponse
import br.com.fiap.challengewtcc.data.repository.NotificationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {

    private val repository = NotificationRepository()

    private val _notifications =
        MutableStateFlow<List<NotificationResponse>>(emptyList())

    val notifications: StateFlow<List<NotificationResponse>> =
        _notifications

    private val _unreadCount =
        MutableStateFlow(0)

    val unreadCount: StateFlow<Int> =
        _unreadCount

    private val _loading =
        MutableStateFlow(false)

    val loading: StateFlow<Boolean> =
        _loading

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    fun loadNotifications(userId: String) {

        viewModelScope.launch {

            repository.getNotifications(userId)
                .onSuccess {

                    Log.d("NOTIFICATION_DEBUG", it.toString())

                    _notifications.value = it
                }
                .onFailure {

                    Log.e("NOTIFICATION_DEBUG", it.message ?: "erro")
                }
        }
    }

    fun loadUnreadCount(
        userId: String
    ) {

        viewModelScope.launch {

            repository
                .getUnreadCount(userId)
                .onSuccess {

                    _unreadCount.value = it
                }
                .onFailure {

                    _error.value = it.message
                }
        }
    }

    fun markAsRead(
        id: String,
        userId: String
    ) {

        viewModelScope.launch {

            repository
                .markAsRead(id)
                .onSuccess {

                    loadNotifications(userId)
                    loadUnreadCount(userId)
                }
                .onFailure {

                    _error.value = it.message
                }
        }
    }
}