package dev.tekofx.artganizer.ui.viewmodels.artists


import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.managers.UiStateManager
import dev.tekofx.artganizer.repository.ArtistRepository
import dev.tekofx.artganizer.utils.AppLogger
import dev.tekofx.artganizer.utils.saveThumbnailFromPath
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.delete
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
class ArtistsViewModel(
    private val repository: ArtistRepository,
    private val uiStateManager: UiStateManager,
) : ViewModel() {

    // Data states
    var newArtistUiState by mutableStateOf(ArtistUiState())
        private set
    var currentArtistUiState by mutableStateOf(ArtistUiState())
        private set


    // UI State
    val textFieldState = TextFieldState()
    val showDeletePopup = uiStateManager.showDeletePopup
    val showEditArtist = uiStateManager.showEditArtist

    val isSearchBarFocused = MutableStateFlow(false)
    val listState = MutableStateFlow(LazyListState())

    val alignment = MutableStateFlow(Alignment.BottomCenter)

    // Inputs
    val queryText = snapshotFlow {
        textFieldState.text.toString()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ""
    )

    //val queryText = _queryText.asStateFlow()

    // Data
    val areThereArtists = mutableStateOf(false)
    private val _artists = MutableStateFlow<List<ArtistWithSubmissions>>(emptyList())
    val artists = _artists
        .combine(queryText) { artists, query ->
            if (query.isBlank()) {
                artists
            } else {
                artists.filter {
                    it.artist.name.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _artists.value
        )


    init {
        // Collect the flow and update _submissions
        viewModelScope.launch {
            repository.getAllArtistsWithSubmissions().collect { submissionsList ->
                _artists.value = submissionsList
                if (submissionsList.isNotEmpty()) {
                    areThereArtists.value = true
                }
            }
        }
    }


    //////////////////////// UI ////////////////////////
    private fun validateInput(uiState: ArtistDetails = newArtistUiState.artistDetails): Boolean {
        return with(uiState) {
            uiState.name.isNotEmpty()
        }
    }

    fun clearTextField() {
        textFieldState.clearText()
    }

    fun setIsSearchBarFocused(isFocused: Boolean) {
        isSearchBarFocused.value = isFocused

        alignment.value = if (isFocused) {
            Alignment.TopCenter
        } else {
            Alignment.BottomCenter
        }
    }

    /**
     * Callback of TextField
     */
    fun onSearchTextChanged(text: String) {
        //_queryText.value = text
    }

    //////////////////////// Setters ////////////////////////
    fun setShowDeletePopup(show: Boolean) {
        showDeletePopup.value = show
    }

    fun setShowEditArtist(show: Boolean) {
        showEditArtist.value = show
    }

    fun updateNewUiState(artistDetails: ArtistDetails) {
        newArtistUiState =
            ArtistUiState(
                artistDetails = artistDetails,
                isEntryValid = validateInput(artistDetails)
            )
    }

    fun clearNewUiState() {
        newArtistUiState = ArtistUiState()
    }

    fun updateCurrentUiState(artistDetails: ArtistDetails) {
        currentArtistUiState =
            ArtistUiState(
                artistDetails = artistDetails,
                isEntryValid = validateInput(artistDetails)
            )
    }

    //////////////////////// Database Operations ////////////////////////
    fun getArtistWithSubmissions(id: Long) {
        viewModelScope.launch {
            val artist = repository.getArtistWithSubmissions(id)
            if (artist == null) {
                return@launch
            }
            currentArtistUiState =
                ArtistUiState(
                    artistDetails = artist.toArtistDetails(),
                    isEntryValid = validateInput(artist.toArtistDetails())
                )
        }
    }

    fun saveArtist() = viewModelScope.launch {
        val imagePath = newArtistUiState.artistDetails.imagePath
        if (imagePath != null) {
            val thumbnail = saveThumbnailFromPath(imagePath)
            AppLogger.d("ArtistsViewModel", thumbnail.path)
            newArtistUiState = newArtistUiState.copy(
                artistDetails = newArtistUiState.artistDetails.copy(imagePath = thumbnail.path)
            )
        }

        if (validateInput()) {
            AppLogger.d("ArtistsViewModel", newArtistUiState.artistDetails.imagePath.toString())

            repository.insertArtist(newArtistUiState.artistDetails.toArtistWithSubmissions().artist)
        }
    }

    suspend fun editArtist() {
        val imagePath = currentArtistUiState.artistDetails.imagePath

        if (imagePath != null) {
            val thumbnail = saveThumbnailFromPath(imagePath)

            currentArtistUiState = currentArtistUiState.copy(
                artistDetails = currentArtistUiState.artistDetails.copy(imagePath = thumbnail.path)
            )
        }

        if (validateInput(currentArtistUiState.artistDetails)) {
            repository.updateArtist(currentArtistUiState.artistDetails.toArtistWithSubmissions().artist)
            currentArtistUiState = currentArtistUiState.copy(isEntryValid = false)
        }
    }

    fun deleteArtist(artist: ArtistUiState) {
        viewModelScope.launch {
            repository.deleteArtist(artist.toArtistWithSubmissions().artist)
            artist.artistDetails.imagePath?.let { imagePath ->
                val file = PlatformFile(imagePath)
                file.delete()
            }
        }
    }


}


