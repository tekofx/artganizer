package dev.tekofx.artganizer.ui.components.layout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.input.ThinSearchBar
import dev.tekofx.artganizer.ui.screens.artists.animatePlacement

@Composable
fun AnimatedThinSearchBarScaffold(
    alignment: Alignment,
    visible: Boolean,
    onClear: () -> Unit,
    textFieldState: TextFieldState,
    onFocusChanged: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        content()
        AnimatedVisibility(
            modifier = Modifier
                .align(alignment)
                .animatePlacement()
                .padding(10.dp),
            visible = visible,
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
                onClear = onClear,
                textFieldState = textFieldState,
                onFocusChanged = onFocusChanged,
            )
        }
    }
}