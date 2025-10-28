package dev.tekofx.artganizer.managers

import kotlinx.coroutines.flow.MutableStateFlow

class UiStateManager {


    // Artists
    val showEditArtist = MutableStateFlow(false)
    val showDeletePopup = MutableStateFlow(false)

}