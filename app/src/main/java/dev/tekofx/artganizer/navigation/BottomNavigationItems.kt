package dev.tekofx.artganizer.navigation

import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource

sealed class BottomNavigationItems(
    val title: String,
    val selectedIcon: IconResource,
    val unselectedIcon: IconResource,
    val route: String
) {
    data object Gallery : BottomNavigationItems(
        title = "Gallery",
        selectedIcon = IconResource.fromDrawableResource(R.drawable.gallery_filled),
        unselectedIcon = IconResource.fromDrawableResource(R.drawable.gallery_outlined),
        route = NavigateDestinations.GALLERY_SCREEN
    )

    data object Artists : BottomNavigationItems(
        title = "Artists",
        selectedIcon = IconResource.fromDrawableResource(R.drawable.palette_filled),
        unselectedIcon = IconResource.fromDrawableResource(R.drawable.palette_outlined),
        route = NavigateDestinations.ARTISTS_SCREEN
    )

    data object Characters : BottomNavigationItems(
        title = "Characters",
        selectedIcon = IconResource.fromDrawableResource(R.drawable.paw_filled),
        unselectedIcon = IconResource.fromDrawableResource(R.drawable.paw_outlined),
        route = NavigateDestinations.CHARACTERS_SCREEN
    )

    data object Tags : BottomNavigationItems(
        title = "Tags",
        selectedIcon = IconResource.fromDrawableResource(R.drawable.tag_filled),
        unselectedIcon = IconResource.fromDrawableResource(R.drawable.tag_outlined),
        route = NavigateDestinations.TAGS_SCREEN
    )
}