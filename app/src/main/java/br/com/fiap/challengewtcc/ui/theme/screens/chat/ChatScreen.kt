package br.com.fiap.challengewtcc.ui.theme.screens.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.fiap.challengewtcc.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    vm: ChatViewModel
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text("Chat Screen")
    }
}