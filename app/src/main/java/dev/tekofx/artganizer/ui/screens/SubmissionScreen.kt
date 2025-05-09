package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.ui.components.submission.Rating

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
            Row {
                Text("Rating")
                Rating(submission.rating)
            }
            ImageInfo(submission)


        }
    }
}


@Composable
fun ImageInfo(submission: Submission) {
    Column {
        Row {
            Text(submission.date.toString())
            Text(submission.sizeInMb.toString())
        }
        Row {
            Text(submission.dimensions)
            Text(submission.extension)
        }
    }

}