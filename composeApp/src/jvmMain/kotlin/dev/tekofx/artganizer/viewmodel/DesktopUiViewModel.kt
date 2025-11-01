package dev.tekofx.artganizer.viewmodel

import androidx.lifecycle.ViewModel
import dev.tekofx.artganizer.managers.DesktopUiManager
import dev.tekofx.artganizer.managers.DialogContent

class DesktopUiViewModel(
    val desktopUiManager: DesktopUiManager
) : ViewModel() {
    val showLeftPanel = desktopUiManager.showLeftPanel
    val showDialog = desktopUiManager.showDialog
    val dialogContent = desktopUiManager.dialogContent

    fun toggleDialog() {
        desktopUiManager.toggleDialog()
    }

    fun setDialogContent(dialogContent: DialogContent) {
        desktopUiManager.setDialogContent(dialogContent)
    }
}