package dev.tekofx.artganizer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun currentRoute(navController: NavController): String? =
    navController.currentBackStackEntryAsState().value?.destination?.route


fun showBottomAppBar(currentRoute: String?): Boolean {
    if (currentRoute == null) {
        return false
    }

    return when (currentRoute) {
        NavigateDestinations.SUBMISSIONS_SCREEN -> true
        NavigateDestinations.ARTISTS_SCREEN -> true
        NavigateDestinations.CHARACTERS_SCREEN -> true
        NavigateDestinations.TAGS_SCREEN -> true
        else -> false
    }

}