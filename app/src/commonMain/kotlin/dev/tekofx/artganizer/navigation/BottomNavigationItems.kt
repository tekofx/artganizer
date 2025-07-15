package dev.tekofx.artganizer.navigation

import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.gallery_filled
import artganizer.app.generated.resources.gallery_outlined
import artganizer.app.generated.resources.palette_filled
import artganizer.app.generated.resources.palette_outlined
import artganizer.app.generated.resources.paw_filled
import artganizer.app.generated.resources.paw_outlined
import artganizer.app.generated.resources.tag_filled
import artganizer.app.generated.resources.tag_outlined
import org.jetbrains.compose.resources.DrawableResource

sealed class BottomNavigationItems(
    val title: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
    val route: String
) {
    data object Gallery : BottomNavigationItems(
        title = "Gallery",
        selectedIcon = Res.drawable.gallery_filled,
        unselectedIcon = Res.drawable.gallery_outlined,
        route = NavigateDestinations.SUBMISSIONS_LIST
    )

    data object Artists : BottomNavigationItems(
        title = "Artists",
        selectedIcon = Res.drawable.palette_filled,
        unselectedIcon = Res.drawable.palette_outlined,
        route = NavigateDestinations.ARTISTS_LIST
    )

    data object Characters : BottomNavigationItems(
        title = "Characters",
        selectedIcon = Res.drawable.paw_filled,
        unselectedIcon = Res.drawable.paw_outlined,
        route = NavigateDestinations.CHARACTERS_LIST
    )

    data object Tags : BottomNavigationItems(
        title = "Tags",
        selectedIcon = Res.drawable.tag_filled,
        unselectedIcon = Res.drawable.tag_outlined,
        route = NavigateDestinations.TAGS_LIST
    )
}