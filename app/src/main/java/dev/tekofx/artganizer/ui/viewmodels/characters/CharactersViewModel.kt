package dev.tekofx.artganizer.ui.viewmodels.characters


import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.repository.CharactersRepository
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class CharactersViewModel(private val repository: CharactersRepository) : ViewModel() {

    // Data states
    var newCharacterUiState by mutableStateOf(CharacterUiState())
        private set
    var currentCharacterUiState by mutableStateOf(CharacterUiState())
        private set

    // UI State
    val showPopup = MutableStateFlow(false)
    val showCharacterEdit = MutableStateFlow(false)

    // Inputs
    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    // Data
    val areThereCharacters = mutableStateOf(false)
    private val _characters = MutableStateFlow<List<CharacterWithSubmissions>>(emptyList())
    val characters = _characters
        .combine(_queryText) { characters, query ->
            if (query.isBlank()) {
                characters
            } else {
                characters.filter {
                    it.character.name.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _characters.value
        )

    private fun validateInput(uiState: CharacterDetails = newCharacterUiState.characterDetails): Boolean {
        return with(uiState) {
            uiState.name.isNotEmpty()
        }
    }

    fun setShowPopup(show: Boolean) {
        showPopup.value = show
    }

    fun setShowEditArtist(show: Boolean) {
        showCharacterEdit.value = show
    }

    fun getCharacterWithSubmission(id: Long) {
        viewModelScope.launch {
            val character = repository.getCharacterWithSubmissions(id)
            if (character == null) {
                return@launch
            }
            currentCharacterUiState =
                CharacterUiState(
                    characterDetails = character.toCharacterDetails(),
                    isEntryValid = validateInput(character.toCharacterDetails())
                )
        }
    }


    fun updateNewUiState(characterDetails: CharacterDetails) {
        newCharacterUiState =
            CharacterUiState(
                characterDetails = characterDetails,
                isEntryValid = validateInput(characterDetails)
            )
    }

    fun updateCurrentUiState(characterDetails: CharacterDetails) {
        currentCharacterUiState =
            CharacterUiState(
                characterDetails = characterDetails,
                isEntryValid = validateInput(characterDetails)
            )
    }

    fun deleteCharacter(character: CharacterUiState) {
        viewModelScope.launch {
            repository.deleteCharacter(character.toCharacterWithSubmissions().character)
        }
    }

    suspend fun saveCharacter(context: Context) {
        val imagePath = newCharacterUiState.characterDetails.imagePath

        if (imagePath != null) {
            // Save image to private storage
            val newImagePath =
                saveImageToInternalStorage(
                    context,
                    imagePath.toUri()
                )

            newCharacterUiState = newCharacterUiState.copy(
                characterDetails = newCharacterUiState.characterDetails.copy(
                    imagePath = newImagePath.toString()
                )
            )

        }
        if (validateInput()) {
            repository.insertCharacter(newCharacterUiState.characterDetails.toCharacterWithSubmissions().character)
            newCharacterUiState = newCharacterUiState.copy(
                characterDetails = CharacterDetails(),
                isEntryValid = false
            )
        }
    }

    suspend fun editCharacter(context: Context) {
        Log.d("editARtist", currentCharacterUiState.characterDetails.toString())
        val imagePath = currentCharacterUiState.characterDetails.imagePath

        if (imagePath != null) {
            // Save image to private storage
            val newImagePath =
                saveImageToInternalStorage(
                    context,
                    imagePath.toUri()
                )

            currentCharacterUiState = currentCharacterUiState.copy(
                characterDetails = currentCharacterUiState.characterDetails.copy(
                    imagePath = newImagePath.toString()
                )
            )

        }
        if (validateInput(currentCharacterUiState.characterDetails)) {
            repository.updateCharacter(currentCharacterUiState.characterDetails.toCharacterWithSubmissions().character)
            currentCharacterUiState = currentCharacterUiState.copy(
                characterDetails = currentCharacterUiState.characterDetails,
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
            repository.getAllCharactersWithSubmissions().collect { charactersList ->
                _characters.value = charactersList
                if (charactersList.isNotEmpty()) {
                    areThereCharacters.value = true
                }
            }
        }
    }


}


