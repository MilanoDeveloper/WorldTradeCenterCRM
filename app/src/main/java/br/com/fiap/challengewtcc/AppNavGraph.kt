package br.com.fiap.challengewtcc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengewtcc.ui.theme.components.InAppNotificationHost
import br.com.fiap.challengewtcc.ui.theme.components.NotificationCenterSheet
import br.com.fiap.challengewtcc.ui.theme.components.ScaffoldApp
import br.com.fiap.challengewtcc.ui.theme.screens.campaings.CampaignsScreen
import br.com.fiap.challengewtcc.ui.theme.screens.chat.ChatScreen
import br.com.fiap.challengewtcc.ui.theme.screens.crm.CRMScreen
import br.com.fiap.challengewtcc.ui.theme.screens.dashboard.DashboardScreen
import br.com.fiap.challengewtcc.ui.theme.screens.login.LoginScreen
import br.com.fiap.challengewtcc.ui.theme.screens.users.UserScreen
import br.com.fiap.challengewtcc.viewmodel.*

sealed class Screen(val route: String) {
    data object Login: Screen("login")
    data object Shell: Screen("shell")
}

sealed class Tab(val route: String) {
    data object Dashboard: Tab("dashboard")
    data object Chat: Tab("chat")
    data object CRM: Tab("crm")
    data object Campaigns: Tab("campaigns")
}

@Composable
fun AppNavGraph(rootNavController: NavHostController = rememberNavController()) {
    val authVm: AuthViewModel = viewModel()

    NavHost(navController = rootNavController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                state = authVm.state.collectAsState(),
                onLogin = { email, pass -> authVm.login(email, pass) },
                onLoggedIn = {
                    rootNavController.navigate(Screen.Shell.route) {
                        popUpTo(0)
                    }
                }
            )
        }
        composable(Screen.Shell.route) {
            Shell(rootNav = rootNavController, authVm = authVm) // <-- passa o mesmo VM
        }
    }
}

@Composable
private fun Shell(rootNav: NavHostController, authVm: AuthViewModel) {
    val tabNav = rememberNavController()
    val notifVm: NotificationViewModel = viewModel()
    val chatVm: ChatViewModel = viewModel()
    val crmVm: CRMViewModel = viewModel()
    val campVm: CampaignViewModel = viewModel()
    val dashVm: DashboardViewModel = viewModel()
    val usersVm: UserViewModel = viewModel()
    var showSheet by rememberSaveable { mutableStateOf(false) }

    ScaffoldApp(
        notifications = notifVm.notificationsCount,
        onNotificationClick = { showSheet = true },
        onLogout = {
            authVm.logout()
            rootNav.navigate(Screen.Login.route) {
                popUpTo(Screen.Shell.route) { inclusive = true }
                launchSingleTop = true
            }
        },
        navController = tabNav
    ) {
        Box(Modifier.fillMaxSize()) {
            NavHost(tabNav, startDestination = Tab.Dashboard.route) {
                composable(Tab.Dashboard.route) { DashboardScreen(dashVm, usersVm) }
                composable(Tab.Chat.route) { ChatScreen(chatVm) }
                composable(Tab.CRM.route) { CRMScreen(crmVm) }
                composable(Tab.Campaigns.route) { CampaignsScreen(campVm) }
            }
            InAppNotificationHost(flow = notifVm.notifications)
            if (showSheet) {
                NotificationCenterSheet(
                    notifications = notifVm.notifications,
                    onMarkAllRead = { notifVm.markAllRead() },
                    onDismiss = { showSheet = false }
                )
            }
        }
    }
}

