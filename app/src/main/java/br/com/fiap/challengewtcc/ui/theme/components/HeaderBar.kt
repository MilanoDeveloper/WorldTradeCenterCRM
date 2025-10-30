/*
package br.com.fiap.challengewtcc.ui.theme.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.Flow

@Composable
fun ScaffoldApp(
    notifications: Flow<Int>,
    onNotificationClick: () -> Unit,
    navController: NavHostController,
    content: @Composable () -> Unit
) {
    val count by notifications.collectAsState(initial = 0)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Challenge CRM/Chat") },
                actions = {
                    BadgedBox(badge = { if (count > 0) Badge { Text("$count") } }) {
                        IconButton(onClick = onNotificationClick) {
                            Icon(Icons.Default.Campaign, contentDescription = "Notificações")
                        }
                    }
                }
            )
        },
        bottomBar = { BottomBar(navController) }
    ) { inner ->
        Box(modifier = Modifier.padding(inner)) {
            content()
        }
    }
}*/
