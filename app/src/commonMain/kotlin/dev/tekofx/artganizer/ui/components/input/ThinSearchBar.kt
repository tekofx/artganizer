package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.x
import org.jetbrains.compose.resources.painterResource

@Composable
fun isKeyboardVisible(): Boolean {
    return WindowInsets.ime.getBottom(LocalDensity.current) > 0
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun ThinSearchBar(
    modifier: Modifier = Modifier,
    onClear: () -> Unit,
    textFieldState: TextFieldState,
    onFocusChanged: (Boolean) -> Unit = {},
) {


    val focusManager = LocalFocusManager.current
    val isImeVisible = isKeyboardVisible()
    LaunchedEffect(isImeVisible) {
        if (!isImeVisible) {
            focusManager.clearFocus() // Clear focus when keyboard is hidden
            onFocusChanged(false)
        }
    }
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painterResource(Res.drawable.x), contentDescription = "")
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged {
                            onFocusChanged(it.isFocused)
                        },
                    state = textFieldState,
                    cursorBrush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    ),
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    ),
                )
            }

            if (textFieldState.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onClear()
                        focusManager.clearFocus()
                    }) {
                    Icon(painterResource(Res.drawable.x), contentDescription = "")
                }
            }
        }
    }
}

