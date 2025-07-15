package dev.tekofx.artganizer.ui.screens.characters

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.edit
import artganizer.app.generated.resources.trash
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.getActivityViewModel
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.components.characters.CharacterForm
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.submission.GalleryRow
import dev.tekofx.artganizer.ui.utils.AVATAR_SIZE
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.toCharacterWithSubmissions
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterDetailsScreen(
    characterId: Long,
    navHostController: NavHostController,
    charactersViewModel: CharactersViewModel = getActivityViewModel<CharactersViewModel>(),
) {
    val showPopup by charactersViewModel.showPopup.collectAsState()
    val showCharacterEdit by charactersViewModel.showCharacterEdit.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        charactersViewModel.getCharacterWithSubmission(characterId)
    }

    if (showPopup) {
        ConfirmationPopup(
            title = "Confirm Action",
            message = "Are you sure you want to proceed?",
            onConfirm = {
                charactersViewModel.setShowPopup(true)
                charactersViewModel.deleteCharacter(charactersViewModel.currentCharacterUiState)
                navHostController.popBackStack()
                charactersViewModel.setShowPopup(false)
            },
            onDismiss = {
                charactersViewModel.setShowPopup(false)
            }
        )
    }
    Scaffold {
        if (showCharacterEdit) {
            CharacterForm(
                charactersViewModel.currentCharacterUiState,
                onItemValueChange = { newValue -> charactersViewModel.updateCurrentUiState(newValue) },
                onSaveClick = {
                    scope.launch { charactersViewModel.editCharacter(context) }
                    charactersViewModel.setShowEditArtist(false)
                },
                onCancelClick = {
                    charactersViewModel.setShowEditArtist(false)
                }
            )
        } else {
            CharacterInfo(
                characterWithSubmissions = charactersViewModel.currentCharacterUiState.toCharacterWithSubmissions(),
                onEditClick = {
                    charactersViewModel.setShowEditArtist(true)
                },
                onDeleteClick = {
                    charactersViewModel.setShowPopup(true)
                },
                onImageClick = { imageId ->
                    navHostController.navigate(
                        "${NavigateDestinations.SUBMISSIONS_LIST}/$imageId"
                    )
                },
            )
        }
    }
}

@Composable
fun CharacterInfo(
    characterWithSubmissions: CharacterWithSubmissions,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onImageClick: (Long) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            Text(
                text = characterWithSubmissions.character.name,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            Avatar(
                characterWithSubmissions.character.imagePath,
                size = AVATAR_SIZE
            )
        }


        characterWithSubmissions.character.description?.let {
            item {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
            }
        }
        item {

            HorizontalDivider(
                modifier = Modifier
                    .width(100.dp)
                    .clip(MaterialTheme.shapes.small),
                thickness = 5.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }
        item {

            Box(
                modifier = Modifier
                    .height(10.dp)
                    .height(200.dp)
                    .background(Color(0xFFFFFFFF))
            )
        }

        item {
            CharacterInfoField("Species", characterWithSubmissions.character.species)
        }
        item {
            CharacterInfoField("Gender", characterWithSubmissions.character.gender)
        }
        item {
            CharacterInfoField("Pronouns", characterWithSubmissions.character.pronouns)
        }
        item {
            CharacterInfoField("Height", characterWithSubmissions.character.height)
        }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ButtonWithIcon(
                    onClick = onEditClick,
                    text = "Edit",
                    icon = Res.drawable.edit,
                )
                ButtonWithIcon(
                    onClick = onDeleteClick,
                    text = "Delete",
                    icon = Res.drawable.trash,
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
        // Group submissions into chunks of 3
        characterWithSubmissions.submissions.chunked(3).forEach { chunk ->
            item {
                GalleryRow(
                    submissions = chunk,
                    onImageClick = onImageClick
                )
            }
        }
    }
}

@Composable
fun CharacterInfoField(
    fieldName: String,
    fieldValue: String?
) {
    fieldValue?.let {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fieldName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = fieldValue,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

    }
}

@Composable
@Preview
fun CharacterInfoPreview() {
    CharacterInfo(
        characterWithSubmissions = CharacterWithSubmissions(
            character = Character(
                characterId = 1,
                name = "Teko",
                species = "Arctic Fox",
                gender = "Male",
                pronouns = "He/Him",
                height = "1.75m",
                description = "Artic foxxo with blue hair",
                imagePath = null // Replace with a valid URI if needed
            ),
            submissions = emptyList<Submission>()
        ),
        onImageClick = {},
        onEditClick = {},
        onDeleteClick = {}
    )
}