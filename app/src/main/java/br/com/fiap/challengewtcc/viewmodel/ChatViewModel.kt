package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.remote.request.SendMessageRequest
import br.com.fiap.challengewtcc.data.remote.response.MessageResponse
import br.com.fiap.challengewtcc.data.repository.ChatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatState(

    val loading: Boolean = false,

    val messages: List<MessageResponse> = emptyList(),

    val error: String? = null
)

class ChatViewModel : ViewModel() {

    private val repository = ChatRepository()

    private val _state = MutableStateFlow(ChatState())

    val state: StateFlow<ChatState> = _state.asStateFlow()

    fun loadMessages(
        user1: String,
        user2: String
    ) {

        viewModelScope.launch {

            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            repository.getMessages(user1, user2)
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

    fun startPolling(
        user1: String,
        user2: String
    ) {

        viewModelScope.launch {

            while (true) {

                loadMessages(user1, user2)

                delay(3000)
            }
        }
    }

    fun sendMessage(
        senderId: String,
        receiverId: String,
        content: String
    ) {

        viewModelScope.launch {

            repository.sendMessage(
                SendMessageRequest(
                    senderId = senderId,
                    receiverId = receiverId,
                    content = content
                )
            ).onSuccess {

                loadMessages(senderId, receiverId)
            }
        }
    }
}