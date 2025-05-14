package dev.tekofx.artganizer.ui.viewmodels.artists


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.repository.ArtistsRepository
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ArtistUiState(
    val artistDetails: ArtistDetails = ArtistDetails(),
    val isEntryValid: Boolean = false
)

data class ArtistDetails(
    val id: Int = 0,
    val name: String = "",
    val imagePath: String? = null,
    val socialNetworks: List<String> = emptyList()
)

fun ArtistDetails.toArtist(): Artist = Artist(
    id = id,
    name = name,
    imagePath = imagePath,
    socialNetworks = socialNetworks
)

fun Artist.toArtisUiState(isEntryValid: Boolean = false): ArtistUiState =
    ArtistUiState(
        artistDetails = this.toArtistDetails(),
        isEntryValid = isEntryValid
    )

fun Artist.toArtistDetails(): ArtistDetails = ArtistDetails(
    id = id,
    name = name,
    imagePath = imagePath
)


class ArtistsViewModel(private val repository: ArtistsRepository) : ViewModel() {

    var newArtistUiState by mutableStateOf(ArtistUiState())
        private set


    val showPopup = MutableStateFlow(false)

    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    // Data
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())

    val artists = _artists.combine(_queryText) { artists, query ->
        if (query.isBlank()) {
            artists
        } else {
            artists.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _artists.value
    )


    private fun validateInput(uiState: ArtistDetails = newArtistUiState.artistDetails): Boolean {
        return with(uiState) {
            uiState.name.isNotEmpty()
        }
    }

    fun showPopup() {
        showPopup.value = true
    }

    fun hidePopup() {
        showPopup.value = false
    }

    fun getArtistById(id: String): Artist? {
        return _artists.value.find { it.id == id.toInt() }
    }

    fun updateUiState(artistDetails: ArtistDetails) {
        newArtistUiState =
            ArtistUiState(
                artistDetails = artistDetails,
                isEntryValid = validateInput(artistDetails)
            )
    }

    fun deleteArtist(artist: Artist) {
        viewModelScope.launch {
            repository.deleteArtist(artist)
        }
    }

    suspend fun saveArtist(context: Context) {
        val imagePath = newArtistUiState.artistDetails.imagePath

        if (imagePath != null) {

            // Save image to private storage
            val newImagePath =
                saveImageToInternalStorage(
                    context,
                    imagePath.toUri()
                )

            newArtistUiState = newArtistUiState.copy(
                artistDetails = newArtistUiState.artistDetails.copy(
                    imagePath = newImagePath.toString()
                )
            )

        }
        if (validateInput()) {
            repository.insertArtist(newArtistUiState.artistDetails.toArtist())
            newArtistUiState = newArtistUiState.copy(
                artistDetails = ArtistDetails(),
                isEntryValid = false
            )
        }
    }


    /**
     * Callback of TextField
     */
    fun onSearchTextChanged(text: String) {
        _queryText.value = text
    }


    init {
        // Collect the flow and update _submissions
        viewModelScope.launch {
            repository.getAllArtists().collect { submissionsList ->
                _artists.value = submissionsList
            }
        }
    }


}


