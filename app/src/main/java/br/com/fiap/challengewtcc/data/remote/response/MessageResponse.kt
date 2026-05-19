package br.com.fiap.challengewtcc.data.remote.response

data class MessageResponse(

    val id: String? = null,

    val conversationId: String? = null,

    val senderId: String? = null,

    val receiverId: String? = null,

    val senderName: String? = null,

    val content: String? = null,

    val createdAt: String? = null,

    val status: String? = null
)