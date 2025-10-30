package br.com.fiap.challengewtcc.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.fiap.challengewtcc.Tab
import kotlinx.coroutines.flow.Flow

@Composable
fun GradientHeader(
    title: String,
    badgeCount: Int,
    onNotifications: () -> Unit,
    onLogout: (() -> Unit)? = null
) {
    val gradient = Brush.horizontalGradient(
        listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient)
            .statusBarsPadding() // 👈 garante espaço seguro no topo
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.weight(1f))
        onLogout?.let {
            TextButton(onClick = it) {
                Text("Sair", color = Color.White)
            }
            Spacer(Modifier.width(8.dp))
        }
        BadgedBox(badge = { if (badgeCount > 0) Badge { Text("$badgeCount") } }) {
            IconButton(onClick = onNotifications) {
                Icon(Icons.Default.Campaign, tint = Color.White, contentDescription = null)
            }
        }
    }
}

@Composable
fun GradientButton(text: String, enabled: Boolean = true, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val gradient = Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary))
    Box(
        modifier
            .clip(RoundedCornerShape(16.dp))
            .background(gradient)
            .clickable(enabled) { onClick() }
            .padding(vertical = 14.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun SegmentedTabs(options: List<String>, selectedIndex: Int, onSelect: (Int) -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Row(
        modifier = Modifier
            .clip(shape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEachIndexed { i, label ->
            val selected = i == selectedIndex
            val bg = if (selected) Brush.horizontalGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)) else null
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(shape)
                    .then(if (bg != null) Modifier.background(bg) else Modifier)
                    .clickable { onSelect(i) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(label, color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun BadgeChip(text: String) {
    Surface(shape = RoundedCornerShape(12.dp), tonalElevation = 1.dp, color = MaterialTheme.colorScheme.primaryContainer) {
        Text(text, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp), style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun StatCard(title: String, value: String, icon: @Composable (() -> Unit)? = null) {
    androidx.compose.material3.ElevatedCard(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            icon?.invoke()
            Column(Modifier.padding(start = 8.dp)) {
                Text(title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ScaffoldApp(
    notifications: Flow<Int>,
    onNotificationClick: () -> Unit,
    navController: NavHostController,
    visibleTabs: List<Tab>,
    onLogout: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val count = notifications.collectAsState(initial = 0).value
    Scaffold(
        topBar = { GradientHeader(title = "WTC Association", badgeCount = count, onNotifications = onNotificationClick, onLogout = onLogout) },
        bottomBar = { BottomBar(navController = navController, visibleTabs = visibleTabs) }
    ) { inner -> Box(Modifier.padding(inner)) { content() } }
}


@Composable
fun BottomBar(navController: NavHostController) {
    NavigationBar {
        val entry by navController.currentBackStackEntryAsState()
        val route = entry?.destination?.route
        NavigationBarItem(
            selected = route == Tab.Dashboard.route,
            onClick = { navController.navigate(Tab.Dashboard.route) },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
            label = { Text("Dashboard") }
        )
        NavigationBarItem(
            selected = route == Tab.Chat.route,
            onClick = { navController.navigate(Tab.Chat.route) },
            icon = { Icon(Icons.Default.Chat, contentDescription = null) },
            label = { Text("Chat") }
        )
        NavigationBarItem(
            selected = route == Tab.CRM.route,
            onClick = { navController.navigate(Tab.CRM.route) },
            icon = { Icon(Icons.Default.People, contentDescription = null) },
            label = { Text("Clientes") }
        )
        NavigationBarItem(
            selected = route == Tab.Campaigns.route,
            onClick = { navController.navigate(Tab.Campaigns.route) },
            icon = { Icon(Icons.Default.Campaign, contentDescription = null) },
            label = { Text("Campanhas") }
        )
    }
}
