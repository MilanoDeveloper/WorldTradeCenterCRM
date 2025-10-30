package br.com.fiap.challengewtcc.ui.theme.screens.crm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.data.models.Client
import br.com.fiap.challengewtcc.viewmodel.CRMViewModel

@Composable
fun CRMListView(vm: CRMViewModel, onSelect: (Client) -> Unit) {
    val list = vm.filteredClients.collectAsState(emptyList()).value
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        CRMFiltersBar(vm)
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list) { client ->
                Card(onClick = { onSelect(client) }, modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text(client.name, style = MaterialTheme.typography.titleMedium)
                        Text(client.email, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(client.phone, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(6.dp))
                        Text(client.segment, color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}
