package dev.tekofx.artganizer.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.tekofx.artganizer.ui.components.layout.DesktopLayout
import dev.tekofx.artganizer.ui.components.layout.LeftPanel
import dev.tekofx.artganizer.ui.components.layout.rightPanel.RightPanel
import dev.tekofx.artganizer.viewmodel.DesktopUiViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen() {
    val desktopUiViewModel = koinViewModel<DesktopUiViewModel>()

    // Panels show status
    val showLeftPanel by desktopUiViewModel.showLeftPanel.collectAsState()
    val showRightPanel by desktopUiViewModel.showRightPanel.collectAsState()


    DesktopLayout(
        leftPanel = {
            LeftPanel()
        },
        toggleLeftPanelShow = { desktopUiViewModel.toggleLeftPanel() },
        showLeftPanel = showLeftPanel,
        rightPanel = {
            RightPanel()
        },
        toggleRightPanelShow = { desktopUiViewModel.toggleRightPanel() },
        showRightPanel = showRightPanel
    ) {
        Text("Main panel")

    }

}

