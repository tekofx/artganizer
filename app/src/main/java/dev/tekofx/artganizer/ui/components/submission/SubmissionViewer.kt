package dev.tekofx.artganizer.ui.components.submission

import android.net.Uri
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@Composable
fun FullscreenImageViewer(
    imageUri: Uri,
    onClose: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 5f) // Limit zoom level
                    offset = Offset(
                        x = (offset.x + pan.x).coerceIn(-500f, 500f), // Adjust bounds as needed
                        y = (offset.y + pan.y).coerceIn(-500f, 500f)
                    )
                }
            }
    ) {
        AsyncImage(
            model = imageUri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y
                ),
            contentScale = ContentScale.Fit
        )
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
fun SubmissionViewer(
    title: String,
    imagePaths: List<Uri>,
    thumbnail: Uri,
    onImageChange: (Int) -> Unit,
    onClick: () -> Unit = {}
) {

    if (imagePaths.size > 1) {
        SubmissionPager(
            imagePaths,
            thumbnail = thumbnail,
            title,
            onImageChange = { onImageChange(it) },
            onClick = onClick
        )
    } else if (imagePaths.size == 1) {
        SubmissionImage(
            imagePaths[0],
            thumbnail,
            title,
            onClick = onClick
        )
    }
}


@Composable
fun SubmissionImage(
    imagePath: Uri,
    thumbnail: Uri,
    title: String,
    onClick: () -> Unit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imagePath)
            .placeholderMemoryCacheKey(thumbnail.toString())
            .build(),
        contentDescription = title,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
fun SubmissionPager(
    imagePaths: List<Uri>,
    thumbnail: Uri,
    title: String,
    onImageChange: (Int) -> Unit,
    onClick: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {

        val pagerState = rememberPagerState(
            pageCount = { imagePaths.size }
        )

        // Observe the current page and update currentImage
        LaunchedEffect(pagerState.currentPage) {
            onImageChange(pagerState.currentPage)
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            SubmissionImage(
                title = title,
                imagePath = imagePaths[page],
                thumbnail = thumbnail,
                onClick = onClick
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp),
            tonalElevation = 30.dp,
            shape = MaterialTheme.shapes.large
        ) {

            // Page indicator
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "${pagerState.currentPage + 1} / ${pagerState.pageCount}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}