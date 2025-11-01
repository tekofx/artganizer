package dev.tekofx.artganizer.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.gallery_filled
import artganizer.composeapp.generated.resources.gallery_outlined
import artganizer.composeapp.generated.resources.palette_filled
import artganizer.composeapp.generated.resources.palette_outlined
import artganizer.composeapp.generated.resources.paw_filled
import artganizer.composeapp.generated.resources.paw_outlined
import artganizer.composeapp.generated.resources.tag_filled
import artganizer.composeapp.generated.resources.tag_outlined
import dev.tekofx.artganizer.navigation.ArtistsListRoute
import dev.tekofx.artganizer.navigation.CharactersListRoute
import dev.tekofx.artganizer.navigation.SubmissionsListRoute
import dev.tekofx.artganizer.navigation.TagsListRoute
import dev.tekofx.artganizer.navigation.getCurrentRoute
import dev.tekofx.artganizer.navigation.serialName
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun BottomNavBar(
    navController: NavHostController
) {
    val currentRoute = navController.getCurrentRoute()

    Surface(
        tonalElevation = 50.dp,
        shape = RoundedCornerShape(50)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            BottomNavItem(
                label = "Submissions",
                selectedIcon = Res.drawable.gallery_filled,
                unselectedIcon = Res.drawable.gallery_outlined,
                onClick = { navController.navigate(SubmissionsListRoute) },
                selected = SubmissionsListRoute.serialName() == currentRoute,
            )
            BottomNavItem(
                label = "Artists",
                selectedIcon = Res.drawable.palette_filled,
                unselectedIcon = Res.drawable.palette_outlined,
                onClick = { navController.navigate(ArtistsListRoute) },
                selected = ArtistsListRoute.serialName() == currentRoute,
            )
            BottomNavItem(
                label = "Submissions",
                selectedIcon = Res.drawable.paw_filled,
                unselectedIcon = Res.drawable.paw_outlined,
                onClick = { navController.navigate(CharactersListRoute) },
                selected = CharactersListRoute.serialName() == currentRoute,
            )
            BottomNavItem(
                label = "Submissions",
                selectedIcon = Res.drawable.tag_filled,
                unselectedIcon = Res.drawable.tag_outlined,
                onClick = { navController.navigate(TagsListRoute) },
                selected = TagsListRoute.serialName() == currentRoute,
            )
        }
    }
}

@Composable
fun BottomNavItem(
    label: String,
    selectedIcon: DrawableResource,
    unselectedIcon: DrawableResource,
    onClick: () -> Unit,
    selected: Boolean
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.height(40.dp),
            painter = painterResource(selectedIcon.takeIf { selected } ?: unselectedIcon),
            contentDescription = label
        )
    }
}