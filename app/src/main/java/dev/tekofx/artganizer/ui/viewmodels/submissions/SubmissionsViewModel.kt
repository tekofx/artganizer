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
import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.utils.dateToString
import dev.tekofx.artganizer.utils.getImageInfo
import dev.tekofx.artganizer.utils.getPaletteFromUri
import dev.tekofx.artganizer.utils.removeImageFromInternalStorage
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import dev.tekofx.artganizer.utils.stringToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date

data class SubmissionUiState(
    val submissionDetails: SubmissionDetails = SubmissionDetails(),
    val imagePaths: List<Uri> = listOf<Uri>(),
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
    val palette: List<Int> = emptyList(),
    val artistId: Int? = null,
    val artist: Artist? = null
)

fun SubmissionDetails.toSubmissionWithArtist(): SubmissionWithArtist = SubmissionWithArtist(
    submission = Submission(
        id = id,
        title = title,
        description = description,
        imagePath = imagePath.toString(),
        rating = rating,
        date = stringToDate(date) ?: Date(),
        sizeInMb = sizeInMb,
        dimensions = dimensions,
        extension = extension,
        palette = palette,
        artistId = artistId
    ),
    artist = artist
)


fun SubmissionWithArtist.toSubmissionDetails(): SubmissionDetails = SubmissionDetails(
    id = submission.id,
    title = submission.title,
    description = submission.description,
    imagePath = submission.imagePath.toUri(),
    rating = submission.rating,
    date = submission.date.toString(),
    sizeInMb = submission.sizeInMb,
    dimensions = submission.dimensions,
    extension = submission.extension,
    palette = submission.palette,
    artist = artist
)

fun SubmissionUiState.toSubmissionWithArtist(): SubmissionWithArtist = SubmissionWithArtist(
    Submission(
        id = submissionDetails.id,
        title = submissionDetails.title,
        description = submissionDetails.description,
        imagePath = submissionDetails.imagePath.toString(),
        rating = submissionDetails.rating,
        date = stringToDate(submissionDetails.date) ?: Date(),
        sizeInMb = submissionDetails.sizeInMb,
        dimensions = submissionDetails.dimensions,
        extension = submissionDetails.extension,
        palette = submissionDetails.palette,
        artistId = submissionDetails.artistId
    ),
    artist = submissionDetails.artist
)


class SubmissionsViewModel(private val repository: SubmissionRepository) : ViewModel() {

    var newSubmissionUiState by mutableStateOf(SubmissionUiState())
        private set


    var currentSubmissionUiState by mutableStateOf(SubmissionUiState())
        private set

    var currentEditingSubmissionUiState by mutableStateOf(SubmissionUiState())
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

    fun getSubmissionWithArtist(id: Int) {
        viewModelScope.launch {
            val submission = repository.getSubmissionWithArtist(id)
            if (submission == null) {
                Log.d("SubmissionsViewModel", "Submission with id $id not found")
                return@launch
            }
            currentSubmissionUiState =
                SubmissionUiState(
                    submissionDetails = submission.toSubmissionDetails(),
                    isEntryValid = validateInput(submission.toSubmissionDetails())
                )
        }
    }


    fun updateNewUiState(submissionDetails: SubmissionDetails) {
        Log.d("UpdateNewuistate", submissionDetails.toString())
        newSubmissionUiState = SubmissionUiState(
            submissionDetails,
            newSubmissionUiState.imagePaths
        )
    }

    fun updateCurrentUiState(submissionDetails: SubmissionDetails) {
        currentEditingSubmissionUiState =
            SubmissionUiState(
                submissionDetails = submissionDetails,
                isEntryValid = validateInput(submissionDetails)
            )
    }

    fun setShowEditSubmission(show: Boolean) {
        showEditSubmission.value = show
        currentEditingSubmissionUiState = if (show) {
            currentSubmissionUiState
        } else {
            SubmissionUiState()
        }
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
        val newSubmissions = mutableListOf<Submission>()
        newSubmissionUiState.imagePaths.forEach { imagePath ->
            val imagePath =
                saveImageToInternalStorage(
                    context,
                    imagePath
                )

            val imageInfo = getImageInfo(context, imagePath)
            val palette = getPaletteFromUri(context, imagePath)

            newSubmissions.add(
                Submission(
                    id = newSubmissionUiState.submissionDetails.id,
                    title = newSubmissionUiState.submissionDetails.title,
                    description = newSubmissionUiState.submissionDetails.description,
                    imagePath = imagePath.toString(),
                    rating = newSubmissionUiState.submissionDetails.rating,
                    date = stringToDate(newSubmissionUiState.submissionDetails.date) ?: Date(),
                    sizeInMb = imageInfo?.sizeInMb ?: 0.0,
                    dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                    extension = imageInfo?.extension ?: "",
                    palette = palette,
                    artistId = newSubmissionUiState.submissionDetails.artistId
                )
            )
        }

        repository.insertSubmissions(newSubmissions)
        currentSubmissionUiState = currentEditingSubmissionUiState
        currentEditingSubmissionUiState = SubmissionUiState()
    }

    suspend fun editSubmission() {
        val submission = currentEditingSubmissionUiState.submissionDetails.toSubmissionWithArtist()
        if (validateInput()) {
            repository.updateSubmissionWithArtist(submission.submission)
        }
    }


    fun updateNewSubmissionsUiState(submissions: SubmissionUiState) {
        newSubmissionUiState = submissions
        Log.d("UpdateNewSubmissionsUiState", submissions.toString())
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


