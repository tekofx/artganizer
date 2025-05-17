package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.ui.components.input.EntitySelect
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionUiState

val items =
    listOf(
        "Item 1",
        "Item 2",
        "Item 3",
        "Item 4",
        "Item 5",
        "Item 6",
        "Item 7",
        "Item 8",
        "Item 9",
        "Item 10"
    )

@Composable
fun SubmissionsForm(
    artistsViewModel: ArtistsViewModel,
    submissionUiState: SubmissionUiState,
    onItemValueChange: (SubmissionDetails) -> Unit,
    onSaveClick: () -> Unit,
) {
    val queryText by artistsViewModel.queryText.collectAsState()
    val artists by artistsViewModel.artists.collectAsState()

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(submissionUiState.submissionDetails.imagePath)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.Inside,
        )
        SubmissionFormFields(
            submissionDetails = submissionUiState.submissionDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Artist", style = MaterialTheme.typography.headlineSmall)
            EntitySelect(
                title = "Artist",
                selectedItem = submissionUiState.submissionDetails.artist,
                query = queryText,
                onQueryChange = { artistsViewModel.onSearchTextChanged(it) },
                items = artists,
                labelMapper = { it.name },
                imageMapper = { it.imagePath },
                onItemSelected = { selectedItem ->
                    onItemValueChange(
                        submissionUiState.submissionDetails.copy(
                            artistId = selectedItem.id,
                            artist = selectedItem
                        )
                    )
                },
            )
        }
        Button(
            onClick = onSaveClick,
            enabled = submissionUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
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
        RatingSelector(
            rating = submissionDetails.rating,
            onRatingChange = { onValueChange(submissionDetails.copy(rating = it)) },
        )
    }
}