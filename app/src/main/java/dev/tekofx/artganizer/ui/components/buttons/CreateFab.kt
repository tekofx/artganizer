package dev.tekofx.artganizer.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.IconResource

@Composable
fun CreateFab(
    onClick: () -> Unit,
    iconResource: IconResource = IconResource.fromImageVector(Icons.Filled.Add),
    text: String = "Add"
) {
    ExtendedFloatingActionButton(onClick = { onClick() }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                iconResource.asPainterResource(),
                contentDescription = ""
            )
            Text(text)
        }
    }
}