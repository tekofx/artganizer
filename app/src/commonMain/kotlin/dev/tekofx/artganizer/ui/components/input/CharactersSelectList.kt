package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.ui.components.SmallCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersSelectList(
    selectedItems: List<Character>,
    characters: List<Character>,
    onItemsSelected: (List<Character>) -> Unit,
    textFieldState: TextFieldState,
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 70.dp),
        ) {
            if (characters.isEmpty()) {
                Text(
                    text = "No items with current search",
                    style = MaterialTheme.typography.bodyMedium,
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(characters) { character ->
                        SmallCard(
                            title = character.name,
                            imagePath = character.imagePath,
                            selected = selectedItems.contains(character),
                            onClick = {
                                val newSelection =
                                    if (selectedItems.contains(character)) {
                                        selectedItems - character
                                    } else {
                                        selectedItems + character
                                    }
                                onItemsSelected(newSelection)
                            },
                        )
                    }
                }
            }
        }

        ThinSearchBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClear = { textFieldState.clearText() },
            textFieldState = textFieldState,
        )
    }
}