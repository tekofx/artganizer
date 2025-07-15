package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.gallery_outlined
import org.jetbrains.compose.resources.painterResource

@Composable
fun SubmissionCount(
    count: Int,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Icon(
            modifier = Modifier.size(30.dp),
            painter = painterResource(Res.drawable.gallery_outlined),
            contentDescription = "",
        )
        Text(
            count.toString(),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}