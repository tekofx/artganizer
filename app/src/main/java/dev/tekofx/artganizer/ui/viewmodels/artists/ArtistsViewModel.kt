package dev.tekofx.artganizer.ui.viewmodels.artists


import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.ArtistWithSubmissions
import dev.tekofx.artganizer.entities.Submission
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
    val socialNetworks: List<String> = emptyList(),
    val submissions: List<Submission> = emptyList()
)

fun ArtistDetails.toArtistWithSubmissions(): ArtistWithSubmissions = ArtistWithSubmissions(
    Artist(
        id = id,
        name = name,
        imagePath = imagePath,
        socialNetworks = socialNetworks
    ),
    submissions = submissions
)

fun ArtistDetails.toArtist(): Artist = Artist(
    id = id,
    name = name,
    imagePath = imagePath,
    socialNetworks = socialNetworks,
)

fun ArtistWithSubmissions.toArtisUiState(isEntryValid: Boolean = false): ArtistUiState =
    ArtistUiState(
        artistDetails = this.toArtistDetails(),
        isEntryValid = isEntryValid
    )

fun ArtistWithSubmissions.toArtistDetails(): ArtistDetails = ArtistDetails(
    id = artist.id,
    name = artist.name,
    imagePath = artist.imagePath,
    socialNetworks = artist.socialNetworks,
    submissions = submissions
)

fun ArtistUiState.toArtistWithSubmissions(): ArtistWithSubmissions = ArtistWithSubmissions(
    Artist(
        id = artistDetails.id,
        name = artistDetails.name,
        imagePath = artistDetails.imagePath,
        socialNetworks = artistDetails.socialNetworks
    ),
    submissions = artistDetails.submissions
)

class ArtistsViewModel(private val repository: ArtistsRepository) : ViewModel() {

    // Data states
    var newArtistUiState by mutableStateOf(ArtistUiState())
        private set
    var currentArtistUiState by mutableStateOf(ArtistUiState())
        private set

    // UI State
    val showPopup = MutableStateFlow(false)
    val showEditArtist = MutableStateFlow(false)

    // Inputs
    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    // Data
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists = _artists
        .combine(_queryText) { artists, query ->
            if (query.isBlank()) {
                artists
            } else {
                artists.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _artists.value
        )

    private fun validateInput(uiState: ArtistDetails = newArtistUiState.artistDetails): Boolean {
        return with(uiState) {
            uiState.name.isNotEmpty()
        }
    }

    fun setShowPopup(show: Boolean) {
        showPopup.value = show
    }

    fun setShowEditArtist(show: Boolean) {
        showEditArtist.value = show
    }

    fun getArtistWithSubmissions(id: Int) {
        viewModelScope.launch {
            val artist = repository.getArtistWithSubmissions(id)
            currentArtistUiState =
                ArtistUiState(
                    artistDetails = artist.toArtistDetails(),
                    isEntryValid = validateInput(artist.toArtistDetails())
                )
        }
    }


    fun updateNewUiState(artistDetails: ArtistDetails) {
        newArtistUiState =
            ArtistUiState(
                artistDetails = artistDetails,
                isEntryValid = validateInput(artistDetails)
            )
    }

    fun updateCurrentUiState(artistDetails: ArtistDetails) {
        currentArtistUiState =
            ArtistUiState(
                artistDetails = artistDetails,
                isEntryValid = validateInput(artistDetails)
            )
    }

    fun deleteArtist(artist: ArtistUiState) {
        viewModelScope.launch {
            repository.deleteArtist(artist.toArtistWithSubmissions().artist)
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
            repository.insertArtist(newArtistUiState.artistDetails.toArtistWithSubmissions().artist)
            newArtistUiState = newArtistUiState.copy(
                artistDetails = ArtistDetails(),
                isEntryValid = false
            )
        }
    }

    suspend fun editArtist(context: Context) {
        val imagePath = currentArtistUiState.artistDetails.imagePath

        if (imagePath != null) {
            // Save image to private storage
            val newImagePath =
                saveImageToInternalStorage(
                    context,
                    imagePath.toUri()
                )

            currentArtistUiState = currentArtistUiState.copy(
                artistDetails = currentArtistUiState.artistDetails.copy(
                    imagePath = newImagePath.toString()
                )
            )

        }
        if (validateInput(currentArtistUiState.artistDetails)) {
            repository.updateArtist(currentArtistUiState.artistDetails.toArtistWithSubmissions().artist)
            currentArtistUiState = currentArtistUiState.copy(
                artistDetails = currentArtistUiState.artistDetails,
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


