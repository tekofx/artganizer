package dev.tekofx.artganizer.navigation

import androidx.compose.animation.fadeOut
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import dev.tekofx.artganizer.managers.UiStateManager
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
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.utils.AppLogger
import dev.tekofx.artganizer.utils.FIRST_ANDROID_ROUTE
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun Navigation() {

    val scope = rememberCoroutineScope()

    // Viewmodels
    val artistViewModel = koinViewModel<ArtistsViewModel>()
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()


    val uiStateManager = koinInject<UiStateManager>()

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
            "ArtistDetails  ${ArtistDetailsRoute.serializer().descriptor.serialName}"
        )

        AppLogger.d(
            "Navigation",
            "ArtistList  ${ArtistsListRoute.serialName()}"
        )
        AppLogger.d(
            "Navigation",
            "------"
        )
    }

    val showBottomNavBar = when (currentRoute2) {
        ArtistsListRoute.serialName() -> true
        SubmissionsListRoute.serialName() -> true
        TagsListRoute.serialName() -> true
        CharactersListRoute.serialName() -> true

        else -> false
    }
    val actions = when (currentRoute2) {
        ArtistsListRoute.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable {
                Action(icon = Res.drawable.add, onClick = {
                    navHostController.navigate(
                        ArtistCreationRoute
                    )
                })
            }
        )

        SubmissionsListRoute.serialName() -> listOf(
            @Composable {
                Action(
                    icon = Res.drawable.filter_outlined,
                    onClick = { /* refresh */ })
            },
            @Composable {
                Action(icon = Res.drawable.add, onClick = {
                    scope.launch {
                        val files = FileKit.openFilePicker(
                            mode = FileKitMode.Multiple(),
                            type = FileKitType.Image
                        )

                        files?.let {
                            uiStateManager.files.value = files
                        }
                        navHostController.navigate(
                            SubmissionCreationRoute
                        )
                    }
                })
            }
        )

        CharactersListRoute.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable {
                Action(icon = Res.drawable.add, onClick = {
                    navHostController.navigate(
                        CharacterCreationRoute
                    )
                })
            }
        )

        TagsListRoute.serialName() -> listOf(
            @Composable { Action(icon = Res.drawable.search, onClick = { /* refresh */ }) },
            @Composable {
                Action(icon = Res.drawable.add, onClick = {
                    navHostController.navigate(
                        TagCreationRoute
                    )
                })
            }
        )

        else -> {
            emptyList()
        }
    }

    val actions2 = when {
        navBackStackEntry?.destination?.hasRoute<ArtistDetailsRoute>() ?: false -> listOf(
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

        navBackStackEntry?.destination?.hasRoute<SubmissionDetailsRoute>() ?: false -> listOf(
            @Composable {
                Action(icon = Res.drawable.share, onClick = {

                })
            },
            @Composable {
                Action(icon = Res.drawable.edit, onClick = {
                    submissionsViewModel.setShowEditSubmission(true)
                })
            },
            @Composable {
                Action(
                    icon = Res.drawable.trash,
                    onClick = {
                        submissionsViewModel.setShowDeletePopup(true)
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
            startDestination = FIRST_ANDROID_ROUTE
        ) {
            artistsGraph(navHostController)
            charactersGraph()
            submissionsGraph(
                onSubmissionClick = {
                    navHostController.navigate(SubmissionDetailsRoute(it))
                },
                navigateBack = { navHostController.popBackStack() }
            )
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

    composable<ArtistsListRoute>(
        exitTransition = { fadeOut() }
    ) {
        ArtistsScreen(onArtistClick = { artistId ->
            navController.navigate(ArtistDetailsRoute(artistId))
        })
    }

    composable<ArtistCreationRoute>(
        exitTransition = { fadeOut() }
    ) {
        ArtistCreationScreen(navController)
    }

    composable<ArtistDetailsRoute>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val artistDetailsRoute = backStackEntry.toRoute<ArtistDetailsRoute>()
        ArtistDetailsScreen(artistDetailsRoute, navController)
    }
}

fun NavGraphBuilder.submissionsGraph(
    onSubmissionClick: (Long) -> Unit,
    navigateBack: () -> Unit
) {

    composable<SubmissionsListRoute>(
        exitTransition = { fadeOut() }
    ) {
        SubmissionsScreen(onSubmissionClick)
    }

    composable<SubmissionCreationRoute>(
        exitTransition = { fadeOut() }
    ) {
        SubmissionCreationScreen(
            onSaveClick = navigateBack,
            onCancelClick = navigateBack
        )
    }

    composable<SubmissionDetailsRoute>(
        exitTransition = { fadeOut() }
    ) { backStackEntry ->
        val submissionDetailsRoute = backStackEntry.toRoute<SubmissionDetailsRoute>()
        SubmissionDetailsScreen(submissionDetailsRoute)
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