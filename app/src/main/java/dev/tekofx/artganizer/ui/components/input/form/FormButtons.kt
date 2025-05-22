package dev.tekofx.artganizer.ui.components.input.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon

@Composable
fun FormButtons(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit = {},
    enabledSave: Boolean = true,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ButtonWithIcon(
            onClick = onSaveClick,
            text = "Save",
            iconResource = IconResource.fromDrawableResource(R.drawable.device_floppy),
            enabled = enabledSave
        )
        ButtonWithIcon(
            onClick = onCancelClick,
            text = "Cancel",
            iconResource = IconResource.fromDrawableResource(R.drawable.cancel),
            color = MaterialTheme.colorScheme.error
        )
    }
}