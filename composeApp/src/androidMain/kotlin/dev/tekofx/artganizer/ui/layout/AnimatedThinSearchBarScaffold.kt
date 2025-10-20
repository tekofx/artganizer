package dev.tekofx.artganizer.ui.layout

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.ui.components.ThinSearchBar

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

    val animatedPadding by animateDpAsState(
        animationSpec = tween(200),
        targetValue = if (alignment == Alignment.TopCenter) 60.dp else 0.dp
    )

    var showComponent by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                onSearchClick = {
                    showComponent = true
                }
            )
        }
    ) {


        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = animatedPadding)
            ) {
                Column {
                    if (showComponent) {

                        ThinSearchBar(
                            onClear = { textFieldState.clearText() },
                            textFieldState = textFieldState,
                            onFocusChanged = onFocusChanged,
                        )
                    }
                    content()
                }
            }
            /*AnimatedVisibility(
                modifier = Modifier
                    .align(alignment)
                    .animatePlacement()
                    .padding(bottom = 100.dp),
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
            }*/
        }
    }
}