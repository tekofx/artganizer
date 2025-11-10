package dev.tekofx.artganizer.ui.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DesktopLayout(
    leftPanel: @Composable () -> Unit = {},
    toggleLeftPanelShow: () -> Unit,
    showLeftPanel: Boolean,
    rightPanel: @Composable () -> Unit = {},
    toggleRightPanelShow: () -> Unit,
    showRightPanel: Boolean,
    bottomPanel: @Composable () -> Unit = {},
    mainPanel: @Composable () -> Unit,
) {

    CreateDialog()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(0.8f).background(Color.Red),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PanelContainer(
                onToggleShowClick = toggleLeftPanelShow,
                showPanel = showLeftPanel,
                togglePosition = TogglePosition.RIGHT
            ) {
                leftPanel()
            }

            Surface(
                modifier = Modifier.weight(1f).fillMaxHeight(),
            ) {
                mainPanel()
            }

            PanelContainer(
                onToggleShowClick = toggleRightPanelShow,
                showPanel = showRightPanel,
                togglePosition = TogglePosition.LEFT
            ) {
                rightPanel()
            }
        }
        Row(modifier = Modifier.fillMaxWidth().weight(0.2f)) {
            Surface(modifier = Modifier.fillMaxSize()) {
                bottomPanel()
            }
        }
    }
}

