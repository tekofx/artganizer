package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.ArtistForm
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ArtistCreationScreen(
    navHostController: NavHostController,
) {

    val artistsViewModel = koinViewModel<ArtistsViewModel>()

    LaunchedEffect(Unit) {
        artistsViewModel.clearNewUiState()
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ArtistForm(
            artistsViewModel.newArtistUiState,
            onItemValueChange = { newValue -> artistsViewModel.updateNewUiState(newValue) },
            onSaveClick = {
                navHostController.popBackStack()
                artistsViewModel.saveArtist()
            },
            onCancelClick = {
                navHostController.popBackStack()
            }
        )
    }
}