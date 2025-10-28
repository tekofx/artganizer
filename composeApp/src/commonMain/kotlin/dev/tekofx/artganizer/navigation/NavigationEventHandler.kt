package dev.tekofx.artganizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.tekofx.artganizer.utils.AppLogger

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavigationEventHandler(
    navigationState: NavigationState,
    navController: NavHostController,
    onEventHandled: () -> Unit,
    onBackStackChanged: (Boolean) -> Unit
) {
    // Monitor back stack changes
    val backStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(backStackEntry) {
        AppLogger.d(
            "NavigationEventHandler",
            "BackStackEntry changed: ${backStackEntry?.destination}"
        )

        val canGoBack = navController.previousBackStackEntry != null
        onBackStackChanged(canGoBack)

    }


    // Handle navigation events
    LaunchedEffect(navigationState) {
        AppLogger.d("NavigationEventHandler", "NavigationState changed: $navigationState")
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