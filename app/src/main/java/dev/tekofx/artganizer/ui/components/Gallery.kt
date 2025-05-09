package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.entities.Submission

@Composable
fun Gallery(submissions: List<Submission>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        content = {
            items(submissions) { submission ->
                Card() {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(submission.imagePath)
                            .build(),
                        contentDescription = "icon",
                    )
                }
            }
        }
    )
}