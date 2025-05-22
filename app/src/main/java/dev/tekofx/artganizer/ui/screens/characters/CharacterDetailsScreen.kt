package dev.tekofx.artganizer.ui.screens.characters

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.components.ConfirmationPopup
import dev.tekofx.artganizer.ui.components.characters.CharacterForm
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.submission.Gallery
import dev.tekofx.artganizer.ui.utils.AVATAR_SIZE
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel
import dev.tekofx.artganizer.ui.viewmodels.characters.toCharacterWithSubmissions
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterDetailsScreen(
    charactersViewModel: CharactersViewModel,
    navHostController: NavHostController
) {
    val showPopup by charactersViewModel.showPopup.collectAsState()
    val showCharacterEdit by charactersViewModel.showCharacterEdit.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

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
                        "${NavigateDestinations.SUBMISSION_DETAILS_SCREEN}/$imageId"
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
    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Avatar(
            characterWithSubmissions.character.imagePath,
            size = AVATAR_SIZE
        )
        Text(
            text = characterWithSubmissions.character.name,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
        characterWithSubmissions.character.species?.let { species ->
            Text(
                text = species,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ButtonWithIcon(
                onClick = onEditClick,
                text = "Edit",
                iconResource = IconResource.fromDrawableResource(R.drawable.edit),
            )
            ButtonWithIcon(
                onClick = onDeleteClick,
                text = "Delete",
                iconResource = IconResource.fromDrawableResource(R.drawable.trash),
                color = MaterialTheme.colorScheme.error,
            )
        }
        Gallery(
            characterWithSubmissions.submissions,
            onImageClick = onImageClick
        )
    }
}

