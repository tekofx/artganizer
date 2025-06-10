package dev.tekofx.artganizer.ui.components.submission.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.CharactersSelectList


@Composable
fun CharactersSheet(
    showCharactersSheet: Boolean,
    characters: List<Character>,
    selectedCharacters: List<Character>,
    onItemValueChange: (List<Character>) -> Unit,
    closeBottomSheet: () -> Unit,
    textFieldState: TextFieldState
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
                characters = characters,
                onItemsSelected = { selectedItems ->
                    onItemValueChange(selectedItems)
                },
                textFieldState = textFieldState
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
