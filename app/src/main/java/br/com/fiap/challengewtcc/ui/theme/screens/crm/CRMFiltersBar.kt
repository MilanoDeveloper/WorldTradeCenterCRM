package br.com.fiap.challengewtcc.ui.theme.screens.crm

import androidx.compose.foundation.layout.*
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.viewmodel.CRMViewModel

@Composable
fun CRMFiltersBar(vm: CRMViewModel) {
    val f = vm.filters.collectAsState().value
    Column(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = f.search,
            onValueChange = vm::setSearch,
            label = { Text("Buscar clientes por nome, email ou telefone") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AssistChip(onClick = { vm.setSegment("premium") }, label = { Text("Premium") })
            AssistChip(onClick = { vm.setSegment("novo") }, label = { Text("Novos") })
            AssistChip(onClick = { vm.setSegment("regular") }, label = { Text("Regular") })
            AssistChip(onClick = { vm.setSegment("recuperacao") }, label = { Text("Recuperação") })
            AssistChip(onClick = { vm.setSegment(null) }, label = { Text("Todos") })
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = f.status=="all", onClick = { vm.setStatus("all") }, label = { Text("Status: Todos") })
            FilterChip(selected = f.status=="ativo", onClick = { vm.setStatus("ativo") }, label = { Text("Ativos") })
            FilterChip(selected = f.status=="inativo", onClick = { vm.setStatus("inativo") }, label = { Text("Inativos") })
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = f.score=="all", onClick = { vm.setScore("all") }, label = { Text("Score: Todos") })
            FilterChip(selected = f.score=="high", onClick = { vm.setScore("high") }, label = { Text("Alta") })
            FilterChip(selected = f.score=="medium", onClick = { vm.setScore("medium") }, label = { Text("Média") })
            FilterChip(selected = f.score=="low", onClick = { vm.setScore("low") }, label = { Text("Baixa") })
        }
    }
}
