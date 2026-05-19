package br.com.fiap.challengewtcc.ui.theme.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.viewmodel.AuthViewModel
import br.com.fiap.challengewtcc.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    vm: ChatViewModel,
    authVm: AuthViewModel
) {

    val state by vm.state.collectAsState()

    val authState by authVm.state.collectAsState()

    var messageText by remember {
        mutableStateOf("")
    }

    val operatorId = "ID_DO_OPERADOR"

    LaunchedEffect(Unit) {

        authState.userId?.let { userId ->

            vm.loadMessages(
                userId,
                operatorId
            )

            vm.startPolling(
                userId,
                operatorId
            )
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {

            items(state.messages.reversed()) { message ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = message.senderName ?: "Usuário"
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = message.content ?: ""
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = message.status ?: ""
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {

            OutlinedTextField(
                value = messageText,
                onValueChange = {
                    messageText = it
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Button(
                onClick = {

                    if (
                        messageText.isNotBlank() &&
                        authState.userId != null
                    ) {

                        vm.sendMessage(
                            senderId = authState.userId!!,
                            receiverId = operatorId,
                            content = messageText
                        )

                        messageText = ""
                    }
                }
            ) {

                Text("Enviar")
            }
        }
    }
}