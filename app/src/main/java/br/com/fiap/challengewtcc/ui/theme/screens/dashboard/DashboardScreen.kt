package br.com.fiap.challengewtcc.ui.theme.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.ui.theme.components.StatCard
import br.com.fiap.challengewtcc.ui.theme.components.UsersCard
import br.com.fiap.challengewtcc.viewmodel.DashboardViewModel
import br.com.fiap.challengewtcc.viewmodel.UserViewModel

@Composable
fun DashboardScreen(vm: DashboardViewModel, usersVm: UserViewModel) {
    val user = vm.user.collectAsState(null).value
    val summary = vm.summary.collectAsState(initial = "").value
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Olá, ${user?.name ?: "Convidado"}", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            StatCard("Notificações", summary)
            UsersCard(vm = usersVm, maxItems = 5)
        }
    }
}
