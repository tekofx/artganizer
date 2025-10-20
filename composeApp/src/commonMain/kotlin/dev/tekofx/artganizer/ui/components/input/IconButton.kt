package dev.tekofx.artganizer.ui.components.input

import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.world
import org.jetbrains.compose.resources.painterResource

@Composable
fun IconButton() {
    Button(
        onClick = {}

    ) {
        Icon(
            painterResource(Res.drawable.world),
            contentDescription = "IconButton",
        )
    }
}