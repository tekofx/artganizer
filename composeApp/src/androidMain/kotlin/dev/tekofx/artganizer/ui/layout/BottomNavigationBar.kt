package dev.tekofx.artganizer.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.add
import dev.tekofx.artganizer.navigation.BottomNavigationItems
import dev.tekofx.artganizer.navigation.NavigationViewModel
import dev.tekofx.artganizer.utils.AppLogger
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BottomNavigationBar(
    firstButtonIcon: DrawableResource,
    onFirstButtonClick: () -> Unit,
    onAddButtonClick: () -> Unit = {}
) {
    val navigationViewModel = koinViewModel<NavigationViewModel>()
    val routesList by navigationViewModel.routesList.collectAsState()


    val items = listOf(
        BottomNavigationItems.Gallery,
        BottomNavigationItems.Artists,
        BottomNavigationItems.Characters,
        BottomNavigationItems.Tags
    )



    BottomAppBar(
        contentPadding = PaddingValues(0.dp),
        containerColor = Color.Transparent,
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
            .height(70.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    items.forEach { item ->
                        AppLogger.d("BottomNavigationBar", "routesList $routesList")

                        AppLogger.d("BottomNavigationBar", "last routesList ${routesList.last()}")
                        AppLogger.d("BottomNavigationBar", "item.route ${item.route}")
                        AppLogger.d("BottomNavigationBar", "\n")

                        val selected =
                            routesList.last() == item.route
                        IconButton(
                            onClick = {
                                navigationViewModel.navigateTo(item.route)
                            }
                        ) {
                            if (selected) {
                                Icon(
                                    modifier = Modifier.height(40.dp),
                                    painter = painterResource(item.selectedIcon),
                                    contentDescription = item.title
                                )
                            } else {
                                Icon(
                                    modifier = Modifier.height(40.dp),
                                    painter = painterResource(item.unselectedIcon),
                                    contentDescription = item.title
                                )
                            }
                        }
                    }
                }
            }
            Row {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = onFirstButtonClick
                ) {
                    Icon(
                        contentDescription = "",
                        painter = painterResource(firstButtonIcon),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = onAddButtonClick
                ) {
                    Icon(
                        contentDescription = "",
                        painter = painterResource(Res.drawable.add),
                        tint = MaterialTheme.colorScheme.onPrimaryContainer

                    )
                }
            }
        }
    }
}



