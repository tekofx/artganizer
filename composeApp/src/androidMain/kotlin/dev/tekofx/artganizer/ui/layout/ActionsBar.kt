package dev.tekofx.artganizer.ui.layout

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.tekofx.artganizer.ui.screens.artists.animatePlacement
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ActionsBar(
    actions: List<@Composable () -> Unit>
) {
    Row(
        modifier = Modifier.animatePlacement()
    ) {
        actions.forEach { action ->
            action()
        }
    }
}

@Composable
fun Action(
    icon: DrawableResource,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    onContainerColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    onClick: () -> Unit
) {
    IconButton(
        colors = IconButtonDefaults.iconButtonColors().copy(
            containerColor = containerColor
        ),
        onClick = onClick
    ) {
        Icon(
            contentDescription = "",
            painter = painterResource(icon),
            tint = onContainerColor
        )
    }
}