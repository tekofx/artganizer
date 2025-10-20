package dev.tekofx.artganizer.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.add
import artganizer.composeapp.generated.resources.edit
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun CreateFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    drawableResource: DrawableResource= Res.drawable.add,
    text: String = "Add"
) {
    ExtendedFloatingActionButton(modifier = modifier, onClick = { onClick() }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(drawableResource),
                contentDescription = ""
            )
            Text(text)
        }
    }
}