package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.tekofx.artganizer.ui.components.layout.DesktopLayout
import dev.tekofx.artganizer.ui.components.layout.LeftPanel

@Composable
actual fun ArtistsScreen() {
    DesktopLayout(
        mainPanel = {
            Text("Artists Screen - Desktop Version")
        },
        leftPanel = {
            LeftPanel()
        }
    )

}