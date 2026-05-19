package br.com.fiap.challengewtcc.ui.theme.screens.chat

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.data.remote.response.MessageResponse
import br.com.fiap.challengewtcc.viewmodel.AuthState
import br.com.fiap.challengewtcc.viewmodel.AuthViewModel
import br.com.fiap.challengewtcc.viewmodel.ChatState
import br.com.fiap.challengewtcc.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    vm: ChatViewModel,
    authVm: AuthViewModel,
    otherUserId: String
) {
    val state: ChatState by vm.state.collectAsState(initial = ChatState())
    val authState: AuthState by authVm.state.collectAsState(initial = AuthState())
    val currentUserId = authState.userId ?: ""
    
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(otherUserId, currentUserId) {
        if (currentUserId.isNotEmpty() && otherUserId.isNotEmpty()) {
            Log.d("ChatScreen", "Abrindo chat: Eu($currentUserId) com Outro($otherUserId)")
            vm.startPolling(currentUserId, otherUserId)
        } else {
            Log.e("ChatScreen", "IDs inválidos: Eu($currentUserId) Outro($otherUserId)")
        }
    }

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.weight(1f)) {
            if (state.loading && state.messages.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (state.error != null && state.messages.isEmpty()) {
                Text(
                    text = "Erro: ${state.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.messages.isEmpty()) {
                Text(
                    text = "Nenhuma mensagem ainda. Comece a conversa!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center).padding(32.dp)
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize(),
                    reverseLayout = false,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.messages) { message: MessageResponse ->
                        MessageItem(
                            message = message,
                            isMe = message.senderId == currentUserId
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Digite sua mensagem...") },
                shape = RoundedCornerShape(24.dp)
            )
            
            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        vm.sendMessage(currentUserId, otherUserId, messageText)
                        messageText = ""
                    }
                },
                enabled = messageText.isNotBlank()
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
            }
        }
    }
}

@Composable
fun MessageItem(message: MessageResponse, isMe: Boolean) {
    val alignment = if (isMe) Alignment.CenterEnd else Alignment.CenterStart
    val bgColor = if (isMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isMe) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
    val shape = if (isMe) {
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Surface(
            color = bgColor,
            shape = shape,
            tonalElevation = 2.dp
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                if (!isMe) {
                    Text(
                        text = message.senderName ?: "Usuário",
                        style = MaterialTheme.typography.labelSmall,
                        color = textColor.copy(alpha = 0.7f)
                    )
                }
                Text(
                    text = message.content ?: "",
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
