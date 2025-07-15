package dev.tekofx.artganizer.ui.components.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import artganizer.app.generated.resources.tag_filled
import artganizer.app.generated.resources.x
import dev.tekofx.artganizer.entities.Tag
import org.jetbrains.compose.resources.painterResource

@Composable
fun SmallTagCard(
    tag: Tag,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    deletable: Boolean = false,
    onClear: () -> Unit = {},
) {
    Card(
        modifier = modifier.clickable(
            onClick = onClick,
            indication = null,
            interactionSource = null,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    painterResource(Res.drawable.tag_filled),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = tag.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (deletable) {
                IconButton(
                    onClick = onClear,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(Res.drawable.x),
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SmallTagCardPreview() {
    SmallTagCard(
        tag = Tag(name = "Sample Tag"),
        onClick = {},
        deletable = true,
        onClear = {}
    )
}