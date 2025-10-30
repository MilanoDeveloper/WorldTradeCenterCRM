package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.challengewtcc.data.MockRepository
import br.com.fiap.challengewtcc.data.models.AppNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotificationViewModel : ViewModel() {
    val notifications: Flow<List<AppNotification>> = MockRepository.notifications
    val notificationsCount: Flow<Int> = MockRepository.notifications.map { it.count { n -> !n.read } }
    fun markAllRead() { MockRepository.markAllRead() }
}
