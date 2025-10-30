package br.com.fiap.challengewtcc.ui.theme.screens.chat

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengewtcc.viewmodel.ChatViewModel

sealed class ChatTab(val route: String) {
    data object List : ChatTab("chat_list")
    data object Conversation : ChatTab("chat_conversation")
}

@Composable
fun ChatScreen(vm: ChatViewModel) {
    val chatNav = rememberNavController()
    NavHost(chatNav, startDestination = ChatTab.List.route) {
        composable(ChatTab.List.route) {
            ChatListScreen(
                vm = vm,
                onOpen = {
                    vm.selectConversation(it)
                    chatNav.navigate(ChatTab.Conversation.route)
                }
            )
        }
        composable(ChatTab.Conversation.route) {
            ChatConversationScreen(
                vm = vm,
                onBack = {
                    vm.clearSelection()
                    chatNav.popBackStack()
                }
            )
        }
    }
}
