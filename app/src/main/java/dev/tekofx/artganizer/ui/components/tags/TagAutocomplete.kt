package dev.tekofx.artganizer.ui.components.tags

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource


val items = listOf(
    "Avatar",
    "Sketch",
    "Sticker",
    "Fullbody",
    "Illustration",
    "Concept Art",
    "Digital Art",
    "Traditional Art",
)

@Composable
fun TagAutocomplete() {
    var value by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val selectedItems = remember { mutableStateOf(emptyList<String>()) }

    Column {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.outline)
                .padding(10.dp),
            itemVerticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            selectedItems.value.map { item ->
                SelectedItem(name = item)
            }
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    value = newValue
                },
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                modifier = Modifier
                    .weight(1f, fill = true)
                    .widthIn(max = value.length.dp * 10)
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    }
                    .onKeyEvent { keyEvent ->
                        when {
                            keyEvent.nativeKeyEvent.keyCode == 67 && value.isEmpty() && selectedItems.value.isNotEmpty() -> {
                                selectedItems.value = selectedItems.value.dropLast(1)
                                true
                            }

                            keyEvent.nativeKeyEvent.keyCode == 66 && value.isNotEmpty() -> { // 66 is the keyCode for Enter
                                val matchingItem =
                                    items.firstOrNull { it.contains(value, ignoreCase = true) }
                                val itemToAdd =
                                    matchingItem?.replaceFirstChar { it.uppercaseChar() }
                                        ?: value.replaceFirstChar { it.uppercaseChar() }
                                if (itemToAdd !in selectedItems.value) {
                                    selectedItems.value = selectedItems.value + itemToAdd
                                }
                                value = "" // Clear the input after adding
                                true
                            }

                            else -> false
                        }
                    },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.None), // Prevents keyboard from closing

            )
        }
        if (isFocused) {
            Card(
                modifier = Modifier.heightIn(max = 100.dp)
            ) {
                LazyColumn {
                    val filteredItems = items.filter { it.contains(value, ignoreCase = true) }
                    if (filteredItems.isEmpty()) {
                        item {
                            val capitalizedValue =
                                value.replaceFirstChar { it.uppercaseChar() }
                            ListItem(
                                "Create tag $capitalizedValue",
                                onClick = {
                                    if (value !in selectedItems.value) {
                                        selectedItems.value = selectedItems.value + capitalizedValue
                                    }
                                    value = "" // Clear the input after selection
                                }
                            )
                        }
                    } else {
                        items(items.size) { index ->
                            val item = items[index]
                            if (item.contains(value, ignoreCase = true)) {
                                ListItem(
                                    item,
                                    onClick = {
                                        val capitalizedItem =
                                            item.replaceFirstChar { it.uppercaseChar() }
                                        if (item !in selectedItems.value) {
                                            selectedItems.value =
                                                selectedItems.value + capitalizedItem
                                        }
                                        value = "" // Clear the input after selection
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListItem(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            IconResource.fromDrawableResource(R.drawable.tag_filled).asPainterResource(),
            contentDescription = ""
        )
        Text(
            text = text,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable(
                    onClick = onClick
                )
        )
    }
}

@Composable
fun SelectedItem(
    name: String,
) {
    Card {
        Row(
            modifier = Modifier.padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                IconResource.fromDrawableResource(R.drawable.tag_filled).asPainterResource(),
                contentDescription = ""
            )

            Text(
                text = name,
                modifier = Modifier
                    .clickable { /* Handle click */ }
                    .padding(10.dp)
            )
        }
    }
}