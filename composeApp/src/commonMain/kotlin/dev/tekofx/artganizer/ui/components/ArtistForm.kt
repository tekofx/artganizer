package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.input.SocialNetworkInput
import dev.tekofx.artganizer.ui.components.input.form.FormAvatar
import dev.tekofx.artganizer.ui.components.input.form.FormButtons
import dev.tekofx.artganizer.ui.components.input.form.FormTextfield
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistDetails
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistUiState
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.launch

@Composable
fun ArtistForm(
    artistUiState: ArtistUiState,
    onItemValueChange: (ArtistDetails) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {
            FormAvatar(
                fallbackText = artistUiState.artistDetails.name,
                artistUiState.artistDetails.imagePath,
                onImageSelect = {
                    scope.launch {
                        val imageFile = FileKit.openFilePicker(type = FileKitType.Image)
                        imageFile?.let {
                            onItemValueChange(
                                artistUiState.artistDetails.copy(
                                    imagePath = it.path
                                )
                            )
                        }
                    }
                },
            )

        }
        item {
            ArtistFormFields(
                artistsDetails = artistUiState.artistDetails,
                onValueChange = onItemValueChange,
            )
        }
        item {
            FormButtons(
                onSaveClick = onSaveClick,
                onCancelClick = onCancelClick,
                enabledSave = artistUiState.isEntryValid
            )
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

        FormTextfield(
            value = artistsDetails.name,
            onValueChange = { onValueChange(artistsDetails.copy(name = it)) },
            label = "Name",
            enabled = enabled
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