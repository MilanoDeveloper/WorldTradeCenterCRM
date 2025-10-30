package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.challengewtcc.data.MockRepository
import br.com.fiap.challengewtcc.data.models.AppNotification
import kotlinx.coroutines.flow.map

class NotificationViewModel : ViewModel() {
    val notifications = MockRepository.notifications
    val notificationsCount = notifications.map { it.count { n -> !n.read } }
    fun markAllRead() = MockRepository.markAllRead()
    fun add(n: AppNotification) = MockRepository.pushNotification(n)
}
