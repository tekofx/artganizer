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
import dev.tekofx.artganizer.repository.ArtistRepository
import dev.tekofx.artganizer.repository.ImageManager
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
class ArtistsViewModel(
    private val repository: ArtistRepository,
    private val imageManager: ImageManager
) : ViewModel() {

    // Data states
    var newArtistUiState by mutableStateOf(ArtistUiState())
        private set
    var currentArtistUiState by mutableStateOf(ArtistUiState())
        private set


    // UI State
    val textFieldState = TextFieldState()
    val showPopup = MutableStateFlow(false)
    val showEditArtist = MutableStateFlow(false)
    val isSearchBarFocused = MutableStateFlow(false)
    val listState = MutableStateFlow(LazyListState())

    val alignment = MutableStateFlow(Alignment.BottomCenter)
    val fabVisible = snapshotFlow {
        Pair(listState.value.firstVisibleItemIndex, isSearchBarFocused.value)
    }.map { (firstVisibleItemIndex, isSearchBarFocused) ->
        (listState.value.lastScrolledBackward || firstVisibleItemIndex == 0) &&
                !isSearchBarFocused && textFieldState.text.isEmpty()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )
    val searchBarVisible = snapshotFlow {
        Triple(
            listState.value.lastScrolledBackward,
            listState.value.firstVisibleItemIndex,
            textFieldState.text.isNotEmpty()
        )
    }.map { (lastScrolledBackward, firstShowedItem, isTextFieldNotEmpty) ->
        lastScrolledBackward || firstShowedItem == 0 || isTextFieldNotEmpty
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        true
    )

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
    fun setShowPopup(show: Boolean) {
        showPopup.value = show
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
        val newImagePath = if (imagePath != null) {
            imageManager.saveImageFromPath(imagePath, "artist_${System.currentTimeMillis()}.jpg")
            // Return app-specific path where it was saved
            "artist_${System.currentTimeMillis()}.jpg"
        } else null

        if (validateInput()) {
            repository.insertArtist(newArtistUiState.artistDetails.toArtistWithSubmissions().artist)
        }
    }

    suspend fun editArtist() {
        val imagePath = currentArtistUiState.artistDetails.imagePath

        if (imagePath != null) {
            val fileName = "thumb_${System.currentTimeMillis()}.jpg"
            val newImagePath = imageManager.saveThumbnail(imagePath, fileName)

            currentArtistUiState = currentArtistUiState.copy(
                artistDetails = currentArtistUiState.artistDetails.copy(imagePath = newImagePath)
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
                val fileName = imagePath.substringAfterLast("/")
                imageManager.removeImage(fileName)
            }
        }
    }


}


