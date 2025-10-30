package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import br.com.fiap.challengewtcc.data.MockRepository
import br.com.fiap.challengewtcc.data.models.Conversation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChatViewModel : ViewModel() {
    val conversations = MockRepository.conversations
    val messages = MockRepository.messages
    private val _selected = MutableStateFlow<Conversation?>(null)
    val selected: StateFlow<Conversation?> = _selected.asStateFlow()
    fun selectConversation(c: Conversation) { _selected.value = c }
    fun clearSelection() { _selected.value = null }
    fun send(text: String) { _selected.value?.let { MockRepository.sendMessage(it.id, text) } }
    fun send(conversationId: String, text: String) { MockRepository.sendMessage(conversationId, text) }
}
