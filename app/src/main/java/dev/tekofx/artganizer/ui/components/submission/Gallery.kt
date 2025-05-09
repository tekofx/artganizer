package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.navigation.NavigateDestinations

@Composable
fun Gallery(navHostController: NavHostController, submissions: List<Submission>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        content = {
            items(submissions) { submission ->
                Card(
                    onClick = { navHostController.navigate("${NavigateDestinations.SUBMISSIONS_SCREEN}/${submission.id}") }
                ) {
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