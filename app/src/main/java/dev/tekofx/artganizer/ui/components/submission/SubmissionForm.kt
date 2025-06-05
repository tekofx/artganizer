package dev.tekofx.artganizer.ui.components.submission

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
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
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.SmallCard
import dev.tekofx.artganizer.ui.components.input.ArtistListSelect
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.CharactersSelectList
import dev.tekofx.artganizer.ui.components.input.RatingInput
import dev.tekofx.artganizer.ui.components.input.TagsSelectList
import dev.tekofx.artganizer.ui.components.input.form.FormButtons
import dev.tekofx.artganizer.ui.components.input.form.FormTextfield
import dev.tekofx.artganizer.ui.components.tags.SmallTagCard
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
                onSearch = { artistsViewModel.onSearchTextChanged(it) },
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
                queryText = queryText,
            )

            CharactersSheet(
                showCharactersSheet = showCharactersSheet,
                characters = characters.toListOfCharacters(),
                selectedCharacters = submissionDetails.characters,
                onSearch = { charactersViewModel.onSearchTextChanged(it) },
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
                queryText = queryText,
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
                onAddTag = {
                    scope.launch {
                        tagsViewModel.createTag(it)
                    }
                },
                closeBottomSheet = {
                    scope.launch {
                        scaffoldState.bottomSheetState.hide()
                    }
                },
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
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    onAddCharactersButton = {
                        showCharactersSheet = true
                        showArtistsSheet = false
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

        ArtistInput(
            areThereArtists = areThereArtists,
            submissionDetails = submissionDetails,
            onAddArtistButton = onAddArtistButton,
            onValueChange = onValueChange
        )
        CharactersInput(
            areThereCharacters = areThereCharacters,
            submissionDetails = submissionDetails,
            onAddCharactersButton = onAddCharactersButton,
            onValueChange = onValueChange
        )



        TagsInput(
            submissionDetails = submissionDetails,
            onValueChange = { onValueChange(submissionDetails.copy(tags = it.tags)) },
            onAddTagsClick = onAddTagsButton
        )

    }
}

@Composable
fun ArtistInput(
    areThereArtists: Boolean,
    submissionDetails: SubmissionDetails,
    onAddArtistButton: () -> Unit,
    onValueChange: (SubmissionDetails) -> Unit
) {
    if (areThereArtists) {
        Text("Artist", style = MaterialTheme.typography.headlineSmall)
        if (submissionDetails.artist == null) {
            ButtonWithIcon(
                onClick = onAddArtistButton,
                iconResource = IconResource.fromDrawableResource(R.drawable.palette_filled),
                text = "Add Artist",
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            SmallCard(
                title = submissionDetails.artist.name,
                imagePath = submissionDetails.artist.imagePath,
                onClick = onAddArtistButton,
                deletable = true,
                onClear = {
                    onValueChange(
                        submissionDetails.copy(
                            artistId = null,
                            artist = null
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun CharactersInput(
    areThereCharacters: Boolean,
    submissionDetails: SubmissionDetails,
    onAddCharactersButton: () -> Unit,
    onValueChange: (SubmissionDetails) -> Unit
) {
    if (areThereCharacters) {
        Text("Characters", style = MaterialTheme.typography.headlineSmall)
        if (submissionDetails.characters.isEmpty()) {
            ButtonWithIcon(
                onClick = onAddCharactersButton,
                iconResource = IconResource.fromDrawableResource(R.drawable.paw_filled),
                text = "Add Characters",
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                submissionDetails.characters.forEach { item ->
                    SmallCard(
                        title = item.name,
                        imagePath = item.imagePath,
                        onClick = onAddCharactersButton,
                        deletable = true,
                        onClear = {
                            onValueChange(
                                submissionDetails.copy(
                                    characters = submissionDetails.characters.filterNot { it.characterId == item.characterId }
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TagsInput(
    submissionDetails: SubmissionDetails,
    onAddTagsClick: () -> Unit,
    onValueChange: (SubmissionDetails) -> Unit
) {
    Text("Tags", style = MaterialTheme.typography.headlineSmall)
    if (submissionDetails.tags.isEmpty()) {
        ButtonWithIcon(
            onClick = onAddTagsClick,
            iconResource = IconResource.fromDrawableResource(R.drawable.tag_filled),
            text = "Add Tags",
            modifier = Modifier.fillMaxWidth()
        )
    } else {
        LazyRow {
            items(submissionDetails.tags) { tag ->
                SmallTagCard(
                    tag,
                    modifier = Modifier.padding(5.dp),
                    onClick = onAddTagsClick,
                    deletable = true,
                    onClear = {
                        onValueChange(
                            submissionDetails.copy(
                                tags = submissionDetails.tags.filterNot { it.tagId == tag.tagId }
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun TagsSheet(
    showCharactersSheet: Boolean,
    selectedTags: List<Tag>,
    tags: List<Tag>,
    onSearch: (String) -> Unit,
    onItemValueChange: (List<Tag>) -> Unit,
    closeBottomSheet: () -> Unit,
    queryText: String
) {
    if (showCharactersSheet) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Select Tags", style = MaterialTheme.typography.headlineSmall)
            TagsSelectList(
                selectedTags = selectedTags,
                title = "Select Tags",
                items = tags,
                onItemsSelected = { selectedItems ->
                    onItemValueChange(selectedTags)
                },
                onQueryChange = { query ->
                    onSearch(query)
                },
                query = queryText
            )
            ButtonWithIcon(
                onClick = {
                    closeBottomSheet()
                },
                iconResource = IconResource.fromDrawableResource(R.drawable.x),
                text = "Close",
            )
        }
    }
}

@Composable
fun TagsSheet(
    showTagsSheet: Boolean,

    // Tags
    tags: List<Tag>,
    selectedTags: List<Tag> = emptyList(),
    onSelectedTagsChange: (List<Tag>) -> Unit,
    onAddTag: (String) -> Unit,

    closeBottomSheet: () -> Unit,
) {
    if (showTagsSheet) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Select Tags", style = MaterialTheme.typography.headlineSmall)
            TagsSelectList(
                selectedTags = selectedTags,
                title = "Select Tags",
                items = tags,
                onItemsSelected = { selectedItems ->
                    onSelectedTagsChange(selectedItems)
                },
                onQueryChange = {},
                query = ""
            )
            ButtonWithIcon(
                onClick = {
                    closeBottomSheet()
                },
                iconResource = IconResource.fromDrawableResource(R.drawable.x),
                text = "Close",
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ArtistSheet(
    showArtistsSheet: Boolean,
    artists: List<ArtistWithSubmissions>,
    onSearch: (String) -> Unit,
    onItemValueChange: (Artist) -> Unit,
    closeBottomSheet: () -> Unit,
    queryText: String
) {
    if (showArtistsSheet) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Select Artist", style = MaterialTheme.typography.headlineSmall)
            ArtistListSelect(
                items = artists,
                onQueryChange = { onSearch(it) },
                onArtistClick = { artist ->
                    onItemValueChange(artist)
                    closeBottomSheet()
                },
                query = queryText,
            )
            ButtonWithIcon(
                onClick = {
                    closeBottomSheet()
                },
                iconResource = IconResource.fromDrawableResource(R.drawable.x),
                text = "Close",
            )

        }
    }
}

@Composable
fun CharactersSheet(
    showCharactersSheet: Boolean,
    characters: List<Character>,
    selectedCharacters: List<Character>,
    onSearch: (String) -> Unit,
    onItemValueChange: (List<Character>) -> Unit,
    closeBottomSheet: () -> Unit,
    queryText: String
) {
    if (showCharactersSheet) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Select Characters", style = MaterialTheme.typography.headlineSmall)
            CharactersSelectList(
                selectedItems = selectedCharacters,
                title = "Select Characters",
                items = characters,
                onItemsSelected = { selectedItems ->
                    onItemValueChange(selectedItems)
                },
                onQueryChange = { onSearch(it) },
                query = queryText,
            )
            ButtonWithIcon(
                onClick = {
                    closeBottomSheet()
                },
                iconResource = IconResource.fromDrawableResource(R.drawable.x),
                text = "Close",
            )
        }
    }
}

