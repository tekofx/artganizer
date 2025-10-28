package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import dev.tekofx.artganizer.ui.components.artists.ArtistCard
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    onArtistClick: (Long) -> Unit
) {
    val artistsViewModel = koinViewModel<ArtistsViewModel>()

    // Data
    val artists by artistsViewModel.artists.collectAsState()

    // UI
    val listState by artistsViewModel.listState.collectAsState()


    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .padding(10.dp),
        state = listState
    ) {
        /*item {
            Spacer(modifier = Modifier.height(10.dp))
        }*/
        /*if (isSearchBarFocused) {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }*/
        items(artists) { artist ->
            ArtistCard(
                artist,
                onClick = {
                    onArtistClick(artist.artist.artistId)
                },
                modifier = Modifier.animateItem()
            )
        }

        item {
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

}

fun Modifier.animatePlacement(): Modifier = composed {
    val scope = rememberCoroutineScope()
    var targetOffset by remember { mutableStateOf(IntOffset.Zero) }
    var animatable by remember {
        mutableStateOf<Animatable<IntOffset, AnimationVector2D>?>(null)
    }
    this
        // 🔥 onPlaced should be before offset Modifier
        .onPlaced {
            // Calculate the position in the parent layout
            targetOffset = it
                .positionInParent()
                .round()
        }
        .offset {

            // Animate to the new target offset when alignment changes.
            val anim = animatable ?: Animatable(targetOffset, IntOffset.VectorConverter)
                .also {
                    animatable = it
                }

            // Skip animation if already at the target position
            if (anim.targetValue != targetOffset && anim.value != targetOffset) {
                scope.launch {
                    anim.animateTo(targetOffset, spring(stiffness = Spring.StiffnessMediumLow))
                }
            }
            // Offset the child in the opposite direction to the targetOffset, and slowly catch
            // up to zero offset via an animation to achieve an overall animated movement.
            animatable?.let { it.value - targetOffset } ?: IntOffset.Zero
        }
}
