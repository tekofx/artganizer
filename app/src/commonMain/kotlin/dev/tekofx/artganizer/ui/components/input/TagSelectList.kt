package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.tag_filled
import dev.tekofx.artganizer.entities.Tag
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsSelectList(
    selectedTags: List<Tag>,
    tags: List<Tag>,
    onItemsSelected: (List<Tag>) -> Unit,
    textFieldState: TextFieldState
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
            if (tags.isEmpty()) {
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
                    items(tags) { tag ->
                        ListItem(
                            tag.name,
                            selected = selectedTags.contains(tag),
                            onClick = {
                                val newSelection =
                                    if (selectedTags.contains(tag)) {
                                        selectedTags - tag
                                    } else {
                                        selectedTags + tag
                                    }
                                onItemsSelected(newSelection)
                            }
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

@Composable
fun ListItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .border(
                width = if (selected) 2.dp else 0.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            ),

        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(Res.drawable.tag_filled),
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
                painterResource(Res.drawable.tag_filled),
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