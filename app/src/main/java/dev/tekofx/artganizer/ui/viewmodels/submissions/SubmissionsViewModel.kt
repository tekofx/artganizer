package dev.tekofx.artganizer.ui.viewmodels.submissions


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
import dev.tekofx.artganizer.utils.dateToString
import dev.tekofx.artganizer.utils.getImageInfo
import dev.tekofx.artganizer.utils.removeImageFromInternalStorage
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import dev.tekofx.artganizer.utils.stringToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date

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
    val date: String = dateToString(Date()),
    val sizeInMb: Double = 0.0,
    val dimensions: String = "",
    val extension: String = "",
    val artistId: Int? = null
)

fun SubmissionDetails.toSubmission(): Submission = Submission(
    id = id,
    title = title,
    description = description,
    imagePath = imagePath.toString(),
    rating = rating,
    date = stringToDate(date) ?: Date(),
    sizeInMb = sizeInMb,
    dimensions = dimensions,
    extension = extension,
    artistId = artistId

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

fun SubmissionUiState.toSubmission(): Submission = Submission(
    id = submissionDetails.id,
    title = submissionDetails.title,
    description = submissionDetails.description,
    imagePath = submissionDetails.imagePath.toString(),
    rating = submissionDetails.rating,
    date = stringToDate(submissionDetails.date) ?: Date(),
    sizeInMb = submissionDetails.sizeInMb,
    dimensions = submissionDetails.dimensions,
    extension = submissionDetails.extension,
    artistId = submissionDetails.artistId
)


class SubmissionsViewModel(private val repository: SubmissionRepository) : ViewModel() {

    var newSubmissionUiState by mutableStateOf(SubmissionUiState())
        private set

    var currentSubmissionUiState by mutableStateOf(SubmissionUiState())
        private set

    // Data
    val submissions = MutableStateFlow<List<Submission>>(emptyList())

    // Ui State
    val showPopup = MutableStateFlow(false)
    val showEditSubmission = MutableStateFlow(false)


    private fun validateInput(uiState: SubmissionDetails = newSubmissionUiState.submissionDetails): Boolean {
        return with(uiState) {
            true
        }
    }

    fun getSubmissionById(id: String) {
        viewModelScope.launch {
            val submission = submissions.value.find { it.id == id.toInt() }
            if (submission != null) {
                currentSubmissionUiState =
                    SubmissionUiState(
                        submissionDetails = submission.toSubmissionDetails(),
                        isEntryValid = validateInput(submission.toSubmissionDetails())
                    )
            }
        }
    }

    fun updateNewUiState(submissionDetails: SubmissionDetails) {
        newSubmissionUiState =
            SubmissionUiState(
                submissionDetails = submissionDetails,
                isEntryValid = validateInput(submissionDetails)
            )
    }

    fun updateCurrentUiState(submissionDetails: SubmissionDetails) {
        currentSubmissionUiState =
            SubmissionUiState(
                submissionDetails = submissionDetails,
                isEntryValid = validateInput(submissionDetails)
            )
    }

    fun setShowEditSubmission(show: Boolean) {
        showEditSubmission.value = show
    }

    fun setShowPopup(show: Boolean) {
        showPopup.value = show
    }

    fun deleteSubmission(context: Context, submission: Submission) {
        viewModelScope.launch {
            repository.deleteSubmission(submission)
            removeImageFromInternalStorage(context, submission.imagePath)
        }
    }

    suspend fun saveSubmission(context: Context) {
        // Save image to private storage
        val imagePath =
            saveImageToInternalStorage(context, newSubmissionUiState.submissionDetails.imagePath)

        val imageInfo = getImageInfo(context, imagePath)

        val newSubmission = Submission(
            id = newSubmissionUiState.submissionDetails.id,
            title = newSubmissionUiState.submissionDetails.title,
            description = newSubmissionUiState.submissionDetails.description,
            imagePath = imagePath.toString(),
            rating = newSubmissionUiState.submissionDetails.rating,
            date = stringToDate(newSubmissionUiState.submissionDetails.date) ?: Date(),
            sizeInMb = imageInfo?.sizeInMb ?: 0.0,
            dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
            extension = imageInfo?.extension ?: "",
            artistId = newSubmissionUiState.submissionDetails.artistId
        )

        if (validateInput()) {
            repository.insertSubmission(newSubmission)
        }
    }

    suspend fun editSubmission() {
        val submission = currentSubmissionUiState.submissionDetails.toSubmission()
        if (validateInput()) {
            repository.updateSubmission(submission)
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


