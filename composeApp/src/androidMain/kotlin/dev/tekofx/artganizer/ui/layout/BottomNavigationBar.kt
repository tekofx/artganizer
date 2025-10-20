package dev.tekofx.artganizer.ui.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.navigation.BottomNavigationItems
import dev.tekofx.artganizer.navigation.NavigationViewModel
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BottomNavigationBar() {
    val navigationViewModel = koinViewModel<NavigationViewModel>()


    val items = listOf(
        BottomNavigationItems.Gallery,
        BottomNavigationItems.Artists,
        BottomNavigationItems.Characters,
        BottomNavigationItems.Tags
    )

    navigationViewModel.navigationState

    BottomAppBar(
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .padding(0.dp)
            .height(60.dp)
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                val selected = navigationViewModel.currentDestination == item.route
                NavigationBarItem(
                    modifier = Modifier.height(30.dp),
                    selected = selected,
                    onClick = {
                        if (!selected) { // Avoid navigating to the same destination
                            navigationViewModel.navigateTo(item.route)
                        }
                    },
                    icon = {
                        if (selected) {

                            Icon(
                                painter = painterResource(item.selectedIcon),
                                contentDescription = item.title
                            )
                        } else {
                            Icon(
                                painter = painterResource(item.unselectedIcon),
                                contentDescription = item.title
                            )
                        }
                    },
                    label = {
                        if (selected) {
                            Text(
                                text = item.title,
                            )
                        }
                    })
            }
        }
    }
}
