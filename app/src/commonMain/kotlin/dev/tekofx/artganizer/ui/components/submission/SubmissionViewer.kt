package dev.tekofx.artganizer.ui.components.submission


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.copy_plus
import coil3.compose.AsyncImage
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import org.jetbrains.compose.resources.painterResource


@Composable
fun ViewerButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .padding(16.dp),
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 1.dp)
    ) {
        IconButton(
            onClick = { onClick() },
            modifier = Modifier
        ) {
            Icon(
                painterResource(Res.drawable.copy_plus),
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Composable
fun FullscreenSubmissionViewer(
    imagePaths: List<String>,
    currentImageIndex: Int,
    thumbnail: String,
    onImageChange: (Int) -> Unit,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        if (imagePaths.size > 1) {
            FullscreenSubmissionPager(
                imagePaths = imagePaths,
                currentImageIndex = currentImageIndex,
                onImageChange = { onImageChange(it) },
            )
        } else if (imagePaths.size == 1) {
            AsyncImage(
                model = imagePaths[0],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize().zoomable(rememberZoomState()),
            )
        }
        ViewerButton(
            onClick = { },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun SubmissionViewer(
    imagePaths: List<String>,
    currentImageIndex: Int,
    thumbnail: String,
    onImageChange: (Int) -> Unit,
    onClick: () -> Unit = {}
) {

    if (imagePaths.size > 1) {
        SubmissionPager(
            imagePaths = imagePaths,
            thumbnail = thumbnail,
            currentImageIndex = currentImageIndex,
            onImageChange = { onImageChange(it) },
            onClick = onClick
        )
    } else if (imagePaths.size == 1) {
        SubmissionImage(
            imagePaths[0],
            thumbnail,
            onClick = onClick
        )
    }
}


@Composable
fun SubmissionImage(
    imagePath: String,
    thumbnail: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        var isLoading by remember { mutableStateOf(true) }

        AsyncImage(
            model = imagePath,
            contentDescription = "submission",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
            onLoading = { isLoading = true },
            onSuccess = { isLoading = false },
            onError = { isLoading = false }
        )

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun FullscreenSubmissionPager(
    imagePaths: List<String>,
    currentImageIndex: Int,
    onImageChange: (Int) -> Unit,

    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {

        val pagerState = rememberPagerState(
            pageCount = { imagePaths.size },
            initialPage = currentImageIndex
        )

        // Observe the current page and update currentImage
        LaunchedEffect(pagerState.currentPage) {
            onImageChange(pagerState.currentPage)
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            AsyncImage(
                model = imagePaths[page],
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize().zoomable(rememberZoomState()),
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
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

@Composable
fun SubmissionPager(
    imagePaths: List<String>,
    currentImageIndex: Int,
    thumbnail: String,
    onImageChange: (Int) -> Unit,
    onClick: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {

        val pagerState = rememberPagerState(
            pageCount = { imagePaths.size },
            initialPage = currentImageIndex
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