package com.tfg.nbapredictor.ui.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tfg.nbapredictor.ui.compose.screens.BetsScreen
import com.tfg.nbapredictor.ui.compose.screens.DashboardScreen
import com.tfg.nbapredictor.ui.compose.screens.MatchDetailScreen
import com.tfg.nbapredictor.ui.compose.screens.MatchesScreen
import com.tfg.nbapredictor.ui.compose.screens.ProfileScreen
import com.tfg.nbapredictor.ui.compose.screens.RankingsScreen
import com.tfg.nbapredictor.ui.compose.screens.StoreScreen
import com.tfg.nbapredictor.util.Session
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NBAComposeApp(
    onLogout: () -> Unit,
    onAdminClick: () -> Unit,
    isAdmin: Boolean
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    var points by remember { mutableStateOf(Session.getCurrentUser()?.points ?: 0) }
    LaunchedEffect(Unit) {
        Session.userUpdated.collectLatest {
            points = Session.getCurrentUser()?.points ?: 0
        }
    }

    val topBarActions = @Composable {
        Text("${points} pts", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        if (isAdmin) {
            TextButton(onClick = onAdminClick) {
                Text("Admin")
            }
        }
        TextButton(onClick = onLogout) {
            Text("Salir")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("NBA Predictor") },
                actions = { topBarActions() }
            )
        },
        bottomBar = {
            val showBottomBar = currentDestination?.route in listOf(
                ComposeScreens.Dashboard.route,
                ComposeScreens.Matches.route,
                ComposeScreens.Bets.route,
                ComposeScreens.Rankings.route,
                ComposeScreens.Profile.route,
                ComposeScreens.Store.route
            )
            if (showBottomBar) {
                NavigationBar {
                    listOf(
                        BottomNavItem(ComposeScreens.Dashboard.route, "Inicio", Icons.Default.Home),
                        BottomNavItem(ComposeScreens.Matches.route, "Partidos", Icons.Default.Sports),
                        BottomNavItem(ComposeScreens.Bets.route, "Apuestas", Icons.Default.SportsEsports),
                        BottomNavItem(ComposeScreens.Rankings.route, "Ranking", Icons.Default.List),
                        BottomNavItem(ComposeScreens.Profile.route, "Perfil", Icons.Default.Person),
                        BottomNavItem(ComposeScreens.Store.route, "Tienda", Icons.Default.ShoppingCart)
                    ).forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
        ) { paddingValues ->
        val onMatchClick: (com.tfg.nbapredictor.model.Partido) -> Unit = { partido ->
            NavSharedState.selectedPartido = partido
            navController.navigate(ComposeScreens.MatchDetail.route)
        }
        NavHost(
            navController = navController,
            startDestination = ComposeScreens.Dashboard.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(ComposeScreens.Dashboard.route) {
                DashboardScreen(onMatchClick = onMatchClick)
            }
            composable(ComposeScreens.Matches.route) {
                MatchesScreen(onMatchClick = onMatchClick)
            }
            composable(ComposeScreens.Bets.route) {
                BetsScreen()
            }
            composable(ComposeScreens.Rankings.route) {
                RankingsScreen()
            }
            composable(ComposeScreens.Profile.route) {
                ProfileScreen()
            }
            composable(ComposeScreens.Store.route) {
                StoreScreen()
            }
            composable(ComposeScreens.MatchDetail.route) {
                MatchDetailScreen(
                    partido = NavSharedState.selectedPartido,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

private data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)

sealed class ComposeScreens(val route: String) {
    object Dashboard : ComposeScreens("dashboard")
    object Matches : ComposeScreens("matches")
    object Bets : ComposeScreens("bets")
    object Rankings : ComposeScreens("rankings")
    object Profile : ComposeScreens("profile")
    object Store : ComposeScreens("store")
    object MatchDetail : ComposeScreens("match_detail")
}
