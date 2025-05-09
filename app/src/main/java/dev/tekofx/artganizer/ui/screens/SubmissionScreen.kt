package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.entities.Submission

@Composable
fun SubmissionScreen(
    submission: Submission
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(submission.imagePath)
                    .build(),
                contentDescription = "icon",
                contentScale = ContentScale.Inside,
            )
            Text(
                text = submission.title,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(submission.description)

        }
    }
}

@Preview
@Composable
fun SubmissionScreenPreview() {
    SubmissionScreen(
        submission = Submission(
            id = 1,
            title = "Submission Title",
            description = "This is a description of the submission.",
            imagePath = "file:///data/user/0/dev.tekofx.artganizer/files/5e210f2b-7c2b-480c-a3b6-02b093ce3f76.jpg"
        )
    )
}