package dev.tekofx.artganizer.ui.components.input.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.cancel
import artganizer.composeapp.generated.resources.device_floppy
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import org.jetbrains.compose.resources.painterResource

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
            drawableResource =Res.drawable.device_floppy,
            enabled = enabledSave
        )
        ButtonWithIcon(
            onClick = onCancelClick,
            text = "Cancel",
            drawableResource = Res.drawable.cancel,
            color = MaterialTheme.colorScheme.error
        )
    }
}