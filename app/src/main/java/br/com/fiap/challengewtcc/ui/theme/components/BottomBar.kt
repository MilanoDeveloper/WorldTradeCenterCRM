package br.com.fiap.challengewtcc.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.fiap.challengewtcc.Tab

@Composable
fun BottomBar(navController: NavHostController, visibleTabs: List<Tab>) {
    NavigationBar {
        val entry = navController.currentBackStackEntryAsState().value
        val route = entry?.destination?.route
        if (Tab.Dashboard in visibleTabs) {
            NavigationBarItem(
                selected = route == Tab.Dashboard.route,
                onClick = { navController.navigate(Tab.Dashboard.route) },
                icon = { androidx.compose.material3.Icon(Icons.Default.Dashboard, null) },
                label = { Text("Dashboard") }
            )
        }
        if (Tab.Chat in visibleTabs) {
            NavigationBarItem(
                selected = route == Tab.Chat.route,
                onClick = { navController.navigate(Tab.Chat.route) },
                icon = { androidx.compose.material3.Icon(Icons.Default.Chat, null) },
                label = { Text("Chat") }
            )
        }
        if (Tab.CRM in visibleTabs) {
            NavigationBarItem(
                selected = route == Tab.CRM.route,
                onClick = { navController.navigate(Tab.CRM.route) },
                icon = { androidx.compose.material3.Icon(Icons.Default.People, null) },
                label = { Text("Clientes") }
            )
        }
        if (Tab.Campaigns in visibleTabs) {
            NavigationBarItem(
                selected = route == Tab.Campaigns.route,
                onClick = { navController.navigate(Tab.Campaigns.route) },
                icon = { androidx.compose.material3.Icon(Icons.Default.Campaign, null) },
                label = { Text("Campanhas") }
            )
        }
    }
}
