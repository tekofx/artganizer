package dev.tekofx.artganizer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.R

sealed class BottomNavigationItems(
    val title: String,
    val selectedIcon: IconResource,
    val unselectedIcon: IconResource,
    val route: String
) {
    data object Gallery : BottomNavigationItems(
        title = "Gallery",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.Home),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.Home),
        route = NavigateDestinations.GALLERY_SCREEN
    )

    data object Artists : BottomNavigationItems(
        title = "Artists",
        selectedIcon = IconResource.fromImageVector(Icons.Filled.Call),
        unselectedIcon = IconResource.fromImageVector(Icons.Outlined.Call),
        route = NavigateDestinations.ARTISTS_SCREEN
    )
}