package dev.tekofx.artganizer.ui.components.layout

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = fabVisible,
                enter = slideInHorizontally(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessMediumLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                ) { fullWidth -> fullWidth },
                exit = slideOutHorizontally(
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
                modifier = Modifier.then(
                    if (alignment == Alignment.TopCenter) Modifier.padding(top = 60.dp)
                    else Modifier
                )
            ) {
                content()
            }
            AnimatedVisibility(
                modifier = Modifier
                    .align(alignment)
                    .animatePlacement()
                    .padding(10.dp),
                visible = searchBarVisible,
                enter = slideInHorizontally(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessMediumLow,
                        dampingRatio = Spring.DampingRatioMediumBouncy
                    )
                ) { fullWidth -> fullWidth },
                exit = slideOutHorizontally(
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