package dev.tekofx.artganizer.ui.viewmodels.artists


import android.content.Context
import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.repository.ArtistRepository
import dev.tekofx.artganizer.utils.removeImageFromInternalStorage
import dev.tekofx.artganizer.utils.saveThumbnail
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


@OptIn(FlowPreview::class)
class ArtistsViewModel(private val repository: ArtistRepository) : ViewModel() {

    // Data states
    var newArtistUiState by mutableStateOf(ArtistUiState())
        private set
    var currentArtistUiState by mutableStateOf(ArtistUiState())
        private set

    val state = TextFieldState()


    // UI State
    val showPopup = MutableStateFlow(false)
    val showEditArtist = MutableStateFlow(false)
    val isSearchBarFocused = MutableStateFlow(false)

    // Inputs
    val queryText = snapshotFlow {
        state.text.toString()
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
        state.clearText()
    }

    fun setIsSearchBarFocused(isFocused: Boolean) {
        isSearchBarFocused.value = isFocused
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
        Log.d("ArtistsViewModel", "updateCurrentUiState: $artistDetails")
        currentArtistUiState =
            ArtistUiState(
                artistDetails = artistDetails,
                isEntryValid = validateInput(artistDetails)
            )
    }

    //////////////////////// Database Operations ////////////////////////
    fun getArtistWithSubmissions(id: Int) {
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

    suspend fun saveArtist(context: Context) {
        val imagePath = newArtistUiState.artistDetails.imagePath

        if (imagePath != null) {
            // Save image to private storage
            val newImagePath =
                saveThumbnail(
                    context,
                    imagePath
                )

            newArtistUiState = newArtistUiState.copy(
                artistDetails = newArtistUiState.artistDetails.copy(
                    imagePath = newImagePath
                )
            )

        }
        if (validateInput()) {
            repository.insertArtist(newArtistUiState.artistDetails.toArtistWithSubmissions().artist)
        }
    }

    suspend fun editArtist(context: Context) {
        val imagePath = currentArtistUiState.artistDetails.imagePath

        if (imagePath != null) {
            // Save image to private storage
            val newImagePath =
                saveThumbnail(
                    context,
                    imagePath
                )

            currentArtistUiState = currentArtistUiState.copy(
                artistDetails = currentArtistUiState.artistDetails.copy(
                    imagePath = newImagePath
                )
            )

        }
        if (validateInput(currentArtistUiState.artistDetails)) {
            Log.d("ArtistsViewModel", "editArtist: ${currentArtistUiState.artistDetails}")
            repository.updateArtist(currentArtistUiState.artistDetails.toArtistWithSubmissions().artist)
            currentArtistUiState = currentArtistUiState.copy(
                artistDetails = currentArtistUiState.artistDetails,
                isEntryValid = false
            )
        }
    }

    fun deleteArtist(context: Context, artist: ArtistUiState) {
        viewModelScope.launch {
            repository.deleteArtist(artist.toArtistWithSubmissions().artist)
            artist.artistDetails.imagePath?.let {
                removeImageFromInternalStorage(context, artist.artistDetails.imagePath)
            }
        }
    }


}


