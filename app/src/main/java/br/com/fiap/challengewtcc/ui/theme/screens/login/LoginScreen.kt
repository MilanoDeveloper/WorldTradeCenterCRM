package br.com.fiap.challengewtcc.ui.theme.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.fiap.challengewtcc.viewmodel.AuthState
import br.com.fiap.challengewtcc.ui.theme.components.SegmentedTabs

@Composable
fun LoginScreen(
    state: State<AuthState>?,
    onLogin: (String, String) -> Unit,
    onLoggedIn: () -> Unit
) {
    val s = state?.value ?: AuthState()
    LaunchedEffect(s.loggedIn) { if (s.loggedIn) onLoggedIn() }
    var email by remember(s.email) { mutableStateOf(s.email) }
    var password by remember(s.password) { mutableStateOf(s.password) }
    var roleIndex by remember { mutableStateOf(0) }
    val gradient = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.85f), MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))))
                .padding(vertical = 16.dp, horizontal = 18.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "WTC Association", style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontWeight = FontWeight.Bold))
                Spacer(Modifier.height(2.dp))
                Text(text = "WORLD TRADE CENTER", style = MaterialTheme.typography.labelMedium.copy(color = Color.White.copy(alpha = .9f)))
            }
        }
        Spacer(Modifier.height(18.dp))
        Text(text = "CRM Chat Pro", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
        Text(text = "Sistema Integrado de Atendimento e Gestão", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {
                SegmentedTabs(options = listOf("Operador", "Cliente"), selectedIndex = roleIndex, onSelect = { roleIndex = it })
                Spacer(Modifier.height(14.dp))
                OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, placeholder = { Text("seu@email.com") }, singleLine = true, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Senha") }, placeholder = { Text("••••••••") }, singleLine = true, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(gradient)
                        .fillMaxWidth()
                        .height(52.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val enabled = !s.loading && email.isNotBlank()
                    TextButton(onClick = { onLogin(email, password.ifBlank { "123" }) }, enabled = enabled, colors = ButtonDefaults.textButtonColors(contentColor = Color.White)) {
                        if (s.loading) CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp) else Text("Entrar", style = MaterialTheme.typography.titleMedium, color = Color.White)
                    }
                }
                if (s.error != null) {
                    Spacer(Modifier.height(8.dp))
                    Text(text = s.error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Spacer(Modifier.height(14.dp))
        ElevatedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(14.dp)) {
                Text("Dica para teste:", style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.height(6.dp))
                Text("Operador WTC: operador@wtcassociation.com\nCliente: cliente@email.com\nQualquer senha funciona no modo demo", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
