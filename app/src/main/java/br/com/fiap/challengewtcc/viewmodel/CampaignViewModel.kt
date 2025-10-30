package br.com.fiap.challengewtcc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.challengewtcc.data.CampaignType
import br.com.fiap.challengewtcc.data.MockRepository
import br.com.fiap.challengewtcc.data.emoji
import br.com.fiap.challengewtcc.data.models.AppNotification
import br.com.fiap.challengewtcc.data.template
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.util.UUID

enum class ClientSegment { TODOS, PREMIUM, NOVOS, REGULAR, RECUPERACAO }

class CampaignViewModel : ViewModel() {
    val clients = MockRepository.clients
    private val _type = MutableStateFlow(CampaignType.PROMOCAO)
    val type: StateFlow<CampaignType> = _type
    private val _segment = MutableStateFlow(ClientSegment.TODOS)
    val segment: StateFlow<ClientSegment> = _segment
    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message

    val recipients = combine(clients, _segment) { list, seg ->
        when (seg) {
            ClientSegment.TODOS -> list
            ClientSegment.PREMIUM -> list.filter { it.segment == "premium" || "vip" in it.tags }
            ClientSegment.NOVOS -> list.filter { it.segment == "novo" }
            ClientSegment.REGULAR -> list.filter { it.segment == "regular" }
            ClientSegment.RECUPERACAO -> list.filter { it.segment == "recuperacao" }
        }
    }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, emptyList())

    val counts = clients.combine(_segment) { list, _ ->
        mapOf(
            ClientSegment.TODOS to list.size,
            ClientSegment.PREMIUM to list.count { it.segment == "premium" || "vip" in it.tags },
            ClientSegment.NOVOS to list.count { it.segment == "novo" },
            ClientSegment.REGULAR to list.count { it.segment == "regular" },
            ClientSegment.RECUPERACAO to list.count { it.segment == "recuperacao" }
        )
    }.stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Eagerly, emptyMap())

    fun setType(t: CampaignType) { _type.value = t }
    fun setSegment(s: ClientSegment) { _segment.value = s }
    fun setMessage(text: String) { _message.value = text }
    fun applyTemplate() { _message.value = _type.value.template() }

    fun send(): Int {
        val qty = recipients.value.size
        val title = "${_type.value.emoji()} Campanha enviada"
        val body = "Destinatários: $qty"
        MockRepository.pushNotification(AppNotification(UUID.randomUUID().toString(), title, body, false))
        return qty
    }
}
