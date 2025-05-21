package dev.tekofx.artganizer.ui.components.artists

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.input.FormAvatar
import dev.tekofx.artganizer.ui.components.input.SocialNetworkInput
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistDetails
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistUiState
import kotlinx.coroutines.launch

@Composable
fun ArtistForm(
    artistUiState: ArtistUiState,
    onItemValueChange: (ArtistDetails) -> Unit,
    onSaveClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), onResult = { uri: Uri? ->
            uri?.let {
                // Save the image and navigate to the next screen
                scope.launch {
                    onItemValueChange(
                        artistUiState.artistDetails.copy(
                            imagePath = uri
                        )
                    )
                }
            }
        }
    )

    LazyColumn(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )


    {
        item {
            FormAvatar(
                fallbackText = artistUiState.artistDetails.name,
                artistUiState.artistDetails.imagePath,
                onImageSelect = { launcher.launch("image/*") })
        }

        item {
            ArtistFormFields(
                artistsDetails = artistUiState.artistDetails,
                onValueChange = onItemValueChange,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
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
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = artistsDetails.name,
            onValueChange = { onValueChange(artistsDetails.copy(name = it)) },
            label = { Text("Name") },

            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        SocialNetworkInput(
            socialNetworks = artistsDetails.socialNetworks,
            onAddSocialNetwork = {
                onValueChange(
                    artistsDetails.copy(
                        socialNetworks = artistsDetails.socialNetworks + it
                    )
                )
            },
            onRemoveSocialNetwork = {
                onValueChange(
                    artistsDetails.copy(
                        socialNetworks = artistsDetails.socialNetworks - it
                    )
                )
            },
        )

    }
}