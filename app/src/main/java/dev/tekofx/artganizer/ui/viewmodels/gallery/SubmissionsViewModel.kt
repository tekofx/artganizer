package dev.tekofx.artganizer.ui.viewmodels.gallery


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.Date


data class SubmissionUiState(
    val submissionDetails: SubmissionDetails = SubmissionDetails(),
    val isEntryValid: Boolean = false
)

data class SubmissionDetails(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val imagePath: Uri = Uri.EMPTY,
    val rating: Int = 0,
    val date: String = "",
    val sizeInMb: Double = 0.0,
    val dimensions: String = "",
    val extension: String = "",
)

fun SubmissionDetails.toSubmission(): Submission = Submission(
    id = id,
    title = title,
    description = description,
    imagePath = imagePath.toString(),
    rating = rating,
    date = Date.valueOf(date),
    sizeInMb = sizeInMb,
    dimensions = dimensions,
    extension = extension

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
    imagePath = imagePath.toUri(),
    rating = rating,
    date = date.toString(),
    sizeInMb = sizeInMb,
    dimensions = dimensions,
    extension = extension

)


class SubmissionsViewModel(private val repository: SubmissionRepository) : ViewModel() {

    var newSubmissionUiState by mutableStateOf(SubmissionUiState())
        private set

    // Data
    val submissions = MutableStateFlow<List<Submission>>(emptyList())

    // Loaders
    val isLoading = MutableStateFlow(false)


    // Inputs
    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()
    private val _filtersApplied = MutableStateFlow(false)
    var filtersApplied = _filtersApplied.asStateFlow()


    private fun validateInput(uiState: SubmissionDetails = newSubmissionUiState.submissionDetails): Boolean {
        return with(uiState) {
            true
        }
    }

    fun getSubmissionById(id: String): Submission? {
        return submissions.value.find { it.id == id.toInt() }
    }

    fun updateUiState(submissionDetails: SubmissionDetails) {
        newSubmissionUiState =
            SubmissionUiState(
                submissionDetails = submissionDetails,
                isEntryValid = validateInput(submissionDetails)
            )
    }

    suspend fun saveSubmission(context: Context) {
        // Save image to private storage
        val imagePath =
            saveImageToInternalStorage(context, newSubmissionUiState.submissionDetails.imagePath)

        // Update the image path in the submission details
        newSubmissionUiState = newSubmissionUiState.copy(
            submissionDetails = newSubmissionUiState.submissionDetails.copy(
                imagePath = imagePath
            )
        )

        if (validateInput()) {
            repository.insertSubmission(newSubmissionUiState.submissionDetails.toSubmission())
        }
    }

    fun setNewSubmissionDetails(submissionDetails: SubmissionDetails) {
        newSubmissionUiState = newSubmissionUiState.copy(
            submissionDetails = submissionDetails,
            isEntryValid = validateInput(submissionDetails)
        )
    }


    init {
        // Collect the flow and update _submissions
        viewModelScope.launch {
            Log.d("SubmissionsViewModel", "Initializing SubmissionsViewModel")
            repository.getAllSubmissions().collect { submissionsList ->
                submissions.value = submissionsList
            }
        }
    }


}

