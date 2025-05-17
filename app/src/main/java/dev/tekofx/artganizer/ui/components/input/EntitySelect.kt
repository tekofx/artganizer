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


val items =
    listOf(
        "Item 1",
        "Item 2",
        "Item 3",
        "Item 4",
        "Item 5",
        "Item 6",
        "Item 7",
        "Item 8",
        "Item 9",
        "Item 10"
    )

@Composable
fun EntitySelect(
    items: List<String>,
) {
    var selectedItem: String? by remember { mutableStateOf(null) }
    var query by remember { mutableStateOf("") }
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
                Text(text = "Selected item: $selectedItem")
            } else {
                Text(text = "No item selected")
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
                    if (item.contains(query)) {
                        Text(
                            text = item,
                            modifier = Modifier
                                .clickable {
                                    selectedItem = item
                                    query = "" // Clear the search query when an item is selected
                                    expanded = false // Close the dropdown
                                }
                                .animateItem()
                        )
                    }
                }
            }

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = query,
                onValueChange = { query = it },
                label = { Text("Search") }
            )
        }
    }
}