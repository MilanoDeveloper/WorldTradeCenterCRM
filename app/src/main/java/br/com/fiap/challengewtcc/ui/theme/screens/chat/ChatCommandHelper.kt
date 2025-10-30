package br.com.fiap.challengewtcc.ui.theme.screens.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.data.ChatCommand

@Composable
fun ChatCommandHelper(
    commands: List<ChatCommand>,
    search: String,
    onSelect: (ChatCommand) -> Unit
) {
    val filtered = if (search.isBlank() || search == "/") commands else commands.filter { it.key.startsWith(search) }
    if (filtered.isEmpty()) return
    Card(Modifier.fillMaxWidth()) {
        LazyColumn(Modifier.padding(8.dp)) {
            items(filtered) { cmd ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(cmd) }
                        .padding(vertical = 10.dp)
                ) {
                    Text("${cmd.label}  ${cmd.key}", style = MaterialTheme.typography.titleSmall)
                    Text(cmd.description, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
