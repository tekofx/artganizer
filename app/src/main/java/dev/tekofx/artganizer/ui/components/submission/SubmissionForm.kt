package dev.tekofx.artganizer.ui.components.submission

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import dev.tekofx.artganizer.ui.components.input.ArtistSelect
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.CharactersSelect
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.submissions.toListOfCharacters

@Composable
fun SubmissionsForm(
    uris: List<Uri>,
    artistsViewModel: ArtistsViewModel,
    submissionDetails: SubmissionDetails,
    charactersViewModel: CharactersViewModel,
    onItemValueChange: (SubmissionDetails) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit = {},
) {
    val queryText by artistsViewModel.queryText.collectAsState()
    val artists by artistsViewModel.artists.collectAsState()
    val characters by charactersViewModel.characters.collectAsState()
    val areThereArtists by artistsViewModel.areThereArtists
    val areThereCharacters by charactersViewModel.areThereCharacters
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SubmissionViewer(
            submissionDetails.title,
            uris,
            currentImage = 0,
            onImageChange = {}
        )


        SubmissionFormFields(
            submissionDetails = submissionDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Artist", style = MaterialTheme.typography.headlineSmall)
            if (areThereArtists) {
                ArtistSelect(
                    title = "Select an Artist",
                    selectedItem = submissionDetails.artist,
                    query = queryText,
                    onQueryChange = { artistsViewModel.onSearchTextChanged(it) },
                    items = artists,
                    onItemSelected = { selectedItem ->
                        onItemValueChange(
                            submissionDetails.copy(
                                artistId = selectedItem.artistId,
                                artist = selectedItem
                            )
                        )
                    },
                )
            } else {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            painter = IconResource.fromDrawableResource(R.drawable.cancel)
                                .asPainterResource(),
                            contentDescription = ""
                        )
                        Text(
                            text = "No artists",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Artist", style = MaterialTheme.typography.headlineSmall)

                if (areThereCharacters) {
                    CharactersSelect(
                        title = "Select a Character",
                        selectedItems = submissionDetails.characters,
                        query = queryText,
                        onQueryChange = { charactersViewModel.onSearchTextChanged(it) },
                        items = characters.toListOfCharacters(),
                        onItemsSelected = { selectedItems ->
                            onItemValueChange(
                                submissionDetails.copy(
                                    characters = selectedItems // Update the list of selected characters
                                )
                            )
                        },
                    )
                }
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
        verticalArrangement = Arrangement.spacedBy(10.dp)
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
        )
        Rating(
            rating = submissionDetails.rating,
            onRatingChange = { onValueChange(submissionDetails.copy(rating = it)) },
        )
    }
}