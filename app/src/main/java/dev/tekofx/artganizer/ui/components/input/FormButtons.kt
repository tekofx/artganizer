package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource

@Composable
fun FormButtons(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ButtonWithIcon(
            onClick = onSaveClick,
            text = "Save",
            iconResource = IconResource.fromDrawableResource(R.drawable.device_floppy),
        )
        ButtonWithIcon(
            onClick = onCancelClick,
            text = "Cancel",
            iconResource = IconResource.fromDrawableResource(R.drawable.cancel),
            color = MaterialTheme.colorScheme.error
        )
    }
}