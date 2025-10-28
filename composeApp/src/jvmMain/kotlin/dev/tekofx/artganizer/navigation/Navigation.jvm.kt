package dev.tekofx.artganizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionsScreen
import dev.tekofx.artganizer.utils.FIRST_ROUTE
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun Navigation() {
    val navHostController = rememberNavController()
    val navigationViewModel = koinViewModel<NavigationViewModel>()
    val navigationState by navigationViewModel.navigationState.collectAsState()
    NavigationEventHandler(
        navigationState = navigationState,
        navController = navHostController,
        onEventHandled = navigationViewModel::clearNavigation,
        onRouteChanged = navigationViewModel::updateCurrentRoute,
        onBackStackChanged = navigationViewModel::updateBackStackState,
    )

    NavHost(
        navController = navHostController,
        startDestination = FIRST_ROUTE
    ) {
        composable<AppRoute.SubmissionsList> {
            SubmissionsScreen()
        }
    }
}