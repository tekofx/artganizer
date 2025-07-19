package dev.tekofx.artganizer.ui.viewmodels.submissions


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.utils.ImageManager
import dev.tekofx.artganizer.utils.getPaletteFromUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class SaveImagesOptions {
    EMPTY,
    SINGLE_SUBMISSION,
    MULTIPLE_SUBMISSIONS
}

class SubmissionsViewModel(
    private val submissionRepo: SubmissionRepository,
    private val imageRepository: ImageRepository,
    private val imageManager: ImageManager
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
    var uris = listOf<String>()
        private set

    // Data
    val submissions = MutableStateFlow(SubmissionsUiState())


    // Ui State
    val showPopup = MutableStateFlow(false)
    val showEditSubmission = MutableStateFlow(false)
    val currentImageIndex = MutableStateFlow(0) // Index of images in current submission
    val isLoading = MutableStateFlow(false)
    val isSelecting = MutableStateFlow(false)
    val savingProgress = MutableStateFlow(0f)

    init {
        viewModelScope.launch {
            submissionRepo.getAllSubmissions().collect { submissionsList ->
                submissions.value = submissionsList.toSubmissionsUiState()
                currentImageIndex.value = 0
            }
        }
    }


    //////////////////////// Setters ////////////////////////

    fun setUris(uris: List<String>) {
        this.uris = uris
        saveImagesOption = SaveImagesOptions.EMPTY
    }

    fun setCurrentImage(value: Int) {
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


    //////////////////////// Updates and clears ////////////////////////

    fun updateNewUiState(submissionDetails: SubmissionDetails) {
        newSubmissionDetails = submissionDetails
    }

    fun clearNewUiState() {
        newSubmissionDetails = SubmissionDetails()
    }

    fun updateEditingUiState(submissionDetails: SubmissionDetails) {
        editingSubmissionDetails = submissionDetails
    }

    fun updateSaveImagesOption(imagesOption: SaveImagesOptions) {
        saveImagesOption = imagesOption
    }

    fun clearSelectedSubmissions() {
        isSelecting.value = false
        submissions.value = submissions.value.copy(
            selectedSubmissions = emptyList()
        )
    }

    fun onSelectSubmission(submissionId: Long) {

        // Show the selection mode if not already selecting
        isSelecting.value = true

        val newSelectedSubmissions =
            submissions.value.selectedSubmissions.toMutableList()


        // Toggle the selection of the submission
        if (newSelectedSubmissions.contains(submissionId)) {
            newSelectedSubmissions.remove(submissionId)
        } else {
            newSelectedSubmissions.add(submissionId)
        }

        // Update the state with the new selection
        submissions.value = submissions.value.copy(
            selectedSubmissions = newSelectedSubmissions
        )

        // If no submissions are selected, exit selection mode
        if (newSelectedSubmissions.isEmpty()) {
            isSelecting.value = false
        }
    }

    fun selectAll() {
        isSelecting.value = true
        val allSubmissionIds = submissions.value.submissions.map { it.submission.submissionId }
        submissions.value = submissions.value.copy(
            selectedSubmissions = allSubmissionIds
        )
    }

    fun deselectAll() {
        isSelecting.value = false
        submissions.value = submissions.value.copy(
            selectedSubmissions = emptyList()
        )
    }

    fun shareSubmission(submission: SubmissionWithArtist) {
        viewModelScope.launch {
            imageManager.shareImage(submission.submission.thumbnail)
        }
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
        submissionRepo.updateSubmissionWithArtist(submission)
        editingSubmissionDetails = SubmissionDetails()
        currentSubmissionDetails = submission.toSubmissionDetails()
    }

    /**
     * Saves a new submission
     */
    suspend fun saveSubmission() {
        isLoading.value = true
        var i = 0f
        val total = uris.size.toFloat()
        try {
            withContext(Dispatchers.IO) {
                if (saveImagesOption == SaveImagesOptions.SINGLE_SUBMISSION) {

                    val imagePaths = uris.map { uri ->
                        imageManager.saveImageToInternalStorage(uri)
                    }

                    val thumbnail = imageManager.saveThumbnail(imagePaths[0])
                    val submissionId = submissionRepo.insertSubmissionDetails(
                        newSubmissionDetails.copy(thumbnail = thumbnail)
                    )
                    imagePaths.forEach { imagePath ->

                        val imageInfo = imageManager.getImageInfo(imagePath)
                        val palette = getPaletteFromUri(imagePath)

                        imageRepository.insert(
                            Image(
                                size = imageInfo?.sizeInBytes ?: 0L,
                                uri = imagePath,
                                dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                                extension = imageInfo?.extension ?: "",
                                palette = palette,
                                submissionId = submissionId
                            )
                        )
                        i++
                        savingProgress.value =
                            i / total
                    }
                } else {
                    uris.forEach { uri ->
                        val imagePath = imageManager.saveImageToInternalStorage(uri)
                        val thumbnailPath = imageManager.saveThumbnail(uri)

                        val imageInfo = imageManager.getImageInfo(imagePath)
                        val palette = getPaletteFromUri(imagePath)
                        val newSub = newSubmissionDetails.copy(thumbnail = thumbnailPath)
                        val submissionId = submissionRepo.insertSubmissionDetails(newSub)

                        imageRepository.insert(
                            Image(
                                imageId = 0,
                                uri = imagePath,
                                size = imageInfo?.sizeInBytes ?: 0L,
                                dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                                extension = imageInfo?.extension ?: "",
                                palette = palette,
                                submissionId = submissionId
                            )
                        )
                        savingProgress.value =
                            (uris.indexOf(uri) + 1).toFloat() / total
                    }
                }
            }
        } finally {
            isLoading.value = false
            savingProgress.value = 0f
        }
    }

    fun deleteSubmission(submission: SubmissionWithArtist) {
        viewModelScope.launch {
            submissionRepo.deleteSubmission(imageManager, submission)
        }
    }

    fun deleteSelectedSubmissions() {
        viewModelScope.launch {
            val selectedSubmissions = submissions.value.submissions.filter {
                submissions.value.selectedSubmissions.contains(it.submission.submissionId)
            }
            submissionRepo.deleteSubmissions(imageManager, selectedSubmissions)
            clearSelectedSubmissions()
        }
    }


}


