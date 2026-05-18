package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.remote.request.SendMessageRequest
import br.com.fiap.challengewtcc.data.remote.response.ChatConversationResponse
import br.com.fiap.challengewtcc.data.remote.response.ChatMessageResponse
import br.com.fiap.challengewtcc.data.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatState(
    val loading: Boolean = false,
    val conversations: List<ChatConversationResponse> = emptyList(),
    val messages: List<ChatMessageResponse> = emptyList(),
    val error: String? = null
)

class ChatViewModel : ViewModel() {

    private val repository = ChatRepository()

    private val _state = MutableStateFlow(ChatState())
    val state: StateFlow<ChatState> = _state.asStateFlow()

    fun loadConversations() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            repository.getConversations()
                .onSuccess { conversations ->
                    _state.value = _state.value.copy(
                        loading = false,
                        conversations = conversations
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = exception.message
                    )
                }
        }
    }

    fun loadMessages(conversationId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            repository.getMessages(conversationId)
                .onSuccess { messages ->
                    _state.value = _state.value.copy(
                        loading = false,
                        messages = messages
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        loading = false,
                        error = exception.message
                    )
                }
        }
    }

    fun sendMessage(
        conversationId: String,
        senderId: String,
        message: String
    ) {
        viewModelScope.launch {
            repository.sendMessage(
                conversationId,
                SendMessageRequest(
                    senderId = senderId,
                    message = message
                )
            ).onSuccess {
                loadMessages(conversationId)
            }
        }
    }
}