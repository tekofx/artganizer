package dev.tekofx.artganizer.ui.components.layout.rightPanel

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.tekofx.artganizer.managers.RightPanelContent
import dev.tekofx.artganizer.viewmodel.DesktopUiViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RightPanel() {
    val desktopUiViewModel = koinViewModel<DesktopUiViewModel>()

    val rightPanelContent by desktopUiViewModel.rightPanelContent.collectAsState()

    when (rightPanelContent) {
        RightPanelContent.ARTIST_DETAILS -> RightArtistPanel()
        RightPanelContent.SUBMISSION_DETAILS -> Text("Submission Details")
        RightPanelContent.CHARACTER_DETAILS -> Text("Character details")
        RightPanelContent.NONE -> Text("None")
    }


}