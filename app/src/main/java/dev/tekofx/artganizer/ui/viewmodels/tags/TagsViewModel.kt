package dev.tekofx.artganizer.ui.viewmodels.tags


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.TagWithSubmissions
import dev.tekofx.artganizer.repository.TagRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TagsViewModel(private val repository: TagRepository) : ViewModel() {

    // Data states
    var newTagUiState by mutableStateOf(TagUiState())
        private set
    var currentTagUiState by mutableStateOf(TagUiState())
        private set

    // UI State
    val showPopup = MutableStateFlow(false)
    val showTagEdit = MutableStateFlow(false)

    // Inputs
    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()

    // Data
    val areThereTags = mutableStateOf(false)
    private val _tags = MutableStateFlow<List<TagWithSubmissions>>(emptyList())
    val tags = _tags
        .combine(_queryText) { tags, query ->
            if (query.isBlank()) {
                tags
            } else {
                tags.filter {
                    it.tag.name.contains(query, ignoreCase = true)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _tags.value
        )


    init {
        // Collect the flow and update _submissions
        viewModelScope.launch {
            repository.getAllTags().collect { submissionsList ->
                _tags.value = submissionsList
                if (submissionsList.isNotEmpty()) {
                    areThereTags.value = true
                }
            }
        }
    }


    //////////////////////// UI ////////////////////////
    private fun validateInput(uiState: TagDetails = newTagUiState.tagDetails): Boolean {
        return with(uiState) {
            uiState.name.isNotEmpty()
        }
    }

    /**
     * Callback of TextField
     */
    fun onSearchTextChanged(text: String) {
        _queryText.value = text
    }

    //////////////////////// Setters ////////////////////////
    fun setShowPopup(show: Boolean) {
        showPopup.value = show
    }

    fun setShowEditArtist(show: Boolean) {
        showTagEdit.value = show
    }

    fun updateNewUiState(tagDetails: TagDetails) {
        newTagUiState =
            TagUiState(
                tagDetails = tagDetails,
                isEntryValid = validateInput(tagDetails)
            )
    }

    fun clearNewUiState() {
        newTagUiState = TagUiState()
    }

    fun updateCurrentUiState(tagDetails: TagDetails) {
        Log.d("ArtistsViewModel", "updateCurrentUiState: $tagDetails")
        currentTagUiState =
            TagUiState(
                tagDetails = tagDetails,
                isEntryValid = validateInput(tagDetails)
            )
    }

    //////////////////////// Database Operations ////////////////////////
    fun getTagById(id: Long) {
        viewModelScope.launch {
            val tag = repository.getTagById(id)
            if (tag == null) {
                return@launch
            }
            currentTagUiState =
                TagUiState(
                    tagDetails = tag.toTagDetails(),
                    isEntryValid = validateInput(tag.toTagDetails())
                )
        }
    }

    suspend fun saveTag() {
        if (validateInput()) {
            repository.insertTag(newTagUiState.tagDetails.toTagWithSubmissions().tag)
        }
    }

    suspend fun editTag() {

        if (validateInput(currentTagUiState.tagDetails)) {
            Log.d("TagsViewModel", "editTag: ${currentTagUiState.tagDetails}")
            repository.updateTag(currentTagUiState.tagDetails.toTagWithSubmissions().tag)
            currentTagUiState = currentTagUiState.copy(
                tagDetails = currentTagUiState.tagDetails,
                isEntryValid = false
            )
        }
    }

    fun deleteTag(tag: TagUiState) {
        viewModelScope.launch {
            repository.deleteTag(tag.toTagWithSubmissions().tag)
        }
    }
}


