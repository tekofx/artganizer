package dev.tekofx.artganizer.ui.components

import android.net.Uri
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import org.jetbrains.compose.resources.painterResource

@Composable
fun SmallCard(
    title: String,
    imagePath: Uri?,
    selected: Boolean = false,
    onClick: () -> Unit? = {},
    deletable: Boolean = false,
    onClear: () -> Unit = {},
) {
    val modifier = if (selected) {
        Modifier
            .border(3.dp, MaterialTheme.colorScheme.primary, shape = MaterialTheme.shapes.large)
            .fillMaxWidth()
    } else {
        Modifier.fillMaxWidth()
    }
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        onClick = { onClick() },
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(
                    imagePath = imagePath,
                    size = 50.dp,
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            if (deletable) {
                IconButton(
                    onClick = onClear
                ) {
                    Icon(
                        painterResource(Res.drawable.x),
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun SmallCardPreview() {
    SmallCard(
        title = "Test",
        imagePath = null,
        onClick = {},
        onClear = { print(1) }
    )
}