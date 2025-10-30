package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.challengewtcc.data.MockRepository
import kotlinx.coroutines.flow.map

class DashboardViewModel: ViewModel() {
    val user = MockRepository.currentUser
    val notifications = MockRepository.notifications
    val summary = notifications.map { list ->
        val unread = list.count { !it.read }
        "Você tem $unread notificações não lidas."
    }
}