package dev.tekofx.artganizer.ui.components.characters

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.input.FormAvatar
import dev.tekofx.artganizer.ui.viewmodels.characters.CharacterDetails
import dev.tekofx.artganizer.ui.viewmodels.characters.CharacterUiState
import kotlinx.coroutines.launch

@Composable
fun CharacterForm(
    characterUiState: CharacterUiState,
    onItemValueChange: (CharacterDetails) -> Unit,
    onSaveClick: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(), onResult = { uri: Uri? ->
            uri?.let {
                // Save the image and navigate to the next screen
                scope.launch {
                    onItemValueChange(
                        characterUiState.characterDetails.copy(
                            imagePath = uri.toString()
                        )
                    )
                }
            }
        }
    )

    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FormAvatar(
            fallbackText = characterUiState.characterDetails.name,
            characterUiState.characterDetails.imagePath,
            onImageSelect = { launcher.launch("image/*") })

        CharacterFormFields(
            characterDetails = characterUiState.characterDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = characterUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
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
        OutlinedTextField(
            value = characterDetails.name,
            onValueChange = { onValueChange(characterDetails.copy(name = it)) },
            label = { Text("Name") },

            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = characterDetails.species ?: "",
            onValueChange = { onValueChange(characterDetails.copy(species = it)) },
            label = { Text("Species") },

            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}