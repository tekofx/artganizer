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
        //startDestination = if (urlEncoded != null) "handleSharedLink/${urlEncoded}" else "settings"
    ) {
        /* composable(
             route = "handleSharedLink/{sharedText}",
             arguments = listOf(navArgument("sharedText") { type = NavType.StringType })
         ) { backStackEntry ->
             val sharedText = backStackEntry.arguments?.getString("sharedText")?.let {
                 URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
             } ?: return@composable
             HandleSharedLink(sharedText, artistsViewModel, navHostController)
         }*/

        //submissionsGraph(navHostController)
        artistsGraph()
        //charactersGraph(navHostController)
        //tagsGraph(navHostController)

        composable(
            route = "settings"
        ) {
            SettingsScreen()
        }


    }
}


/*fun NavGraphBuilder.submissionsGraph(navController: NavHostController) {

    composable(
        route = NavigateDestinations.SUBMISSIONS_LIST,
        exitTransition = { fadeOut() }
    ) {
        SubmissionsScreen(navController)
    }

    composable(
        route = NavigateDestinations.SUBMISSION_CREATION,
        exitTransition = { fadeOut() }
    ) {
        SubmissionCreationScreen(navController)
    }

    composable(
        route = "${NavigateDestinations.SUBMISSION_DETAILS}/{submissionId}",
        arguments = listOf(navArgument("submissionId") { type = NavType.LongType }),
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val submissionId = backStackEntry.arguments?.getLong("submissionId")
        if (submissionId == null) {
            navController.popBackStack()
            return@composable
        }
        SubmissionDetailsScreen(submissionId, navController)
    }
}*/

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
        val artistId = backStackEntry.toRoute<ArtistDetailsRoute>()
        ArtistDetailsScreen(artistId)
    }
}


/*
fun NavGraphBuilder.charactersGraph(navController: NavHostController) {

    composable(
        route = NavigateDestinations.CHARACTERS_LIST,
        exitTransition = { fadeOut() }
    ) {
        CharactersScreen(navController)
    }

    composable(
        route = NavigateDestinations.CHARACTER_CREATION,
        exitTransition = { fadeOut() }
    ) {
        CharacterCreationScreen(navController)
    }

    composable(
        route = "${NavigateDestinations.CHARACTER_DETAILS}/{characterId}",
        arguments = listOf(navArgument("characterId") { type = NavType.StringType }),
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val characterId = backStackEntry.arguments?.getString("characterId")
        if (characterId == null) {
            navController.popBackStack()
            return@composable
        }
        CharacterDetailsScreen(characterId.toLong(), navController)
    }
}

fun NavGraphBuilder.tagsGraph(navController: NavHostController) {

    composable(
        route = NavigateDestinations.TAGS_LIST,
        exitTransition = { fadeOut() }
    ) {
        TagsScreen(navController)
    }

    composable(
        route = NavigateDestinations.TAG_CREATION,
        exitTransition = { fadeOut() }
    ) {
        TagCreationScreen(navController)
    }

    composable(
        route = "${NavigateDestinations.TAG_DETAILS}/{tagId}",
        arguments = listOf(navArgument("tagId") { type = NavType.StringType }),
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val tagId = backStackEntry.arguments?.getString("tagId")
        if (tagId == null) {
            navController.popBackStack()
            return@composable
        }
        TagDetailsScreen(tagId.toLong(), navController)
    }
}*/
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
            NavigationState.Idle -> {
                // Do nothing
            }
        }
    }
}