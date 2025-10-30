package br.com.fiap.challengewtcc.ui.theme.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.ui.theme.components.StatCard
import br.com.fiap.challengewtcc.viewmodel.DashboardViewModel

@Composable
fun DashboardScreen(vm: DashboardViewModel) {
    val user = vm.user.collectAsState(null).value
    val summary = vm.summary.collectAsState(initial = "").value
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Olá, ${user?.name ?: "Convidado"}", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard("Notificações", summary, icon = { Icon(Icons.Filled.Campaign, contentDescription = null) })
            StatCard("Clientes Ativos", "4", icon = { Icon(Icons.Filled.People, contentDescription = null) })
            StatCard("VIP", "2", icon = { Icon(Icons.Filled.Star, contentDescription = null) })
        }
    }
}
