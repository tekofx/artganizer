package dev.tekofx.artganizer.ui.components.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.arrow_left
import artganizer.composeapp.generated.resources.arrow_right
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource


enum class TogglePosition {
    LEFT,
    RIGHT
}

@Composable
fun PanelContainer(
    onToggleShowClick: () -> Unit,
    showPanel: Boolean,
    togglePosition: TogglePosition,
    panel: @Composable () -> Unit,
) {
    val icon = when (togglePosition) {
        TogglePosition.LEFT -> if (showPanel) Res.drawable.arrow_right else Res.drawable.arrow_left
        TogglePosition.RIGHT -> if (showPanel) Res.drawable.arrow_left else Res.drawable.arrow_right
    }
    Surface(
        modifier = Modifier.wrapContentWidth().fillMaxHeight(),
        color = MaterialTheme.colorScheme.surfaceContainerLow
    ) {
        Row(
            modifier = Modifier.fillMaxHeight(),
        ) {

            if (togglePosition == TogglePosition.LEFT) {
                TogglePanelButton(
                    onClick = onToggleShowClick,
                    icon = icon
                )
            }

            AnimatedVisibility(visible = showPanel) {
                panel()
            }

            if (togglePosition == TogglePosition.RIGHT) {
                TogglePanelButton(
                    onClick = onToggleShowClick,
                    icon = icon
                )
            }
        }
    }
}

@Composable
fun TogglePanelButton(
    onClick: () -> Unit,
    icon: DrawableResource
) {
    Surface(
        modifier = Modifier.fillMaxHeight()
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ),
        color = MaterialTheme.colorScheme.surfaceContainerHighest
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = ""
        )
    }
}