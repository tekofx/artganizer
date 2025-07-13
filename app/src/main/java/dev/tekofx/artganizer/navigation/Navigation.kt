package dev.tekofx.artganizer.navigation

import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun Navigation(
    navHostController: NavHostController,
    sharedText: String?
) {
    val urlEncoded = if (sharedText == null) null else URLEncoder.encode(
        sharedText,
        StandardCharsets.UTF_8.toString()
    )


    NavHost(
        navController = navHostController,
        startDestination = if (urlEncoded != null) "handleSharedLink/${urlEncoded}" else NavigateDestinations.SUBMISSIONS_ROOT
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

        submissionsGraph(navHostController)
        artistsGraph(navHostController)
        charactersGraph(navHostController)
        tagsGraph(navHostController)


    }
}


fun NavGraphBuilder.submissionsGraph(navController: NavHostController) {
    navigation(
        route = NavigateDestinations.SUBMISSIONS_ROOT,
        startDestination = NavigateDestinations.SUBMISSIONS_LIST

    ) {
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
            route = "details/{submissionId}",
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
    }
}

fun NavGraphBuilder.artistsGraph(navController: NavHostController) {
    navigation(
        route = NavigateDestinations.ARTISTS_ROOT,
        startDestination = NavigateDestinations.ARTISTS_LIST
    ) {
        composable(
            route = NavigateDestinations.ARTISTS_LIST,
            exitTransition = { fadeOut() }
        ) {
            ArtistScreen(navController)
        }

        composable(
            route = NavigateDestinations.ARTIST_CREATION,
            exitTransition = { fadeOut() }
        ) {
            ArtistCreationScreen(navController)
        }

        composable(
            route = "details/{artistId}",
            arguments = listOf(navArgument("artistId") { type = NavType.StringType }),
            exitTransition = { fadeOut() }
        ) { backStackEntry ->
            val artistId = backStackEntry.arguments?.getString("artistId")
            if (artistId == null) {
                navController.popBackStack()
                return@composable
            }
            ArtistDetailsScreen(artistId.toLong(), navController)
        }
    }
}


fun NavGraphBuilder.charactersGraph(navController: NavHostController) {
    navigation(
        route = NavigateDestinations.CHARACTERS_ROOT,
        startDestination = NavigateDestinations.CHARACTERS_LIST
    ) {
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
            route = "details/{characterId}",
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
}

fun NavGraphBuilder.tagsGraph(navController: NavHostController) {
    navigation(
        route = NavigateDestinations.TAGS_ROOT,
        startDestination = NavigateDestinations.TAGS_LIST
    ) {
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
            route = "details/{tagId}",
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
    }
}