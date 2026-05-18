package br.com.fiap.challengewtcc.ui.theme.screens.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.viewmodel.AuthViewModel
import br.com.fiap.challengewtcc.viewmodel.NotificationViewModel

@Composable
fun NotificationsScreen(
    vm: NotificationViewModel,
    authVm: AuthViewModel
) {

    val authState by authVm.state.collectAsState()

    LaunchedEffect(Unit) {

        authState.userId?.let { userId ->
            vm.loadNotifications(userId)
        }
    }

    val notifications by vm.notifications.collectAsState()

    LazyColumn {

        items(notifications) { notification ->

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                Column(
                    Modifier.padding(16.dp)
                ) {

                    Text(notification.title)

                    Spacer(Modifier.height(4.dp))

                    Text(notification.message)
                }
            }
        }
    }
}