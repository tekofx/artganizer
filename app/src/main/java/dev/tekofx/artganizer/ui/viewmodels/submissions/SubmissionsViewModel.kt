package dev.tekofx.artganizer.ui.viewmodels.submissions


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.utils.getImageInfo
import dev.tekofx.artganizer.utils.getPaletteFromUri
import dev.tekofx.artganizer.utils.removeImagesFromInternalStorage
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import dev.tekofx.artganizer.utils.stringToDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date


enum class SaveImagesOptions {
    EMPTY,
    SINGLE_SUBMISSION,
    MULTIPLE_SUBMISSIONS
}

class SubmissionsViewModel(private val repository: SubmissionRepository) : ViewModel() {

    var newSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    var currentSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    var editingSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    var saveImagesOption by mutableStateOf(SaveImagesOptions.EMPTY)
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
            Log.d("GetSubmissionWithArtist", submission.toString())
            currentSubmissionDetails = submission.toSubmissionDetails()
            uris = submission.submission.imagesPath
        }
    }


    fun updateNewUiState(submissionDetails: SubmissionDetails) {
        newSubmissionDetails = submissionDetails
    }

    fun updateEditingUiState(submissionDetails: SubmissionDetails) {
        Log.d("updateEditingUiState", editingSubmissionDetails.toString())
        editingSubmissionDetails = submissionDetails
    }

    fun updateSaveImagesOption(imagesOption: SaveImagesOptions) {
        saveImagesOption = imagesOption
    }


    fun setShowEditSubmission(show: Boolean) {
        showEditSubmission.value = show
        if (show) {
            editingSubmissionDetails = currentSubmissionDetails
        }
    }

    fun setShowPopup(show: Boolean) {
        showPopup.value = show

    }

    fun deleteSubmission(context: Context, submission: Submission) {
        viewModelScope.launch {
            repository.deleteSubmission(submission)
            removeImagesFromInternalStorage(context, submission.imagesPath)
        }
    }

    suspend fun saveSubmission(context: Context) {
        val newSubmissions = mutableListOf<Submission>()

        if (saveImagesOption == SaveImagesOptions.SINGLE_SUBMISSION) {
            val submissionUris = mutableListOf<Uri>()

            uris.forEach { uri ->
                val imagePath =
                    saveImageToInternalStorage(
                        context,
                        uri
                    )
                submissionUris.add(imagePath)

            }
            val imageInfo = getImageInfo(context, submissionUris[0])
            val palette = getPaletteFromUri(context, submissionUris[0])
            val submission = Submission(
                id = newSubmissionDetails.id,
                title = newSubmissionDetails.title,
                description = newSubmissionDetails.description,
                imagesPath = submissionUris,
                rating = newSubmissionDetails.rating,
                date = stringToDate(newSubmissionDetails.date) ?: Date(),
                size = imageInfo?.sizeInBytes ?: 0L,
                dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                extension = imageInfo?.extension ?: "",
                palette = palette,
                artistId = newSubmissionDetails.artistId
            )
            repository.insertSubmission(submission)
        } else {
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
                        imagesPath = listOf(imagePath),
                        rating = newSubmissionDetails.rating,
                        date = stringToDate(newSubmissionDetails.date) ?: Date(),
                        size = imageInfo?.sizeInBytes ?: 0L,
                        dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                        extension = imageInfo?.extension ?: "",
                        palette = palette,
                        artistId = newSubmissionDetails.artistId
                    )
                )
            }
            repository.insertSubmissions(newSubmissions)
        }

        currentSubmissionDetails = editingSubmissionDetails
        editingSubmissionDetails = SubmissionDetails()
        uris = emptyList()
        saveImagesOption = SaveImagesOptions.EMPTY
    }

    suspend fun editSubmission() {
        Log.d("editSubmission", editingSubmissionDetails.toString())
        val submission = editingSubmissionDetails.toSubmissionWithArtist()
        Log.d("editSumission2", submission.toString())
        repository.updateSubmissionWithArtist(submission.submission)
        editingSubmissionDetails = SubmissionDetails()
        currentSubmissionDetails = submission.toSubmissionDetails()
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


