package dev.tekofx.artganizer.viewmodel

import androidx.lifecycle.ViewModel
import dev.tekofx.artganizer.managers.DesktopUiManager
import dev.tekofx.artganizer.managers.DialogContent
import dev.tekofx.artganizer.managers.RightPanelContent

class DesktopUiViewModel(
    val desktopUiManager: DesktopUiManager
) : ViewModel() {


    // Panels show status
    val showLeftPanel = desktopUiManager.showLeftPanel
    val showRightPanel = desktopUiManager.showRightPanel


    val dialogContent = desktopUiManager.dialogContent
    val rightPanelContent = desktopUiManager.rightPanelContent


    fun setDialogContent(dialogContent: DialogContent) {
        desktopUiManager.setDialogContent(dialogContent)
    }

    fun setRightPanelContent(rightPanelContent: RightPanelContent) {
        desktopUiManager.setRightPanelContent(rightPanelContent)
    }

    fun toggleRightPanel() {
        desktopUiManager.setShowRightPanel(!showRightPanel.value)
    }

    fun toggleLeftPanel() {
        desktopUiManager.setShowLeftPanel(!showLeftPanel.value)
    }


}