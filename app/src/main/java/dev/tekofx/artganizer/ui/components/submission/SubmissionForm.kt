package dev.tekofx.artganizer.ui.components.submission

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.EntitySelect
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails

@Composable
fun SubmissionsForm(
    uris: List<Uri>,
    artistsViewModel: ArtistsViewModel,
    submissionDetails: SubmissionDetails,
    onItemValueChange: (SubmissionDetails) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit = {},
) {
    val queryText by artistsViewModel.queryText.collectAsState()
    val artists by artistsViewModel.artists.collectAsState()
    val areThereArtists by artistsViewModel.areThereArtists

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uris.size == 1) {
            SubmissionViewer(
                submissionDetails.title,
                uris,
                currentImage = 0,
                onImageChange = {}
            )
        }

        if (uris.size > 1) {
            Text("Multiple images")
        }


        SubmissionFormFields(
            submissionDetails = submissionDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        if (areThereArtists) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Artist", style = MaterialTheme.typography.headlineSmall)
                EntitySelect(
                    title = "Select an Artist",
                    selectedItem = submissionDetails.artist,
                    query = queryText,
                    onQueryChange = { artistsViewModel.onSearchTextChanged(it) },
                    items = artists,
                    labelMapper = { it.name },
                    imageMapper = { it.imagePath },
                    onItemSelected = { selectedItem ->
                        onItemValueChange(
                            submissionDetails.copy(
                                artistId = selectedItem.id,
                                artist = selectedItem
                            )
                        )
                    },
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ButtonWithIcon(
                onClick = onSaveClick,
                text = "Save",
                iconResource = IconResource.fromDrawableResource(R.drawable.device_floppy),
            )
            ButtonWithIcon(
                onClick = onCancelClick,
                text = "Cancel",
                iconResource = IconResource.fromDrawableResource(R.drawable.cancel),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
fun SubmissionFormFields(
    submissionDetails: SubmissionDetails,
    modifier: Modifier = Modifier,
    onValueChange: (SubmissionDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = submissionDetails.title,
            onValueChange = { onValueChange(submissionDetails.copy(title = it)) },
            label = { Text("Title") },

            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = submissionDetails.description,
            onValueChange = { onValueChange(submissionDetails.copy(description = it)) },
            label = { Text("Description") },

            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        Rating(
            rating = submissionDetails.rating,
            onRatingChange = { onValueChange(submissionDetails.copy(rating = it)) },
        )
    }
}