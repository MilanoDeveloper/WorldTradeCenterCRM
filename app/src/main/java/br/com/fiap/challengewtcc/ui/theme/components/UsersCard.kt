package br.com.fiap.challengewtcc.ui.theme.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.viewmodel.UserViewModel

@Composable
fun UsersCard(vm: UserViewModel, maxItems: Int = 5) {
    val users by vm.users.collectAsState(initial = emptyList())
    val loading by vm.loading.collectAsState(initial = false)
    val error by vm.error.collectAsState(initial = null)

    LaunchedEffect(Unit) { vm.loadUsers() }

    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text("Usuários", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            when {
                loading -> Text("Carregando…")
                error != null -> Text("Erro: $error", color = MaterialTheme.colorScheme.error)
                else -> {
                    val list = users.take(maxItems)
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(list) { u ->
                            Column {
                                Text(u.name, style = MaterialTheme.typography.bodyMedium)
                                Text(u.email, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            Divider()
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text("Total: ${users.size}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
