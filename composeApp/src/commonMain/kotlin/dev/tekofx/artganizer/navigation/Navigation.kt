package dev.tekofx.artganizer.navigation

import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
        onBackStackChanged = navigationViewModel::updateBackStackState
    )

    NavHost(
        navController = navHostController,
        startDestination = ArtistsListRoute
    ) {
        submissionsGraph()
        artistsGraph()
        charactersGraph()
        tagsGraph()

        composable(
            route = "settings"
        ) {
            SettingsScreen()
        }


    }
}


fun NavGraphBuilder.submissionsGraph() {

    composable<SubmissionsListRoute>(
        exitTransition = { fadeOut() }
    ) {
        SubmissionsScreen()
    }

    composable<SubmissionCreationRoute>(
        exitTransition = { fadeOut() }
    ) {
        SubmissionCreationScreen()
    }

    composable<SubmissionDetailsRoute>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val submissionDetailsRoute = backStackEntry.toRoute<SubmissionDetailsRoute>()
        SubmissionDetailsScreen(submissionDetailsRoute)
    }
}

fun NavGraphBuilder.artistsGraph() {

    composable<ArtistsListRoute>(
        exitTransition = { fadeOut() }
    ) {
        ArtistsScreen()
    }

    composable<ArtistCreationRoute>(
        exitTransition = { fadeOut() }
    ) {
        ArtistCreationScreen()
    }

    composable<ArtistDetailsRoute>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val artistDetailsRoute = backStackEntry.toRoute<ArtistDetailsRoute>()
        ArtistDetailsScreen(artistDetailsRoute)
    }
}


fun NavGraphBuilder.charactersGraph() {

    composable<CharactersListRoute>(
        exitTransition = { fadeOut() }
    ) {
        CharactersScreen()
    }

    composable<CharacterCreationRoute>(
        exitTransition = { fadeOut() }
    ) {
        CharacterCreationScreen()
    }

    composable<CharacterDetailsRoute>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val characterDetailsRoute = backStackEntry.toRoute<CharacterDetailsRoute>()

        CharacterDetailsScreen(characterDetailsRoute)
    }
}

fun NavGraphBuilder.tagsGraph() {

    composable<TagsListRoute>(

        exitTransition = { fadeOut() }
    ) {
        TagsScreen()
    }

    composable<TagCreationRoute>(
        exitTransition = { fadeOut() }
    ) {
        TagCreationScreen()
    }

    composable<TagDetailsRoute>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val tagDetailsRoute = backStackEntry.toRoute<TagDetailsRoute>()
        TagDetailsScreen(tagDetailsRoute)
    }
}

@Composable
private fun NavigationEventHandler(
    navigationState: NavigationState,
    navController: NavHostController,
    onEventHandled: () -> Unit,
    onBackStackChanged: (Boolean) -> Unit
) {
    // Monitor back stack changes
    val backStackEntry by navController.currentBackStackEntryAsState()

    LaunchedEffect(backStackEntry) {
        val canGoBack = navController.previousBackStackEntry != null
        onBackStackChanged(canGoBack)
    }

    // Handle navigation events
    LaunchedEffect(navigationState) {
        println(navigationState)
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