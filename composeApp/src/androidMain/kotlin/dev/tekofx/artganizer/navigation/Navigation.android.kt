package dev.tekofx.artganizer.navigation

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.add
import artganizer.composeapp.generated.resources.edit
import artganizer.composeapp.generated.resources.filter_outlined
import artganizer.composeapp.generated.resources.search
import artganizer.composeapp.generated.resources.share
import artganizer.composeapp.generated.resources.trash
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
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.utils.AppLogger
import dev.tekofx.artganizer.utils.FIRST_ROUTE
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun Navigation() {
    // Viewmodels
    val artistViewModel = koinViewModel<ArtistsViewModel>()

    // Routing
    val navHostController = rememberNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.toRoute<AppRoute>()
    val currentRoute2 = navHostController.getCurrentRoute()

    LaunchedEffect(currentRoute) {
        AppLogger.d("Navigation", "currentRoute ${currentRoute?.serialName()}")

        AppLogger.d(
            "Navigation",
            "navHostController.getCurrentRoute() $currentRoute2"
        )

        AppLogger.d(
            "Navigation",
            "ArtistDetails  ${ArtistDetails.serializer().descriptor.serialName}"
        )

        AppLogger.d(
            "Navigation",
            "ArtistList  ${ArtistsList.serialName()}"
        )
        AppLogger.d(
            "Navigation",
            "------"
        )
    }

    val showBottomNavBar = when (currentRoute2) {
        ArtistsList.serialName() -> true
        SubmissionsList.serialName() -> true
        TagsList.serialName() -> true
        CharactersList.serialName() -> true

        else -> false
    }
    val actions = when (currentRoute2) {
        ArtistsList.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable {
                Action(icon = Res.drawable.add, onClick = {
                    navHostController.navigate(
                        ArtistCreation
                    )
                })
            }
        )

        SubmissionsList.serialName() -> listOf(
            @Composable {
                Action(
                    icon = Res.drawable.filter_outlined,
                    onClick = { /* refresh */ })
            },
            @Composable {
                Action(icon = Res.drawable.add, onClick = {
                    navHostController.navigate(
                        SubmissionCreation
                    )
                })
            }
        )

        CharactersList.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable {
                Action(icon = Res.drawable.add, onClick = {
                    navHostController.navigate(
                        CharacterCreation
                    )
                })
            }
        )

        TagsList.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable {
                Action(icon = Res.drawable.add, onClick = {
                    navHostController.navigate(
                        TagCreation
                    )
                })
            }
        )

        else -> {
            emptyList()
        }
    }

    val actions2 = when {
        navBackStackEntry?.destination?.hasRoute<ArtistDetails>() ?: false -> listOf(
            @Composable {
                Action(icon = Res.drawable.share, onClick = {

                })
            },
            @Composable {
                Action(icon = Res.drawable.edit, onClick = {
                    artistViewModel.setShowEditArtist(true)
                })
            },
            @Composable {
                Action(
                    icon = Res.drawable.trash,
                    onClick = {
                        artistViewModel.setShowDeletePopup(true)
                    },
                    containerColor = MaterialTheme.colorScheme.error,
                    onContainerColor = MaterialTheme.colorScheme.onErrorContainer
                )
            }

        )

        else -> emptyList()
    }


    BottomAppBarScaffold(
        textFieldState = rememberTextFieldState(),
        onFocusChanged = {},
        navController = navHostController,
        actions = actions + actions2,
        showBottomNavBar = showBottomNavBar
    ) {
        NavHost(
            navController = navHostController,
            startDestination = FIRST_ROUTE
        ) {
            artistsGraph(navHostController)
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

fun NavGraphBuilder.artistsGraph(navController: NavHostController) {

    composable<ArtistsList>(
        exitTransition = { fadeOut() }
    ) {
        ArtistsScreen(onArtistClick = { artistId ->
            navController.navigate(ArtistDetails(artistId))
        })
    }

    composable<ArtistCreation>(
        exitTransition = { fadeOut() }
    ) {
        ArtistCreationScreen(navController)
    }

    composable<ArtistDetails>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val artistDetailsRoute = backStackEntry.toRoute<ArtistDetails>()
        ArtistDetailsScreen(artistDetailsRoute, navController)
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