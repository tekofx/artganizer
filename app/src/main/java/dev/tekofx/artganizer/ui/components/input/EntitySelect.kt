package dev.tekofx.artganizer.ui.components.input

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.SmallCard

@Composable
fun <T> EntitySelect(
    selectedItem: T?,
    items: List<T>,
    labelMapper: (T) -> String,
    imageMapper: (T) -> String?,
    onItemSelected: (T) -> Unit,
    onQueryChange: (String) -> Unit,
    query: String,
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                expanded = !expanded
            },
        ) {
            if (selectedItem != null) {
                SmallCard(
                    title = labelMapper(selectedItem),
                    imagePath = imageMapper(selectedItem),
                    onClick = { expanded = !expanded }
                )
            } else {
                SmallCard(
                    title = "Select an item",
                    imagePath = null,
                    onClick = { expanded = !expanded }
                )
            }
        }
        if (expanded) {
            LazyColumn(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                items(items.size) { index ->
                    val item = items[index]
                    if (labelMapper(item).contains(query, ignoreCase = true)) {
                        Text(
                            text = labelMapper(item),
                            modifier = Modifier
                                .clickable {
                                    onQueryChange("") // Clear the search query when an item is selected
                                    expanded = false // Close the dropdown
                                    onItemSelected(item) // Notify the selection
                                }
                        )
                    }
                }
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = { onQueryChange(it) },
                label = { Text("Search") }
            )
        }
    }
}