package br.com.fiap.challengewtcc.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import br.com.fiap.challengewtcc.data.models.AppNotification

@Composable
fun InAppNotificationHost(flow: Flow<List<AppNotification>>) {
    val list by flow.collectAsState(initial = emptyList())
    var current by remember { mutableStateOf<AppNotification?>(null) }

    LaunchedEffect(list) {
        if (list.isNotEmpty()) {
            current = list.last()
            delay(2500)
            current = null
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        AnimatedVisibility(visible = current != null, enter = fadeIn(), exit = fadeOut()) {
            Surface(
                tonalElevation = 4.dp,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Text(current?.title ?: "", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(2.dp))
                    Text(current?.body ?: "", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
