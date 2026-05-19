package br.com.fiap.challengewtcc.ui.theme.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import br.com.fiap.challengewtcc.Tab

@Composable
fun BottomBar(
    navController: NavHostController,
    visibleTabs: List<Tab>
) {

    NavigationBar {

        val entry by navController.currentBackStackEntryAsState()
        val route = entry?.destination?.route

        visibleTabs.forEach { tab ->

            when (tab) {

                Tab.Dashboard -> {

                    NavigationBarItem(
                        selected = route == Tab.Dashboard.route,
                        onClick = {
                            navController.navigate(Tab.Dashboard.route)
                        },
                        icon = {
                            Icon(
                                Icons.Default.Dashboard,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text("Dashboard")
                        }
                    )
                }

                Tab.Chat -> {

                    NavigationBarItem(
                        selected = route?.startsWith("chat") == true || route == "users",
                        onClick = {
                            // Se for operador, vai para a lista de usuários primeiro para escolher um cliente
                            navController.navigate("users")
                        },
                        icon = {
                            Icon(
                                Icons.Default.Chat,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text("Chat")
                        }
                    )
                }

                Tab.Campaigns -> {

                    NavigationBarItem(
                        selected = route == Tab.Campaigns.route,
                        onClick = {
                            navController.navigate(Tab.Campaigns.route)
                        },
                        icon = {
                            Icon(
                                Icons.Default.Campaign,
                                contentDescription = null
                            )
                        },
                        label = {
                            Text("Campanhas")
                        }
                    )
                }
            }
        }
    }
}
