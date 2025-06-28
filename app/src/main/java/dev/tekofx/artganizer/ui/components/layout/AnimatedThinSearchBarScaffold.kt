package dev.tekofx.artganizer.ui.components.layout

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.input.ThinSearchBar
import dev.tekofx.artganizer.ui.screens.artists.animatePlacement

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnimatedThinSearchBarScaffold(
    alignment: Alignment,
    searchBarVisible: Boolean,
    textFieldState: TextFieldState,
    onFocusChanged: (Boolean) -> Unit,
    fabVisible: Boolean,
    onFabClick: () -> Unit,
    content: @Composable () -> Unit,
) {
    BackHandler(enabled = searchBarVisible) {
        onFocusChanged(false) // Remove focus when back is pressed
    }

    val animatedPadding by animateDpAsState(
        animationSpec = tween(200),
        targetValue = if (alignment == Alignment.TopCenter) 60.dp else 0.dp
    )

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = fabVisible,
                enter = slideInVertically(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessMediumLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                ) { fullWidth -> fullWidth },
                exit = slideOutVertically(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh, dampingRatio = Spring.DampingRatioNoBouncy
                    )
                ) { fullWidth -> fullWidth }) {
                CreateFab(
                    modifier = Modifier.padding(bottom = 50.dp),
                    onClick = onFabClick,
                )
            }
        },
    ) {


        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = animatedPadding)
            ) {
                content()
            }
            AnimatedVisibility(
                modifier = Modifier
                    .align(alignment)
                    .animatePlacement()
                    .padding(10.dp),
                visible = searchBarVisible,
                enter = slideInVertically(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessMediumLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                ) { fullWidth -> fullWidth },
                exit = slideOutVertically(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh, dampingRatio = Spring.DampingRatioNoBouncy
                    )
                ) { fullWidth -> fullWidth }) {
                ThinSearchBar(
                    onClear = { textFieldState.clearText() },
                    textFieldState = textFieldState,
                    onFocusChanged = onFocusChanged,
                )
            }
        }
    }
}