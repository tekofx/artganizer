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
import dev.tekofx.artganizer.utils.getImageInfo
import dev.tekofx.artganizer.utils.getPaletteFromUri
import dev.tekofx.artganizer.utils.removeImageFromInternalStorage
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import dev.tekofx.artganizer.utils.stringToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date


class SubmissionsViewModel(private val repository: SubmissionRepository) : ViewModel() {

    var newSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    var currentSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    var currentEditingSubmissionUiState by mutableStateOf(SubmissionDetails())
        private set

    var uris = listOf<Uri>()
        private set

    // Data
    val submissions = MutableStateFlow<List<Submission>>(emptyList())

    // Ui State
    val showPopup = MutableStateFlow(false)
    val showEditSubmission = MutableStateFlow(false)

    fun setUris(uris: List<Uri>) {
        this.uris = uris
    }


    fun getSubmissionWithArtist(id: Int) {
        viewModelScope.launch {
            val submission = repository.getSubmissionWithArtist(id)
            if (submission == null) {
                Log.d("SubmissionsViewModel", "Submission with id $id not found")
                return@launch
            }
            currentSubmissionDetails = submission.toSubmissionDetails()
            uris = listOf(submission.submission.imagePath.toUri())
        }
    }


    fun updateNewUiState(submissionDetails: SubmissionDetails) {
        newSubmissionDetails = submissionDetails
    }

    fun updateCurrentUiState(submissionDetails: SubmissionDetails) {
        currentEditingSubmissionUiState = submissionDetails
    }

    fun setShowEditSubmission(show: Boolean) {
        showEditSubmission.value = show
        currentEditingSubmissionUiState = if (show) {
            currentSubmissionDetails
        } else {
            SubmissionDetails()
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

        uris.forEach { uri ->
            val imagePath =
                saveImageToInternalStorage(
                    context,
                    uri
                )

            val imageInfo = getImageInfo(context, imagePath)
            val palette = getPaletteFromUri(context, imagePath)

            newSubmissions.add(
                Submission(
                    id = newSubmissionDetails.id,
                    title = newSubmissionDetails.title,
                    description = newSubmissionDetails.description,
                    imagePath = imagePath.toString(),
                    rating = newSubmissionDetails.rating,
                    date = stringToDate(newSubmissionDetails.date) ?: Date(),
                    sizeInMb = imageInfo?.sizeInMb ?: 0.0,
                    dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                    extension = imageInfo?.extension ?: "",
                    palette = palette,
                    artistId = newSubmissionDetails.artistId
                )
            )

        }
        repository.insertSubmissions(newSubmissions)
        currentSubmissionDetails = currentEditingSubmissionUiState
        currentEditingSubmissionUiState = SubmissionDetails()
        uris = emptyList()
    }

    suspend fun editSubmission() {
        val submission = currentEditingSubmissionUiState.toSubmissionWithArtist()
        repository.updateSubmissionWithArtist(submission.submission)
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


