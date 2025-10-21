package dev.tekofx.artganizer.ui.components.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DesktopLayout(
    leftPanel: @Composable () -> Unit = {},
    rightPanel: @Composable () -> Unit = {},
    bottomPanel: @Composable () -> Unit = {},
    mainPanel: @Composable () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.fillMaxWidth().weight(0.8f)) {
            Column(modifier = Modifier.weight(0.2f)) {
                Surface(modifier = Modifier.fillMaxSize(), tonalElevation = 50.dp) {
                    leftPanel()
                }
            }
            Column(modifier = Modifier.weight(0.5f)) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    mainPanel()
                }
            }
            Column(modifier = Modifier.weight(0.3f)) {
                Surface(modifier = Modifier.fillMaxSize(), tonalElevation = 50.dp) {
                    rightPanel()
                }
            }

        }
        Row(modifier = Modifier.fillMaxWidth().weight(0.2f)) {
            Surface(modifier = Modifier.fillMaxSize()) {
                bottomPanel()
            }
        }
    }
}