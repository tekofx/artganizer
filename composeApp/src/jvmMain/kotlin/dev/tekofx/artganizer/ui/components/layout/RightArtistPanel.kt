package dev.tekofx.artganizer.ui.components.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import coil3.compose.AsyncImage
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RightArtistPanel() {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val currentArtist = artistsViewModel.currentArtistUiState

    Column {
        AsyncImage(model = currentArtist.artistDetails.imagePath, contentDescription = "")
        Text(currentArtist.artistDetails.name)
    }

}