package dev.tekofx.artganizer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.components.layout.DesktopLayout
import dev.tekofx.artganizer.ui.components.layout.LeftPanel
import dev.tekofx.artganizer.ui.components.layout.RightArtistPanel
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val scope = rememberCoroutineScope()
    var isEnabled by remember { mutableStateOf(true) }

    DesktopLayout(
        leftPanel = {
            LeftPanel(onArtistClick = {
                artistsViewModel.getArtistWithSubmissions(it)
            })
        },
        rightPanel = {
            if (isEnabled) {
                RightArtistPanel()
            }
        }
    ) {
        ArtistForm(
            artistUiState = artistsViewModel.newArtistUiState,
            onItemValueChange = { newValue -> artistsViewModel.updateNewUiState(newValue) },
            onSaveClick = {
                scope.launch {
                    artistsViewModel.saveArtist()
                    navController.popBackStack()
                }
            },
            onCancelClick = {
                navController.popBackStack()
            }
        )

    }

}

