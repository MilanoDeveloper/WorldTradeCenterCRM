package br.com.fiap.challengewtcc.ui.theme.screens.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.challengewtcc.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen() {
    val vm: UserViewModel = viewModel()
    val users by vm.users.collectAsState()
    val error by vm.error.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadUsers()
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Usuários (Mock API)") }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(16.dp)
        ) {
            if (error != null) {
                Text("Erro: $error", color = MaterialTheme.colorScheme.error)
            } else if (users.isEmpty()) {
                CircularProgressIndicator()
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(users) { user ->
                        Card(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(16.dp)) {
                                Text(user.name, style = MaterialTheme.typography.titleMedium)
                                Text(user.email, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
