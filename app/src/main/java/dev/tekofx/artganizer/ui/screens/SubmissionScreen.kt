package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.entities.Submission

@Composable
fun SubmissionScreen(
    submission: Submission
) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(submission.imagePath)
                    .build(),
                contentDescription = "icon",
                contentScale = ContentScale.Inside,
            )
            Text(submission.title)
            Text(submission.description)

        }
    }
}