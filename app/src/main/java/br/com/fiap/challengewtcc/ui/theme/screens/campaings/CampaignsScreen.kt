package br.com.fiap.challengewtcc.ui.theme.screens.campaings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.data.CampaignType
import br.com.fiap.challengewtcc.viewmodel.CampaignViewModel
import br.com.fiap.challengewtcc.viewmodel.ClientSegment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignsScreen(vm: CampaignViewModel) {
    val type by vm.type.collectAsState()
    val segment by vm.segment.collectAsState()
    val message by vm.message.collectAsState()
    val counts by vm.counts.collectAsState()
    val recipients by vm.recipients.collectAsState()
    val canSend = message.isNotBlank()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Campanha Personalizada") }) },
        bottomBar = {
            BottomAppBar {
                Row(
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("${recipients.size}", style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.width(2.dp))
                        Text("clientes serão notificados")
                    }
                    val ctx = androidx.compose.ui.platform.LocalContext.current

                    Button(
                        onClick = {
                            val (title, body) = vm.send()
                            if (br.com.fiap.challengewtcc.notifications.canPostNotifications(ctx)) {
                                try {
                                    br.com.fiap.challengewtcc.notifications.LocalNotifier.notify(ctx, title, body)
                                } catch (_: SecurityException) { }
                            }
                        },
                        enabled = message.isNotBlank()
                    ) { Text("Enviar Campanha") }
                }
            }
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 16.dp, vertical = 2.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            contentPadding = PaddingValues(bottom = 50.dp)
        ) {
            item {
                Text("Tipo de Campanha", style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = type == CampaignType.PROMOCAO, onClick = { vm.setType(CampaignType.PROMOCAO) }, label = { Text("Promoção") })
                    FilterChip(selected = type == CampaignType.COMUNICADO, onClick = { vm.setType(CampaignType.COMUNICADO) }, label = { Text("Comunicado") })
                    FilterChip(selected = type == CampaignType.ALERTA, onClick = { vm.setType(CampaignType.ALERTA) }, label = { Text("Alerta") })
                    FilterChip(selected = type == CampaignType.BOLETO, onClick = { vm.setType(CampaignType.BOLETO) }, label = { Text("Boleto") })
                    FilterChip(selected = type == CampaignType.AGRADECIMENTO, onClick = { vm.setType(CampaignType.AGRADECIMENTO) }, label = { Text("Agradecimento") })
                }
            }

            item {
                Text("Segmento de Clientes", style = MaterialTheme.typography.titleMedium)
            }

            items(listOf(
                Triple("Todos os Clientes", ClientSegment.TODOS, counts[ClientSegment.TODOS] ?: 0),
                Triple("Premium", ClientSegment.PREMIUM, counts[ClientSegment.PREMIUM] ?: 0),
                Triple("Novos", ClientSegment.NOVOS, counts[ClientSegment.NOVOS] ?: 0),
                Triple("Regular", ClientSegment.REGULAR, counts[ClientSegment.REGULAR] ?: 0),
                Triple("Recuperação", ClientSegment.RECUPERACAO, counts[ClientSegment.RECUPERACAO] ?: 0)
            )) { (label, seg, count) ->
                SegmentRadioRow(
                    text = label,
                    selected = segment == seg,
                    count = count,
                    onSelect = { vm.setSegment(seg) }
                )
            }

            item {
                Text("Mensagem", style = MaterialTheme.typography.titleMedium)
                OutlinedTextField(
                    value = message,
                    onValueChange = vm::setMessage,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                    placeholder = { Text("Digite sua mensagem aqui...") }
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(onClick = { vm.applyTemplate() }, label = { Text("Usar Template") })
                    Text("${message.length} caracteres", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

    }
}

@Composable
private fun SegmentRadioRow(text: String, selected: Boolean, count: Int, onSelect: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(selected = selected, onClick = onSelect, role = Role.RadioButton)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = onSelect)
        Spacer(Modifier.width(8.dp))
        Text(text, modifier = Modifier.weight(1f))
        Text("$count clientes", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }


}
