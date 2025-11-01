package dev.tekofx.artganizer.managers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class RightPanelContent {
    NONE
}

enum class DialogContent {
    NONE,
    SUBMISSIONS_FORM,
    ARTIST_FORM
}

class DesktopUiManager {
    private val _showLeftPanel = MutableStateFlow(false)
    val showLeftPanel: StateFlow<Boolean> = _showLeftPanel.asStateFlow()

    private val _showRightPanel = MutableStateFlow(false)
    val showRightPanel: StateFlow<Boolean> = _showRightPanel.asStateFlow()

    private val _showBottomPanel = MutableStateFlow(false)
    val showBottomPanel: StateFlow<Boolean> = _showBottomPanel.asStateFlow()
    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog.asStateFlow()


    private val _rightPanelContent = MutableStateFlow(RightPanelContent.NONE)
    val rightPanelContent: StateFlow<RightPanelContent> = _rightPanelContent.asStateFlow()


    private val _dialogContent = MutableStateFlow(DialogContent.NONE)
    val dialogContent: StateFlow<DialogContent> = _dialogContent.asStateFlow()


    fun toggleDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun setDialogContent(dialogContent: DialogContent) {
        this._dialogContent.value = dialogContent
    }
}