package dev.tekofx.artganizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavigationEventHandler(
    navigationState: NavigationState,
    navController: NavHostController,
    onEventHandled: () -> Unit,
    onRouteChanged: (NavBackStackEntry?) -> Unit,
    onBackStackChanged: (Boolean) -> Unit
) {
    // Monitor back stack changes
    val backStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(backStackEntry) {
        println("BackStackEntry changed: $backStackEntry")
        val canGoBack = navController.previousBackStackEntry != null
        onBackStackChanged(canGoBack)
        onRouteChanged(backStackEntry)
    }


    // Handle navigation events
    LaunchedEffect(navigationState) {
        println("NavigationState changed: $navigationState")
        when (navigationState) {
            is NavigationState.Navigate -> {
                try {
                    navController.navigate(navigationState.destination)
                } catch (e: Exception) {
                    // Handle navigation errors
                    println("Navigation error: ${e.message}")
                }
                onEventHandled()
            }

            is NavigationState.NavigateBack -> {
                println("NavigateBack event received")
                if (navController.previousBackStackEntry != null) {
                    navController.popBackStack()
                }
                onEventHandled()
            }

            is NavigationState.Idle -> {
                // Do nothing
            }
        }
    }
}