package br.com.fiap.challengewtcc.ui.theme.screens.crm

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.data.models.Client
import br.com.fiap.challengewtcc.viewmodel.CRMViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailView(vm: CRMViewModel, client: Client, onBack: () -> Unit) {
    var note by remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(client.name) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null) } }
            )
        }
    ) { inner ->
        Column(Modifier.fillMaxSize().padding(inner).padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Informações", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("Email: ${client.email}")
                    Text("Telefone: ${client.phone}")
                    Text("Segmento: ${client.segment}")
                    Text("Status: ${client.status}")
                    Text("Score: ${client.score}")
                    Text("Última Compra: ${client.lastPurchase}")
                    Text("Total Gasto: R$ ${client.totalSpent}")
                }
            }
            Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("Notas", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    client.notes.forEach { Text("• $it", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = note, onValueChange = { note = it }, placeholder = { Text("Adicionar nova anotação...") }, modifier = Modifier.fillMaxWidth())
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { if (note.isNotBlank()) { vm.addNote(client.id, note); note = "" } }, modifier = Modifier.align(Alignment.End)) { Text("Adicionar") }
                }
            }
        }
    }
}
