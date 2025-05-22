package dev.tekofx.artganizer.ui.components.submission

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.ui.components.input.ArtistSelect
import dev.tekofx.artganizer.ui.components.input.CharactersSelect
import dev.tekofx.artganizer.ui.components.input.FormButtons
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
    currentImageIndex: Int,
    onItemValueChange: (SubmissionDetails) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit = {},
) {
    val queryText by artistsViewModel.queryText.collectAsState()
    val artists by artistsViewModel.artists.collectAsState()
    val characters by charactersViewModel.characters.collectAsState()
    val areThereArtists by artistsViewModel.areThereArtists
    val areThereCharacters by charactersViewModel.areThereCharacters

    LazyColumn(
        modifier = Modifier
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            SubmissionViewer(
                title = submissionDetails.title,
                imagePaths = uris,
                thumbnail = submissionDetails.thumbnail,
                currentImageIndex = currentImageIndex,
                onImageChange = {}
            )
        }

        item {
            SubmissionFormFields(
                submissionDetails = submissionDetails,
                onValueChange = onItemValueChange,
                modifier = Modifier.fillMaxWidth(),
                queryText = queryText,
                artists = artists,
                areThereArtists = areThereArtists,
                onArtistSearchTextChange = { artistsViewModel.onSearchTextChanged(it) },
                characters = characters,
                areThereCharacters = areThereCharacters,
                onCharacterSearchTextChange = { charactersViewModel.onSearchTextChanged(it) }
            )
        }

        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )
        }

        item {
            FormButtons(
                onSaveClick = onSaveClick,
                onCancelClick = onCancelClick,
            )
        }
    }
}

@Composable
fun SubmissionFormFields(
    submissionDetails: SubmissionDetails,
    modifier: Modifier = Modifier,
    queryText: String,
    onValueChange: (SubmissionDetails) -> Unit = {},
    artists: List<ArtistWithSubmissions>,
    areThereArtists: Boolean,
    onArtistSearchTextChange: (String) -> Unit,
    characters: List<CharacterWithSubmissions>,
    areThereCharacters: Boolean,
    onCharacterSearchTextChange: (String) -> Unit = {},
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
            singleLine = true
        )
        OutlinedTextField(
            value = submissionDetails.description,
            onValueChange = { onValueChange(submissionDetails.copy(description = it)) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
        )
        Rating(
            rating = submissionDetails.rating,
            onRatingChange = { onValueChange(submissionDetails.copy(rating = it)) },
        )
        if (areThereArtists) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Artist", style = MaterialTheme.typography.headlineSmall)
                ArtistSelect(
                    title = "Select an Artist",
                    selectedItem = submissionDetails.artist,
                    query = queryText,
                    onQueryChange = onArtistSearchTextChange,
                    items = artists,
                    onItemSelected = { selectedItem ->
                        onValueChange(
                            submissionDetails.copy(
                                artistId = selectedItem?.artistId,
                                artist = selectedItem
                            )
                        )
                    },
                )
            }
        }
        if (areThereCharacters) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text("Artist", style = MaterialTheme.typography.headlineSmall)

                CharactersSelect(
                    title = "Select a Character",
                    selectedItems = submissionDetails.characters,
                    query = queryText,
                    onQueryChange = { onCharacterSearchTextChange },
                    items = characters.toListOfCharacters(),
                    onItemsSelected = { selectedItems ->
                        onValueChange(
                            submissionDetails.copy(
                                characters = selectedItems // Update the list of selected characters
                            )
                        )
                    },
                )
            }
        }
    }
}