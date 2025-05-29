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
import dev.tekofx.artganizer.HandleSharedLink
import dev.tekofx.artganizer.ui.screens.artists.ArtistCreationScreen
import dev.tekofx.artganizer.ui.screens.artists.ArtistDetailsScreen
import dev.tekofx.artganizer.ui.screens.artists.ArtistScreen
import dev.tekofx.artganizer.ui.screens.characters.CharacterCreationScreen
import dev.tekofx.artganizer.ui.screens.characters.CharacterDetailsScreen
import dev.tekofx.artganizer.ui.screens.characters.CharactersScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionCreationScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionDetailsScreen
import dev.tekofx.artganizer.ui.screens.submissions.SubmissionsScreen
import dev.tekofx.artganizer.ui.screens.tags.TagCreationScreen
import dev.tekofx.artganizer.ui.screens.tags.TagDetailsScreen
import dev.tekofx.artganizer.ui.screens.tags.TagsScreen
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun Navigation(
    navHostController: NavHostController,
    submissionsViewModel: SubmissionsViewModel,
    artistsViewModel: ArtistsViewModel,
    charactersViewModel: CharactersViewModel,
    tagsViewModel: TagsViewModel,
    sharedText: String?
) {
    val urlEncoded = if (sharedText == null) null else URLEncoder.encode(
        sharedText,
        StandardCharsets.UTF_8.toString()
    )

    NavHost(
        navController = navHostController,
        startDestination = if (urlEncoded != null) "handleSharedLink/${urlEncoded}" else NavigateDestinations.SUBMISSIONS_SCREEN
    ) {

        composable(
            route = "handleSharedLink/{sharedText}",
            arguments = listOf(navArgument("sharedText") { type = NavType.StringType })
        ) { backStackEntry ->
            val sharedText = backStackEntry.arguments?.getString("sharedText")?.let {
                URLDecoder.decode(it, StandardCharsets.UTF_8.toString())
            } ?: return@composable
            HandleSharedLink(sharedText, artistsViewModel, navHostController)
        }

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
                navHostController,
                submissionsViewModel,
                artistsViewModel,
                charactersViewModel
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
                navHostController,
                submissionsViewModel,
                artistsViewModel,
                charactersViewModel
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
            TagsScreen(navHostController, tagsViewModel)
        }

        composable(
            route = NavigateDestinations.TAG_DETAILS_SCREEN,
            arguments = listOf(navArgument("tagId") { type = NavType.StringType }),
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val tagId = backStackEntry.arguments?.getString("tagId")
            if (tagId == null) {
                navHostController.popBackStack()
                return@composable
            }
            tagsViewModel.getTagById(tagId.toLong())
            TagDetailsScreen(tagsViewModel, navHostController)
        }

        composable(
            route = NavigateDestinations.TAG_CREATION_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            TagCreationScreen(navHostController, tagsViewModel)
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
