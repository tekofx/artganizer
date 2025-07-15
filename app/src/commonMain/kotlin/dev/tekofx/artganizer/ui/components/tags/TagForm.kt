package dev.tekofx.artganizer.ui.components.tags

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.form.FormButtons
import dev.tekofx.artganizer.ui.components.input.form.FormTextfield
import dev.tekofx.artganizer.ui.utils.AVATAR_SIZE
import dev.tekofx.artganizer.ui.viewmodels.tags.TagDetails
import dev.tekofx.artganizer.ui.viewmodels.tags.TagUiState

@Composable
fun TagForm(
    tagUiState: TagUiState,
    onItemValueChange: (TagDetails) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Icon(
                IconResource.fromDrawableResource(R.drawable.tag_filled).asPainterResource(),
                contentDescription = "",
                modifier = Modifier.size(AVATAR_SIZE)
            )
        }
        item {
            TagFormFields(
                tagDetails = tagUiState.tagDetails,
                onValueChange = onItemValueChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            FormButtons(
                onSaveClick = onSaveClick,
                onCancelClick = onCancelClick,
                enabledSave = tagUiState.isEntryValid
            )
        }
    }
}

@Composable
fun TagFormFields(
    tagDetails: TagDetails,
    modifier: Modifier = Modifier,
    onValueChange: (TagDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        FormTextfield(
            value = tagDetails.name,
            onValueChange = { onValueChange(tagDetails.copy(name = it)) },
            label = "Name",
            enabled = enabled
        )
    }
}