package dev.tekofx.artganizer.ui.screens.artists


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector2D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.artists.ArtistCard
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.input.ThinSearchBar
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun ArtistScreen(
    navHostController: NavHostController, artistsViewModel: ArtistsViewModel
) {
    val artists by artistsViewModel.artists.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val isSearchBarFocused by artistsViewModel.isSearchBarFocused.collectAsState()
    val alignment by artistsViewModel.alignment.collectAsState()

    val lazyListState = rememberLazyListState()
    val firstShowedItem by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }


    BottomSheetScaffold(
        scaffoldState = scaffoldState, sheetContent = {
            Column {
                Text("Sheet")
            }
        }) {
        Scaffold(
            floatingActionButton = {
                AnimatedVisibility(
                    visible = (lazyListState.lastScrolledBackward || firstShowedItem == 0) && !isSearchBarFocused && artistsViewModel.state.text.isEmpty(),
                    enter = slideInHorizontally(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessMediumLow,
                            dampingRatio = Spring.DampingRatioMediumBouncy
                        )
                    ) { fullWidth -> fullWidth },
                    exit = slideOutHorizontally(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessHigh,
                            dampingRatio = Spring.DampingRatioNoBouncy
                        )
                    ) { fullWidth -> fullWidth }
                ) {
                    CreateFab(
                        modifier = Modifier.padding(bottom = 50.dp),
                        onClick = { navHostController.navigate(NavigateDestinations.ARTIST_CREATION_SCREEN) },
                    )
                }
            },
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(horizontal = 10.dp),
                    state = lazyListState
                ) {
                    item {
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    if (isSearchBarFocused) {
                        item {
                            Spacer(modifier = Modifier.height(50.dp))
                        }
                    }
                    items(artists) { artist ->
                        ArtistCard(
                            artist, onClick = {
                                navHostController.navigate("${NavigateDestinations.ARTISTS_SCREEN}/${artist.artist.artistId}")
                            }, modifier = Modifier.animateItem()
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(50.dp))
                    }


                }
                AnimatedVisibility(
                    modifier = Modifier
                        .align(alignment)
                        .animatePlacement()
                        .padding(10.dp),
                    visible = lazyListState.lastScrolledBackward || firstShowedItem == 0,
                    enter = slideInHorizontally(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessMediumLow,
                            dampingRatio = Spring.DampingRatioMediumBouncy
                        )
                    ) { fullWidth -> fullWidth },
                    exit = slideOutHorizontally(
                        animationSpec = spring(
                            stiffness = Spring.StiffnessHigh,
                            dampingRatio = Spring.DampingRatioNoBouncy
                        )
                    ) { fullWidth -> fullWidth }
                ) {
                    ThinSearchBar(
                        onClear = { artistsViewModel.clearTextField() },
                        state = artistsViewModel.state,
                        onFocusChanged = { artistsViewModel.setIsSearchBarFocused(it) },
                    )
                }
            }
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
