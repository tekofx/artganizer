package dev.tekofx.artganizer.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.tekofx.artganizer.managers.DialogContent
import dev.tekofx.artganizer.managers.RightPanelContent
import dev.tekofx.artganizer.ui.components.layout.DesktopLayout
import dev.tekofx.artganizer.ui.components.layout.LeftPanel
import dev.tekofx.artganizer.ui.components.layout.rightPanel.RightPanel
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionsViewModel
import dev.tekofx.artganizer.viewmodel.DesktopUiViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen() {
    val desktopUiViewModel = koinViewModel<DesktopUiViewModel>()
    val artistsViewModel = koinViewModel<ArtistsViewModel>()
    val submissionsViewModel = koinViewModel<SubmissionsViewModel>()

    // Panels show status
    val showLeftPanel by desktopUiViewModel.showLeftPanel.collectAsState()
    val showRightPanel by desktopUiViewModel.showRightPanel.collectAsState()


    DesktopLayout(
        leftPanel = {
            LeftPanel(
                onArtistClick = {
                    artistsViewModel.getArtistWithSubmissions(it)
                    desktopUiViewModel.setRightPanelContent(RightPanelContent.ARTIST_DETAILS)
                    if (!showLeftPanel) {
                        desktopUiViewModel.toggleRightPanel()
                    }
                },
                onSubmissionAddClick = {
                    submissionsViewModel.setNewFiles()
                    desktopUiViewModel.setDialogContent(DialogContent.SUBMISSIONS_FORM)
                },
                onArtistAddClick = {
                    desktopUiViewModel.setDialogContent(DialogContent.ARTIST_FORM)
                },
                onCharacterAddClick = {
                    desktopUiViewModel.setDialogContent(DialogContent.CHARACTER_FORM)
                },
                onTagAddClick = {},
            )
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

