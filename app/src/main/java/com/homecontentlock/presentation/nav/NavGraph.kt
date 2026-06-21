package com.homecontentlock.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.homecontentlock.presentation.screen.LogsScreen
import com.homecontentlock.presentation.screen.PasswordScreen
import com.homecontentlock.presentation.screen.SitesScreen
import com.homecontentlock.presentation.screen.StatusScreen

enum class NavRoute {
    STATUS,
    LOGS,
    PASSWORD,
    SITES
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.STATUS.name
    ) {
        composable(NavRoute.STATUS.name) {
            StatusScreen()
        }
        composable(NavRoute.LOGS.name) {
            LogsScreen()
        }
        composable(NavRoute.PASSWORD.name) {
            PasswordScreen()
        }
        composable(NavRoute.SITES.name) {
            SitesScreen()
        }
    }
}
