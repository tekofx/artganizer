package dev.tekofx.artganizer.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.tekofx.artganizer.ui.screens.TagsScreen
import dev.tekofx.artganizer.ui.screens.artists.ArtistCreationScreen
import dev.tekofx.artganizer.ui.screens.artists.ArtistDetailsScreen
import dev.tekofx.artganizer.ui.screens.artists.ArtistScreen
import dev.tekofx.artganizer.ui.screens.characters.CharacterCreationScreen
import dev.tekofx.artganizer.ui.screens.characters.CharacterDetailsScreen
import dev.tekofx.artganizer.ui.screens.characters.CharactersScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionCreationScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionDetailsScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionsScreen
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel


@Composable
fun Navigation(
    navHostController: NavHostController,
    submissionsViewModel: SubmissionsViewModel,
    artistsViewModel: ArtistsViewModel,
    charactersViewModel: CharactersViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = NavigateDestinations.SUBMISSIONS_SCREEN
    ) {

        // Submissions
        composable(
            route = NavigateDestinations.SUBMISSIONS_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            SubmissionsScreen(navHostController, submissionsViewModel)
        }

        composable(
            route = NavigateDestinations.SUBMISSION_CREATION_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            SubmissionCreationScreen(
                submissionsViewModel,
                artistsViewModel,
                navHostController
            )
        }

        composable(
            route = NavigateDestinations.SUBMISSION_DETAILS_SCREEN,
            arguments = listOf(navArgument("submissionId") { type = NavType.StringType }),
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val submissionId = backStackEntry.arguments?.getString("submissionId")
            if (submissionId == null) {
                navHostController.popBackStack()
                return@composable
            }
            submissionsViewModel.getSubmissionWithArtist(submissionId.toLong())
            SubmissionDetailsScreen(
                submissionsViewModel,
                artistsViewModel,
                navHostController
            )
        }

        // Artists
        composable(
            route = NavigateDestinations.ARTISTS_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            ArtistScreen(navHostController, artistsViewModel)
        }

        composable(
            route = NavigateDestinations.ARTIST_CREATION_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            ArtistCreationScreen(artistsViewModel, navHostController)
        }

        composable(
            route = NavigateDestinations.ARTIST_DETAILS_SCREEN,
            arguments = listOf(navArgument("artistId") { type = NavType.StringType }),
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val artistId = backStackEntry.arguments?.getString("artistId")
            if (artistId == null) {
                navHostController.popBackStack()
                return@composable
            }
            artistsViewModel.getArtistWithSubmissions(artistId.toInt())
            ArtistDetailsScreen(artistsViewModel, navHostController)
        }


        // Characters
        composable(
            route = NavigateDestinations.CHARACTERS_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            CharactersScreen(charactersViewModel, navHostController)
        }

        composable(
            route = NavigateDestinations.CHARACTER_CREATION_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            CharacterCreationScreen(charactersViewModel, navHostController)
        }

        composable(
            route = NavigateDestinations.CHARACTER_DETAILS_SCREEN,
            arguments = listOf(navArgument("characterId") { type = NavType.StringType }),
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val characterId = backStackEntry.arguments?.getString("characterId")
            if (characterId == null) {
                navHostController.popBackStack()
                return@composable
            }
            charactersViewModel.getCharacterWithSubmission(characterId.toLong())
            CharacterDetailsScreen(charactersViewModel, navHostController)
        }

        // Tags
        composable(
            route = NavigateDestinations.TAGS_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            TagsScreen()
        }


    }
}

// SlideIn Transitions
fun slideInToTop(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Up,
    )
}

fun slideInToBottom(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
    )
}


fun slideInToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
    )
}

fun slideInToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): EnterTransition {
    return scope.slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
    )
}

// SlideOut Transitions

fun slideOutToBottom(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
    )
}

fun slideOutToRight(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Right,
    )
}

fun slideOutToLeft(scope: AnimatedContentTransitionScope<NavBackStackEntry>): ExitTransition {
    return scope.slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Left,
    )
}
