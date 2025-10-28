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
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.ui.components.ThinSearchBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomAppBarScaffold(
    textFieldState: TextFieldState,
    onFocusChanged: (Boolean) -> Unit,
    navController: NavHostController,
    actions: List<@Composable () -> Unit>,
    content: @Composable () -> Unit,
) {


    var showComponent by remember {
        mutableStateOf(false)
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                actions = actions
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