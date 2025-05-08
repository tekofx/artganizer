package dev.tekofx.artganizer.ui.viewmodels.gallery


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.repository.SubmissionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class SubmissionUiState(
    val submissionDetails: SubmissionDetails = SubmissionDetails(),
    val isEntryValid: Boolean = false
)

data class SubmissionDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val imagePath: String = "",
)

fun SubmissionDetails.toSubmission(): Submission = Submission(
    id = id,
    title = title,
    description = description,
    imagePath = imagePath
)

fun Submission.toSubmissionUiState(isEntryValid: Boolean = false): SubmissionUiState =
    SubmissionUiState(
        submissionDetails = this.toSubmissionDetails(),
        isEntryValid = isEntryValid
    )

fun Submission.toSubmissionDetails(): SubmissionDetails = SubmissionDetails(
    id = id,
    title = title,
    description = description,
    imagePath = imagePath
)


class SubmissionsViewModel(private val repository: SubmissionRepository) : ViewModel() {

    var submissionUiState by mutableStateOf(SubmissionUiState())
        private set

    // Data
    private val _submissions = MutableStateFlow<List<String>>(emptyList())
    val submissions = _submissions.asStateFlow()

    // Loaders
    val isLoading = MutableStateFlow(false)


    // Inputs
    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()
    private val _filtersApplied = MutableStateFlow(false)
    var filtersApplied = _filtersApplied.asStateFlow()

    // Error
    val errorMessage = MutableLiveData<String>()

    private fun validateInput(uiState: SubmissionDetails = submissionUiState.submissionDetails): Boolean {
        return with(uiState) {
            title.isNotBlank()
        }
    }

    fun updateUiState(submissionDetails: SubmissionDetails) {
        submissionUiState =
            SubmissionUiState(
                submissionDetails = submissionDetails,
                isEntryValid = validateInput(submissionDetails)
            )
    }

    suspend fun saveSubmission() {
        if (validateInput()) {
            repository.insertSubmission(submissionUiState.submissionDetails.toSubmission())
        }
    }


}

