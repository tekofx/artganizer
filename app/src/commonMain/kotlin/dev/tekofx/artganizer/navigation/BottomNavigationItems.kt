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
import dev.tekofx.artganizer.ui.IconResource

sealed class BottomNavigationItems(
    val title: String,
    val selectedIcon: IconResource,
    val unselectedIcon: IconResource,
    val route: String
) {
    data object Gallery : BottomNavigationItems(
        title = "Gallery",
        selectedIcon = IconResource.fromDrawableResource(Res.drawable.gallery_filled),
        unselectedIcon = IconResource.fromDrawableResource(Res.drawable.gallery_outlined),
        route = NavigateDestinations.SUBMISSIONS_LIST
    )

    data object Artists : BottomNavigationItems(
        title = "Artists",
        selectedIcon = IconResource.fromDrawableResource(Res.drawable.palette_filled),
        unselectedIcon = IconResource.fromDrawableResource(Res.drawable.palette_outlined),
        route = NavigateDestinations.ARTISTS_LIST
    )

    data object Characters : BottomNavigationItems(
        title = "Characters",
        selectedIcon = IconResource.fromDrawableResource(Res.drawable.paw_filled),
        unselectedIcon = IconResource.fromDrawableResource(Res.drawable.paw_outlined),
        route = NavigateDestinations.CHARACTERS_LIST
    )

    data object Tags : BottomNavigationItems(
        title = "Tags",
        selectedIcon = IconResource.fromDrawableResource(Res.drawable.tag_filled),
        unselectedIcon = IconResource.fromDrawableResource(Res.drawable.tag_outlined),
        route = NavigateDestinations.TAGS_LIST
    )
}