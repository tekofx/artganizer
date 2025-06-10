package dev.tekofx.artganizer.ui.components.submission.form

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.input.RatingInput
import dev.tekofx.artganizer.ui.components.input.form.FormButtons
import dev.tekofx.artganizer.ui.components.input.form.FormTextfield
import dev.tekofx.artganizer.ui.components.submission.SubmissionViewer
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.submissions.toListOfCharacters
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel
import dev.tekofx.artganizer.ui.viewmodels.tags.toListOfTags
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionsForm(
    uris: List<Uri>,
    artistsViewModel: ArtistsViewModel,
    submissionDetails: SubmissionDetails,
    charactersViewModel: CharactersViewModel,
    tagsViewModel: TagsViewModel,
    currentImageIndex: Int,
    onItemValueChange: (SubmissionDetails) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit = {},
) {
    val queryText by artistsViewModel.queryText.collectAsState()

    // Artists
    val artists by artistsViewModel.artists.collectAsState()
    val areThereArtists by artistsViewModel.areThereArtists
    var showArtistsSheet by remember { mutableStateOf(true) }

    // Characters
    val characters by charactersViewModel.characters.collectAsState()
    val areThereCharacters by charactersViewModel.areThereCharacters
    var showCharactersSheet by remember { mutableStateOf(false) }


    // Tags
    val tags by tagsViewModel.tags.collectAsState()
    var showTagsSheet by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetSwipeEnabled = false,
        sheetDragHandle = null,
        sheetContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        sheetContent = {
            ArtistSheet(
                showArtistsSheet = showArtistsSheet,
                artists = artists,
                onItemValueChange = { artist ->
                    onItemValueChange(
                        submissionDetails.copy(
                            artistId = artist.artistId,
                            artist = artist
                        )
                    )
                },
                closeBottomSheet = {
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                },
                textFieldState = artistsViewModel.textFieldState
            )

            CharactersSheet(
                showCharactersSheet = showCharactersSheet,
                characters = characters.toListOfCharacters(),
                selectedCharacters = submissionDetails.characters,
                onItemValueChange = { selectedItems ->
                    onItemValueChange(
                        submissionDetails.copy(
                            characters = selectedItems // Update the list of selected characters
                        )
                    )
                },
                closeBottomSheet = {
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                },
                textFieldState = charactersViewModel.textFieldState,
            )
            TagsSheet(
                showTagsSheet = showTagsSheet,
                tags = tags.toListOfTags(),
                selectedTags = submissionDetails.tags,
                onSelectedTagsChange = {
                    onItemValueChange(
                        submissionDetails.copy(
                            tags = it // Update the list of selected tags
                        )
                    )
                },
                closeBottomSheet = {
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                },
                textFieldState = tagsViewModel.textFieldState
            )
        }
    ) {
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
                    areThereArtists = areThereArtists,
                    areThereCharacters = areThereCharacters,
                    onAddArtistButton = {
                        showArtistsSheet = true
                        showCharactersSheet = false
                        showTagsSheet = false
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    onAddCharactersButton = {
                        showCharactersSheet = true
                        showArtistsSheet = false
                        showTagsSheet = false
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    onAddTagsButton = {
                        showTagsSheet = true
                        showArtistsSheet = false
                        showCharactersSheet = false
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
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
}

@Composable
fun SubmissionFormFields(
    submissionDetails: SubmissionDetails,
    modifier: Modifier = Modifier,
    onValueChange: (SubmissionDetails) -> Unit = {},

    // Artists
    areThereArtists: Boolean,
    onAddArtistButton: () -> Unit,

    // Characters
    areThereCharacters: Boolean,
    onAddCharactersButton: () -> Unit,

    // Tags
    onAddTagsButton: () -> Unit

) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FormTextfield(
            value = submissionDetails.title,
            onValueChange = { onValueChange(submissionDetails.copy(title = it)) },
            label = "Title",
        )
        FormTextfield(
            value = submissionDetails.description,
            onValueChange = { onValueChange(submissionDetails.copy(description = it)) },
            label = "Description",
            singleLine = false
        )
        RatingInput(
            rating = submissionDetails.rating,
            onRatingChange = { onValueChange(submissionDetails.copy(rating = it)) },
        )

        ArtistSection(
            areThereArtists = areThereArtists,
            submissionDetails = submissionDetails,
            onAddArtistButton = onAddArtistButton,
            onValueChange = onValueChange
        )
        CharactersSection(
            areThereCharacters = areThereCharacters,
            submissionDetails = submissionDetails,
            onAddCharactersButton = onAddCharactersButton,
            onValueChange = onValueChange
        )



        TagsSection(
            submissionDetails = submissionDetails,
            onValueChange = { onValueChange(submissionDetails.copy(tags = it.tags)) },
            onAddTagsClick = onAddTagsButton
        )

    }
}





