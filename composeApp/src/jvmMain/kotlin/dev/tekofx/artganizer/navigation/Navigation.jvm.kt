package dev.tekofx.artganizer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.tekofx.artganizer.ui.screens.MainScreen
import dev.tekofx.artganizer.utils.FIRST_DESKTOP_ROUTE

@Composable
actual fun Navigation() {
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = FIRST_DESKTOP_ROUTE
    ) {
        composable<DesktopMainRoute> {
            MainScreen()
        }
    }
}