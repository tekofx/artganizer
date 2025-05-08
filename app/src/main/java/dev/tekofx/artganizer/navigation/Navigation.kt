package dev.tekofx.artganizer.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.tekofx.artganizer.ui.screens.ArtistScreen
import dev.tekofx.artganizer.ui.screens.CharactersScreen
import dev.tekofx.artganizer.ui.screens.GalleryScreen
import dev.tekofx.artganizer.ui.screens.SubmissionCreationScreen
import dev.tekofx.artganizer.ui.screens.TagsScreen
import dev.tekofx.artganizer.ui.viewmodels.gallery.GalleryViewModel


@Composable
fun Navigation(
    navHostController: NavHostController,
    galleryViewModel: GalleryViewModel
) {
    NavHost(
        navController = navHostController,
        startDestination = NavigateDestinations.GALLERY_SCREEN
    ) {
        composable(
            route = NavigateDestinations.GALLERY_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            GalleryScreen(navHostController, galleryViewModel)
        }

        composable(
            route = NavigateDestinations.ARTISTS_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            ArtistScreen()
        }
        composable(
            route = NavigateDestinations.CHARACTERS_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            CharactersScreen()
        }
        composable(
            route = NavigateDestinations.TAGS_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            TagsScreen()
        }

        composable(
            route = NavigateDestinations.SUBMISSION_CREATION_SCREEN,
            exitTransition = { fadeOut() }
        ) {
            SubmissionCreationScreen()
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
