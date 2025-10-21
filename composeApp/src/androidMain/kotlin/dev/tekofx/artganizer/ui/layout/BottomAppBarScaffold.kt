package dev.tekofx.artganizer.ui.layout

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.search
import dev.tekofx.artganizer.ui.components.ThinSearchBar
import org.jetbrains.compose.resources.DrawableResource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomAppBarScaffold(
    textFieldState: TextFieldState,
    onFocusChanged: (Boolean) -> Unit,
    onAddButtonClick: (() -> Unit),
    firstButtonIcon: DrawableResource = Res.drawable.search,
    content: @Composable () -> Unit,
) {


    var showComponent by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                firstButtonIcon = firstButtonIcon,
                onFirstButtonClick = { showComponent = true },
                onAddButtonClick = onAddButtonClick
            )
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
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
    }
}