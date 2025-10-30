package br.com.fiap.challengewtcc.ui.theme.screens.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.data.models.Conversation
import br.com.fiap.challengewtcc.viewmodel.ChatViewModel

@Composable
fun ChatListScreen(vm: ChatViewModel, onOpen: (Conversation) -> Unit) {
    val conversations by vm.conversations.collectAsState(emptyList())
    var query by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Buscar conversas") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(conversations.filter { it.contactName.contains(query, true) }) { c ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOpen(c) }
                        .padding(vertical = 8.dp)
                ) {
                    Row {
                        Text(c.contactName, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
                        if (c.unread > 0) Badge { Text("${c.unread}") }
                    }
                    Text(c.lastMessagePreview, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(8.dp))
                    Divider()
                }
            }
        }
    }
}
