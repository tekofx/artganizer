package dev.tekofx.artganizer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavHostController.getCurrentRoute(): String? {
    return this.currentBackStackEntryAsState().value?.destination?.route
}


fun showBottomAppBar(currentRoute: String?): Boolean {
    if (currentRoute == null) {
        return false
    }

    return currentRoute.contains("/list")
}