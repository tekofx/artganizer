package dev.tekofx.artganizer.navigation

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.add
import artganizer.composeapp.generated.resources.filter_outlined
import artganizer.composeapp.generated.resources.search
import dev.tekofx.artganizer.ui.layout.Action
import dev.tekofx.artganizer.ui.layout.BottomAppBarScaffold
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

@Composable
actual fun Navigation() {
    val navHostController = rememberNavController()


    val showNavBar = when (navHostController.getCurrentRoute()) {
        ArtistsList.serialName() -> true
        SubmissionsList.serialName() -> true
        TagsList.serialName() -> true
        CharactersList.serialName() -> true

        else -> false
    }


    val actions = when (navHostController.getCurrentRoute()) {
        ArtistsList.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable { Action(icon = Res.drawable.add, onClick = { /* refresh */ }) }
        )

        SubmissionsList.serialName() -> listOf(
            @Composable {
                Action(
                    icon = Res.drawable.filter_outlined,
                    onClick = { /* refresh */ })
            },
            @Composable { Action(icon = Res.drawable.add, onClick = { /* refresh */ }) }
        )

        CharactersList.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable { Action(icon = Res.drawable.add, onClick = { /* refresh */ }) }
        )

        TagsList.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable { Action(icon = Res.drawable.add, onClick = { /* refresh */ }) }
        )

        else -> {
            emptyList()
        }
    }


    BottomAppBarScaffold(
        textFieldState = rememberTextFieldState(),
        onFocusChanged = {},
        navController = navHostController,
        actions = actions
    ) {
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
}

fun NavGraphBuilder.artistsGraph() {

    composable<ArtistsList>(
        exitTransition = { fadeOut() }
    ) {
        ArtistsScreen()
    }

    composable<ArtistCreation>(
        exitTransition = { fadeOut() }
    ) {
        ArtistCreationScreen()
    }

    composable<ArtistDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val artistDetailsRoute = backStackEntry.toRoute<ArtistDetails>()
        ArtistDetailsScreen(artistDetailsRoute)
    }
}

fun NavGraphBuilder.submissionsGraph() {

    composable<SubmissionsList>(
        exitTransition = { fadeOut() }
    ) {
        SubmissionsScreen()
    }

    composable<SubmissionCreation>(
        exitTransition = { fadeOut() }
    ) {
        SubmissionCreationScreen()
    }

    composable<SubmissionDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val submissionDetailsRoute = backStackEntry.toRoute<SubmissionDetails>()
        SubmissionDetailsScreen(submissionDetailsRoute)
    }
}


fun NavGraphBuilder.charactersGraph() {

    composable<CharactersList>(
        exitTransition = { fadeOut() }
    ) {
        CharactersScreen()
    }

    composable<CharacterCreation>(
        exitTransition = { fadeOut() }
    ) {
        CharacterCreationScreen()
    }

    composable<CharacterDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val characterDetailsRoute = backStackEntry.toRoute<CharacterDetails>()

        CharacterDetailsScreen(characterDetailsRoute)
    }
}

fun NavGraphBuilder.tagsGraph() {

    composable<TagsList>(

        exitTransition = { fadeOut() }
    ) {
        TagsScreen()
    }

    composable<TagCreation>(
        exitTransition = { fadeOut() }
    ) {
        TagCreationScreen()
    }

    composable<TagDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val tagDetailsRoute = backStackEntry.toRoute<TagDetails>()
        TagDetailsScreen(tagDetailsRoute)
    }
}