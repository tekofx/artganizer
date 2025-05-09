package dev.tekofx.artganizer.ui.components.artists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistDetails
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistUiState

@Composable
fun ArtistFrom(
    artistUiState: ArtistUiState,
    onItemValueChange: (ArtistDetails) -> Unit,
    onSaveClick: () -> Unit,
) {

    Column(
        modifier = Modifier.padding(10.dp)
    ) {

        ArtistFormFields(
            artistsDetails = artistUiState.artistDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = artistUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun ArtistFormFields(
    artistsDetails: ArtistDetails,
    modifier: Modifier = Modifier,
    onValueChange: (ArtistDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = artistsDetails.name,
            onValueChange = { onValueChange(artistsDetails.copy(name = it)) },
            label = { Text("Name") },

            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
}