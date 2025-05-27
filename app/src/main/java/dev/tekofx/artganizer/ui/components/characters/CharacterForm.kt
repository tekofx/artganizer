package dev.tekofx.artganizer.ui.components.characters

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.input.form.FormAvatar
import dev.tekofx.artganizer.ui.components.input.form.FormButtons
import dev.tekofx.artganizer.ui.components.input.form.FormTextfield
import dev.tekofx.artganizer.ui.viewmodels.characters.CharacterDetails
import dev.tekofx.artganizer.ui.viewmodels.characters.CharacterUiState
import kotlinx.coroutines.launch

@Composable
fun CharacterForm(
    characterUiState: CharacterUiState,
    onItemValueChange: (CharacterDetails) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), onResult = { uri: Uri? ->
            uri?.let {
                // Save the image and navigate to the next screen
                scope.launch {
                    onItemValueChange(
                        characterUiState.characterDetails.copy(
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
    ) {
        item {
            FormAvatar(
                fallbackText = characterUiState.characterDetails.name,
                characterUiState.characterDetails.imagePath,
                onImageSelect = { launcher.launch("image/*") }
            )
        }

        item {
            CharacterFormFields(
                characterDetails = characterUiState.characterDetails,
                onValueChange = onItemValueChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            FormButtons(
                onSaveClick = onSaveClick,
                onCancelClick = onCancelClick,
                enabledSave = characterUiState.isEntryValid
            )
        }
    }
}

@Composable
fun CharacterFormFields(
    characterDetails: CharacterDetails,
    modifier: Modifier = Modifier,
    onValueChange: (CharacterDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FormTextfield(
            value = characterDetails.name,
            onValueChange = { onValueChange(characterDetails.copy(name = it)) },
            label = "Name",
            enabled = enabled
        )
        FormTextfield(
            value = characterDetails.description ?: "",
            onValueChange = { onValueChange(characterDetails.copy(description = it)) },
            label = "Description",
            enabled = enabled
        )
        FormTextfield(
            value = characterDetails.species ?: "",
            onValueChange = { onValueChange(characterDetails.copy(species = it)) },
            label = "Species",
            enabled = enabled
        )
        FormTextfield(
            value = characterDetails.gender ?: "",
            onValueChange = { onValueChange(characterDetails.copy(gender = it)) },
            label = "Gender",
            enabled = enabled
        )
        FormTextfield(
            value = characterDetails.pronouns ?: "",
            onValueChange = { onValueChange(characterDetails.copy(pronouns = it)) },
            label = "Pronouns",
            enabled = enabled
        )
        FormTextfield(
            value = characterDetails.height ?: "",
            onValueChange = { onValueChange(characterDetails.copy(height = it)) },
            label = "Height",
            enabled = enabled
        )
    }
}