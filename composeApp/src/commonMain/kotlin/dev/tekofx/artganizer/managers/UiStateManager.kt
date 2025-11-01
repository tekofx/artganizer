package dev.tekofx.artganizer.managers

import io.github.vinceglb.filekit.PlatformFile
import kotlinx.coroutines.flow.MutableStateFlow


class UiStateManager {


    // Artists
    val showEditArtist = MutableStateFlow(false)
    val showDeletePopup = MutableStateFlow(false)

    val files = MutableStateFlow<List<PlatformFile>>(emptyList())


}