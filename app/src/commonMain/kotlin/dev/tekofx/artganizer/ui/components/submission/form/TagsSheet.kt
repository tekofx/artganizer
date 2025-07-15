package dev.tekofx.artganizer.ui.components.submission.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.components.input.TagsSelectList

@Composable
fun TagsSheet(
    showTagsSheet: Boolean,

    // Tags
    tags: List<Tag>,
    selectedTags: List<Tag> = emptyList(),
    onSelectedTagsChange: (List<Tag>) -> Unit,
    textFieldState: TextFieldState,

    closeBottomSheet: () -> Unit,
) {
    if (showTagsSheet) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Select Tags", style = MaterialTheme.typography.headlineSmall)
            TagsSelectList(
                selectedTags = selectedTags,
                tags = tags,
                onItemsSelected = { selectedItems ->
                    onSelectedTagsChange(selectedItems)
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