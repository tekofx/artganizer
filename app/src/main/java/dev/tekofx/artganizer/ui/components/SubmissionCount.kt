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
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource

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
            painter = IconResource.fromDrawableResource(R.drawable.gallery_outlined)
                .asPainterResource(),
            contentDescription = "",
        )
        Text(
            count.toString(),
            style = MaterialTheme.typography.headlineMedium
        )
    }
}