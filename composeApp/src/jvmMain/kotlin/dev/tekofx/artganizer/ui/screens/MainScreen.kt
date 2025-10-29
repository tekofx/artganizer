package dev.tekofx.artganizer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.components.layout.DesktopLayout
import dev.tekofx.artganizer.ui.components.layout.LeftPanel
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    navController: NavHostController
) {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val scope = rememberCoroutineScope()


    DesktopLayout(
        leftPanel = { LeftPanel(onArtistClick = {}) }
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