package br.com.fiap.challengewtcc.ui.theme.screens.campaings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.viewmodel.CampaignViewModel

@Composable
fun CampaignsScreen(vm: CampaignViewModel) {
    val list by vm.campaigns.collectAsState(emptyList())
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        LazyColumn {
            items(list) { cp ->
                ElevatedCard(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text(cp.name, style = MaterialTheme.typography.titleMedium)
                        Text("Status: ${cp.status}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(6.dp))
                        LinearProgressIndicator(progress = { (cp.opened / cp.sent.toFloat()).coerceIn(0f,1f) })
                        Spacer(Modifier.height(4.dp))
                        Text("Enviadas: ${cp.sent}  •  Abertas: ${cp.opened}  •  Cliques: ${cp.clicked}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}
