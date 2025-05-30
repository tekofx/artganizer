package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
    title: String,
    items: List<Character>,
    onItemsSelected: (List<Character>) -> Unit,
    onQueryChange: (String) -> Unit,
    query: String,
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
            if (items.isEmpty()) {
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
                    items(items.size) { index ->
                        val item = items[index]
                        if (item.name.contains(query, ignoreCase = true)) {
                            SmallCard(
                                title = item.name,
                                imagePath = item.imagePath,
                                selected = selectedItems.contains(item),
                                onClick = {
                                    val newSelection =
                                        if (selectedItems.contains(item)) {
                                            selectedItems - item
                                        } else {
                                            selectedItems + item
                                        }
                                    onItemsSelected(newSelection)
                                },
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            SearchBar(
                queryText = query,
                onValueChange = { onQueryChange(it) },
            )
        }
    }
}