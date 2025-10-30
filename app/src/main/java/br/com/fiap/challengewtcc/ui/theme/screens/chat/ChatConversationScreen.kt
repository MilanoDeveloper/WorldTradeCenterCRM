package br.com.fiap.challengewtcc.ui.theme.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.data.ChatCommands
import br.com.fiap.challengewtcc.data.models.Message
import br.com.fiap.challengewtcc.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatConversationScreen(vm: ChatViewModel, onBack: () -> Unit) {
    val selected by vm.selected.collectAsState(null)
    val messages by vm.messages.collectAsState(emptyList())
    val list = messages.filter { it.conversationId == (selected?.id ?: "") }
    var input by remember { mutableStateOf("") }
    val showHelper = input.startsWith("/")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selected?.contactName ?: "") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { inner ->
        Column(Modifier.fillMaxSize().padding(inner)) {
            LazyColumn(
                modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(list) { m -> MessageBubble(m) }
            }
            if (showHelper) {
                ChatCommandHelper(
                    commands = ChatCommands.all,
                    search = input,
                    onSelect = { cmd ->
                        input = cmd.template
                    }
                )
            }
            Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(value = input, onValueChange = { input = it }, modifier = Modifier.weight(1f), placeholder = { Text("Digite / para comandos rápidos...") })
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    if (input.isBlank() || selected == null) return@Button
                    val convId = selected!!.id
                    val cmd = ChatCommands.findByPrefix(input)
                    if (cmd != null) cmd.action(convId)
                    vm.send(convId, input)
                    input = ""
                }, enabled = input.isNotBlank()) { Text("Enviar") }
            }
            Row(Modifier.fillMaxWidth().padding(horizontal = 3.dp, vertical = 1.dp), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                val replies = listOf("👍 Entendido", "⏰ Aguarde um momento", "✅ Resolvido")
                replies.forEach { txt ->
                    AssistChip(onClick = { input = txt }, label = { Text(txt) })
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(m: Message) {
    val gradient = Brush.horizontalGradient(
        if (m.fromMe) listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
        else listOf(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.surfaceVariant)
    )
    val textColor = if (m.fromMe) Color.White else MaterialTheme.colorScheme.onSurface
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .widthIn(max = 320.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(gradient)
                .padding(10.dp)
                .align(if (m.fromMe) Alignment.CenterEnd else Alignment.CenterStart)
        ) { Text(m.text, color = textColor) }
    }
}
