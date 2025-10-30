package br.com.fiap.challengewtcc.data

import br.com.fiap.challengewtcc.data.models.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.delay
import java.time.Instant
import java.util.UUID

object MockRepository {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    suspend fun login(email: String, password: String): Boolean {
        delay(300)
        _currentUser.value = User("u1", "Usuário Demo", email)
        return true
    }

    private val _notifications = MutableStateFlow(
        listOf(
            AppNotification("n1", "Boas-vindas", "Sua conta foi criada", false),
            AppNotification("n2", "Atualização", "Campanha Setembro pausada", false)
        )
    )
    val notifications: StateFlow<List<AppNotification>> = _notifications.asStateFlow()
    fun markAllRead() { _notifications.update { it.map { n -> n.copy(read = true) } } }
    fun pushNotification(n: AppNotification) { _notifications.update { it + n } }

    private val _conversations = MutableStateFlow(
        listOf(
            Conversation("c1", "Ana Paula", "Oi! Podemos falar hoje?", 2),
            Conversation("c2", "Carlos Silva", "Obrigado pelo retorno.", 0),
            Conversation("c3", "Equipe Suporte", "Ticket #123 resolvido", 0)
        )
    )
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    private val _messages = MutableStateFlow(
        listOf(
            Message(UUID.randomUUID().toString(), "c1", false, "Oi!", Instant.now()),
            Message(UUID.randomUUID().toString(), "c1", true, "Oi, tudo bem?", Instant.now()),
            Message(UUID.randomUUID().toString(), "c1", false, "Podemos falar hoje?", Instant.now())
        )
    )
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    fun sendMessage(conversationId: String, text: String) {
        _messages.update { it + Message(UUID.randomUUID().toString(), conversationId, true, text, Instant.now()) }
        _conversations.update { list -> list.map { if (it.id == conversationId) it.copy(lastMessagePreview = text, unread = 0) else it } }
    }

    private val _clients = MutableStateFlow(
        listOf(
            Client("1","João Silva","joao@email.com","(11) 98765-4321","👨",95,"ativo",listOf("vip","premium"),"2025-10-20",15000,listOf("Cliente muito satisfeito","Prefere contato por WhatsApp"),"premium"),
            Client("2","Maria Santos","maria@email.com","(11) 91234-5678","👩",78,"ativo",listOf("novo","interessado"),"2025-10-25",3500,listOf("Primeira compra realizada"),"novo"),
            Client("3","Pedro Costa","pedro@email.com","(11) 99876-5432","👨‍💼",45,"inativo",listOf("urgente","insatisfeito"),"2025-08-15",8000,listOf("Teve problema com entrega","Necessita follow-up"),"recuperacao"),
            Client("4","Ana Paula","ana@email.com","(11) 97654-3210","👩‍💼",88,"ativo",listOf("vip","fidelizado"),"2025-10-22",25000,listOf("Cliente há 3 anos","Sempre recomenda para amigos"),"premium"),
            Client("5","Carlos Mendes","carlos@email.com","(11) 96543-2109","🧔",62,"ativo",listOf("potencial"),"2025-10-18",5500,listOf("Interessado em novos produtos"),"regular")
        )
    )
    val clients: StateFlow<List<Client>> = _clients.asStateFlow()

    private val _campaigns = MutableStateFlow(
        listOf(
            Campaign("cp1", "Lançamento Out/24", "Ativa", 5000, 2100, 600),
            Campaign("cp2", "Black Friday", "Pausada", 15000, 8500, 2200),
            Campaign("cp3", "Express Teste", "Encerrada", 900, 400, 120)
        )
    )
    val campaigns: StateFlow<List<Campaign>> = _campaigns.asStateFlow()

    fun addNote(clientId: String, note: String) {
        _clients.value = _clients.value.map { if (it.id == clientId) it.copy(notes = it.notes + note) else it }
    }

    fun updateTags(clientId: String, newTags: List<String>) {
        _clients.value = _clients.value.map { if (it.id == clientId) it.copy(tags = newTags) else it }
    }

    fun updateStatus(clientId: String, status: String) {
        _clients.value = _clients.value.map { if (it.id == clientId) it.copy(status = status) else it }
    }
}
