package br.com.fiap.challengewtcc

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.challengewtcc.data.UserRole
import br.com.fiap.challengewtcc.ui.theme.components.ScaffoldApp
import br.com.fiap.challengewtcc.ui.theme.screens.campaings.CampaignsScreen
import br.com.fiap.challengewtcc.ui.theme.screens.chat.ChatScreen
import br.com.fiap.challengewtcc.ui.theme.screens.dashboard.DashboardScreen
import br.com.fiap.challengewtcc.ui.theme.screens.login.LoginScreen
import br.com.fiap.challengewtcc.ui.theme.screens.login.SignUpScreen
import br.com.fiap.challengewtcc.ui.theme.screens.notification.NotificationsScreen
import br.com.fiap.challengewtcc.ui.theme.screens.users.UserScreen
import br.com.fiap.challengewtcc.viewmodel.AuthViewModel
import br.com.fiap.challengewtcc.viewmodel.CampaignViewModel
import br.com.fiap.challengewtcc.viewmodel.ChatViewModel
import br.com.fiap.challengewtcc.viewmodel.DashboardViewModel
import br.com.fiap.challengewtcc.viewmodel.NotificationViewModel
import br.com.fiap.challengewtcc.viewmodel.UserViewModel
import kotlinx.coroutines.flow.MutableStateFlow

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object SignUp : Screen("signup")
    data object Shell : Screen("shell")
}

sealed class Tab(val route: String) {
    data object Dashboard : Tab("dashboard")
    data object Chat : Tab("chat")
    data object Campaigns : Tab("campaigns")
}

@Composable
fun AppNavGraph(
    rootNavController: NavHostController = rememberNavController()
) {
    val authVm: AuthViewModel = viewModel()

    NavHost(
        navController = rootNavController,
        startDestination = Screen.Login.route
    ) {

        composable(Screen.Login.route) {

            LoginScreen(
                state = authVm.state.collectAsState(),
                onLogin = { email, pass ->
                    authVm.login(email, pass)
                },
                onLoggedIn = {
                    rootNavController.navigate(Screen.Shell.route) {
                        popUpTo(0)
                    }
                },
                onRoleChange = { role ->
                    authVm.updateRole(role)
                },
                onSignUpClick = {
                    rootNavController.navigate(Screen.SignUp.route)
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                state = authVm.state.collectAsState(),
                onRegister = { name, email, pass ->
                    authVm.register(name, email, pass)
                },
                onBack = {
                    rootNavController.popBackStack()
                },
                onSuccess = {
                    rootNavController.navigate(Screen.Shell.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Screen.Shell.route) {
            Shell(
                rootNav = rootNavController,
                authVm = authVm
            )
        }
    }
}

@Composable
private fun Shell(
    rootNav: NavHostController,
    authVm: AuthViewModel
) {

    val tabNav = rememberNavController()

    val chatVm: ChatViewModel = viewModel()
    val campVm: CampaignViewModel = viewModel()
    val dashVm: DashboardViewModel = viewModel()
    val usersVm: UserViewModel = viewModel()
    val notificationVm: NotificationViewModel = viewModel()

    val role by authVm.state.collectAsState()

    val visibleTabs = if (role.role == UserRole.CLIENT) {
        listOf(
            Tab.Dashboard,
            Tab.Chat
        )
    } else {
        listOf(
            Tab.Dashboard,
            Tab.Chat,
            Tab.Campaigns
        )
    }

    ScaffoldApp(
        notifications = MutableStateFlow(0),
        onNotificationClick = {
            tabNav.navigate("notifications")
        },
        onLogout = {

            authVm.logout()

            rootNav.navigate(Screen.Login.route) {

                popUpTo(Screen.Shell.route) {
                    inclusive = true
                }

                launchSingleTop = true
            }
        },
        navController = tabNav,
        visibleTabs = visibleTabs
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            NavHost(
                navController = tabNav,
                startDestination = Tab.Dashboard.route
            ) {

                composable(Tab.Dashboard.route) {
                    DashboardScreen(
                        dashVm,
                        usersVm,
                        currentUserRole = role.role
                    )
                }

                composable(
                    route = "chat/{otherUserId}"
                ) { backStackEntry ->

                    val otherUserId =
                        backStackEntry.arguments
                            ?.getString("otherUserId")
                            ?: ""

                    ChatScreen(
                        vm = chatVm,
                        authVm = authVm,
                        otherUserId = otherUserId
                    )
                }

                composable(Tab.Campaigns.route) {
                    CampaignsScreen(campVm)
                }

                composable("users") {
                    UserScreen(
                        currentUserRole = role.role,
                        onUserClick = { userId ->
                            tabNav.navigate("chat/$userId")
                        }
                    )
                }

                composable("notifications") {
                    NotificationsScreen(
                        vm = notificationVm,
                        authVm = authVm
                    )
                }

            }
        }
    }
}