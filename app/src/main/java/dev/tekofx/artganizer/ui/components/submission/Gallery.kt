package dev.tekofx.artganizer.ui.components.submission

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(submission.imagesPath[0])
                        .build(),
                    contentDescription = submission.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .padding(2.dp)
                        .clip(MaterialTheme.shapes.small)
                        .clickable {
                            navHostController.navigate("${NavigateDestinations.SUBMISSIONS_SCREEN}/${submission.id}")
                        }

                )
            }
        }
    )
}