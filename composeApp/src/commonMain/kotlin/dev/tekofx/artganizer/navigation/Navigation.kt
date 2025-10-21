package dev.tekofx.artganizer.navigation

import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.tekofx.artganizer.ui.screens.SettingsScreen
import dev.tekofx.artganizer.ui.screens.artists.ArtistCreationScreen
import dev.tekofx.artganizer.ui.screens.artists.ArtistDetailsScreen
import dev.tekofx.artganizer.ui.screens.artists.ArtistsScreen
import dev.tekofx.artganizer.ui.screens.characters.CharacterCreationScreen
import dev.tekofx.artganizer.ui.screens.characters.CharacterDetailsScreen
import dev.tekofx.artganizer.ui.screens.characters.CharactersScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionCreationScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionDetailsScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionsScreen
import dev.tekofx.artganizer.ui.screens.tags.TagCreationScreen
import dev.tekofx.artganizer.ui.screens.tags.TagDetailsScreen
import dev.tekofx.artganizer.ui.screens.tags.TagsScreen
import dev.tekofx.artganizer.utils.FIRST_ROUTE
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation() {
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
        artistsGraph()
        charactersGraph()
        submissionsGraph()
        tagsGraph()

        composable(
            route = "settings"
        ) {
            SettingsScreen()
        }


    }
}

fun NavGraphBuilder.artistsGraph() {

    composable<AppRoute.ArtistsList>(
        exitTransition = { fadeOut() }
    ) {
        ArtistsScreen()
    }

    composable<AppRoute.ArtistCreation>(
        exitTransition = { fadeOut() }
    ) {
        ArtistCreationScreen()
    }

    composable<AppRoute.ArtistDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val artistDetailsRoute = backStackEntry.toRoute<AppRoute.ArtistDetails>()
        ArtistDetailsScreen(artistDetailsRoute)
    }
}

fun NavGraphBuilder.submissionsGraph() {

    composable<AppRoute.SubmissionsList>(
        exitTransition = { fadeOut() }
    ) {
        SubmissionsScreen()
    }

    composable<AppRoute.SubmissionCreation>(
        exitTransition = { fadeOut() }
    ) {
        SubmissionCreationScreen()
    }

    composable<AppRoute.SubmissionDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val submissionDetailsRoute = backStackEntry.toRoute<AppRoute.SubmissionDetails>()
        SubmissionDetailsScreen(submissionDetailsRoute)
    }
}


fun NavGraphBuilder.charactersGraph() {

    composable<AppRoute.CharactersList>(
        exitTransition = { fadeOut() }
    ) {
        CharactersScreen()
    }

    composable<AppRoute.CharacterCreation>(
        exitTransition = { fadeOut() }
    ) {
        CharacterCreationScreen()
    }

    composable<AppRoute.CharacterDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val characterDetailsRoute = backStackEntry.toRoute<AppRoute.CharacterDetails>()

        CharacterDetailsScreen(characterDetailsRoute)
    }
}

fun NavGraphBuilder.tagsGraph() {

    composable<AppRoute.TagsList>(

        exitTransition = { fadeOut() }
    ) {
        TagsScreen()
    }

    composable<AppRoute.TagCreation>(
        exitTransition = { fadeOut() }
    ) {
        TagCreationScreen()
    }

    composable<AppRoute.TagDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val tagDetailsRoute = backStackEntry.toRoute<AppRoute.TagDetails>()
        TagDetailsScreen(tagDetailsRoute)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun NavigationEventHandler(
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