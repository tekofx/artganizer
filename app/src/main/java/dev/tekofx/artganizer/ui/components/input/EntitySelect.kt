package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.window.Dialog
import dev.tekofx.artganizer.ui.components.SmallCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> EntitySelect(
    selectedItem: T?,
    title: String,
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
                    title = title,
                    imagePath = null,
                    onClick = { expanded = !expanded }
                )
            }
        }
        if (expanded) {
            Dialog(
                onDismissRequest = { expanded = false },
                content = {
                    Surface {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            LazyColumn(
                                modifier = Modifier
                                    .heightIn(min = 100.dp, max = 300.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(items.size) { index ->
                                    val item = items[index]
                                    if (labelMapper(item).contains(query, ignoreCase = true)) {
                                        SmallCard(
                                            title = labelMapper(item),
                                            imagePath = imageMapper(item),
                                            onClick = {
                                                onQueryChange("") // Clear the search query when an item is selected
                                                expanded = false // Close the dropdown
                                                onItemSelected(item) // Notify the selection
                                            },
                                            elevation = CardDefaults.cardElevation(
                                                defaultElevation = 4.dp,
                                                pressedElevation = 8.dp
                                            ),
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
            )
        }
    }
}