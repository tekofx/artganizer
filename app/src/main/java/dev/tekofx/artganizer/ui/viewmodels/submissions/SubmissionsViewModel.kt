package dev.tekofx.artganizer.ui.viewmodels.submissions


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.utils.getImageInfo
import dev.tekofx.artganizer.utils.getPaletteFromUri
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import dev.tekofx.artganizer.utils.saveThumbnail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

enum class SaveImagesOptions {
    EMPTY,
    SINGLE_SUBMISSION,
    MULTIPLE_SUBMISSIONS
}

class SubmissionsViewModel(
    private val submissionRepo: SubmissionRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {

    // New submission data used for creating a submission
    var newSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    // Current submission data
    var currentSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set


    // Data of submission to edit
    var editingSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    /**
     * What to do when selecting multiple images.
     *
     * [SaveImagesOptions.SINGLE_SUBMISSION] -> Create new submission with selected images
     * [SaveImagesOptions.MULTIPLE_SUBMISSIONS] -> Create a submission with each image
     */
    var saveImagesOption by mutableStateOf(SaveImagesOptions.EMPTY)
        private set

    // Uris of selected images
    var uris = listOf<Uri>()
        private set

    // Data
    val submissions = MutableStateFlow(SubmissionsUiState())

    // Ui State
    val showPopup = MutableStateFlow(false)
    val showEditSubmission = MutableStateFlow(false)
    val showFullscreen = MutableStateFlow(false)
    val currentImageIndex = MutableStateFlow(0) // Index of images in current submission
    val isLoading = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            submissionRepo.getAllSubmissions().collect { submissionsList ->
                Log.d("SubmissionsViewModel", submissionsList.toString())
                submissions.value = submissionsList.toSubmissionsUiState()
                currentImageIndex.value = 0
            }
        }
    }


    //////////////////////// Setters ////////////////////////

    fun setUris(uris: List<Uri>) {
        this.uris = uris
        saveImagesOption = SaveImagesOptions.EMPTY
    }

    fun setCurrentImage(value: Int) {
        Log.d("setCurrentImage", value.toString())
        currentImageIndex.value = value
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

    fun setShowFullscreen(show: Boolean) {
        showFullscreen.value = show
    }


    //////////////////////// Updates and clears ////////////////////////

    fun updateNewUiState(submissionDetails: SubmissionDetails) {
        Log.d("updateNewUiState", submissionDetails.characters.toString())
        newSubmissionDetails = submissionDetails
    }

    fun clearNewUiState() {
        newSubmissionDetails = SubmissionDetails()
    }

    fun updateEditingUiState(submissionDetails: SubmissionDetails) {
        Log.d("updateEditingUiState", editingSubmissionDetails.toString())
        editingSubmissionDetails = submissionDetails
    }

    fun updateSaveImagesOption(imagesOption: SaveImagesOptions) {
        saveImagesOption = imagesOption
    }

    fun clearSelectedSubmissions() {
        Log.d("SubmissionsViewModel", "Deselecting all submissions")
        submissions.value = submissions.value.copy(
            selectedSubmissions = emptyList()
        )
    }


    fun onSelectSubmission(submissionId: Long) {
        val newSelectedSubmissions =
            submissions.value.selectedSubmissions.toMutableList()
        if (newSelectedSubmissions.contains(submissionId)) {
            newSelectedSubmissions.remove(submissionId)
        } else {
            newSelectedSubmissions.add(submissionId)
        }

        submissions.value = submissions.value.copy(
            selectedSubmissions = newSelectedSubmissions
        )
    }

    fun selectAll() {
        Log.d("SubmissionsViewModel", "Selecting all submissions")
        val allSubmissionIds = submissions.value.submissions.map { it.submission.submissionId }
        submissions.value = submissions.value.copy(
            selectedSubmissions = allSubmissionIds
        )
    }

    //////////////////////// Database Operations ////////////////////////
    /**
     * Gets the submission of the id.
     */
    fun getSubmissionWithArtist(id: Long) {
        viewModelScope.launch {
            currentImageIndex.value = 0
            val submission = submissionRepo.getSubmissionWithArtist(id)
            if (submission == null) {
                Log.d("SubmissionsViewModel", "Submission with id $id not found")
                return@launch
            }
            currentSubmissionDetails = submission.toSubmissionDetails()
            uris = submission.images.map { it.uri }
        }
    }

    /**
     * Updates the data of current submission
     */
    suspend fun editSubmission() {
        val submission = editingSubmissionDetails.toSubmissionWithArtist()
        Log.d("editSubmission", submission.toString())
        submissionRepo.updateSubmissionWithArtist(submission)
        editingSubmissionDetails = SubmissionDetails()
        currentSubmissionDetails = submission.toSubmissionDetails()
    }

    /**
     * Saves a new submission
     */
    suspend fun saveSubmission(context: Context) {
        Log.d("saveSubmission", "Started saving submission")
        isLoading.value = true
        try {
            withContext(Dispatchers.IO) {
                if (saveImagesOption == SaveImagesOptions.SINGLE_SUBMISSION) {
                    val imagePaths = uris.map { uri ->
                        saveImageToInternalStorage(context, uri)
                    }

                    val thumbnail = saveThumbnail(context, imagePaths[0])

                    val submissionId = submissionRepo.insertSubmissionDetails(
                        newSubmissionDetails.copy(thumbnail = thumbnail)
                    )

                    imagePaths.forEach { imagePath ->
                        val imageInfo = getImageInfo(context, imagePath)
                        val palette = getPaletteFromUri(context, imagePath)

                        imageRepository.insert(
                            Image(
                                date = Date(),
                                size = imageInfo?.sizeInBytes ?: 0L,
                                uri = imagePath,
                                dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                                extension = imageInfo?.extension ?: "",
                                palette = palette,
                                submissionId = submissionId
                            )
                        )
                    }
                } else {
                    uris.forEach { uri ->
                        val imagePath = saveImageToInternalStorage(context, uri)
                        val thumbnailPath = saveThumbnail(context, uri)

                        val imageInfo = getImageInfo(context, imagePath)
                        val palette = getPaletteFromUri(context, imagePath)
                        val newSub = newSubmissionDetails.copy(thumbnail = thumbnailPath)
                        val submissionId = submissionRepo.insertSubmissionDetails(newSub)

                        imageRepository.insert(
                            Image(
                                imageId = 0,
                                date = Date(),
                                uri = imagePath,
                                size = imageInfo?.sizeInBytes ?: 0L,
                                dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                                extension = imageInfo?.extension ?: "",
                                palette = palette,
                                submissionId = submissionId
                            )
                        )
                    }
                }
            }
        } finally {
            Log.d("saveSubmission", "Finished saving submission")
            isLoading.value = false
        }
    }

    fun deleteSubmission(context: Context, submission: SubmissionWithArtist) {
        viewModelScope.launch {
            submissionRepo.deleteSubmission(context, submission)
        }
    }

    fun deleteSelectedSubmissions(context: Context) {
        viewModelScope.launch {
            val selectedSubmissions = submissions.value.submissions.filter {
                submissions.value.selectedSubmissions.contains(it.submission.submissionId)
            }
            submissionRepo.deleteSubmissions(context, selectedSubmissions)
        }
    }


}


