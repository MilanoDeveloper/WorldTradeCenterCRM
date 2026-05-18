package br.com.fiap.challengewtcc.ui.theme.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.ui.theme.components.UsersCard
import br.com.fiap.challengewtcc.viewmodel.DashboardViewModel
import br.com.fiap.challengewtcc.viewmodel.UserViewModel

@Composable
fun DashboardScreen(
    vm: DashboardViewModel,
    usersVm: UserViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Bem-vindo!",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        UsersCard(
            vm = usersVm,
            maxItems = 5
        )
    }
}