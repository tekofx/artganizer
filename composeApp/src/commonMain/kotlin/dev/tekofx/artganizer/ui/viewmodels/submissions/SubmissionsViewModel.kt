package dev.tekofx.artganizer.ui.viewmodels.submissions


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kmpalette.palette.graphics.Palette
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.managers.UiStateManager
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.utils.AppLogger
import dev.tekofx.artganizer.utils.saveSubmissionFromPlatformFile
import dev.tekofx.artganizer.utils.saveThumbnailFromPlatformFile
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitMode
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.util.toImageBitmap
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.path
import io.github.vinceglb.filekit.size
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
    private val imageRepository: ImageRepository,
    private val uiStateManager: UiStateManager
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

    // New images to be added as submissions
    var newFiles = uiStateManager.files


    // Data
    val submissions = MutableStateFlow(SubmissionsUiState())


    // Ui State
    val showDeletePopup = MutableStateFlow(false)
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

    fun setNewFiles() = viewModelScope.launch {
        val files = FileKit.openFilePicker(
            mode = FileKitMode.Multiple(),
            type = FileKitType.Image
        )

        files?.let {
            newFiles.value = files
        }
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

    fun setShowDeletePopup(show: Boolean) {
        showDeletePopup.value = show
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

    fun clearNewFiles() {
        newFiles.value = emptyList<PlatformFile>()
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
            //uris = submission.images.map { it.uri }
        }
    }

    /**
     * Updates the data of current submission
     */
    fun editSubmission() = viewModelScope.launch {
        val submission = editingSubmissionDetails.toSubmissionWithArtist()
        submissionRepo.updateSubmissionWithArtist(submission)
        editingSubmissionDetails = SubmissionDetails()
        currentSubmissionDetails = submission.toSubmissionDetails()
    }

    /**
     * Saves a new submission
     */
    fun saveSubmission() = viewModelScope.launch {
        isLoading.value = true
        var i = 0f
        val total = newFiles.value.size.toFloat()
        try {
            withContext(Dispatchers.IO) {
                if (saveImagesOption == SaveImagesOptions.SINGLE_SUBMISSION) {
                    // Save thumbnail
                    val thumbnail = saveThumbnailFromPlatformFile(newFiles.value[0])

                    // Save images
                    val savedImages = newFiles.value.map { file ->
                        saveSubmissionFromPlatformFile(file)
                    }

                    val submissionId = submissionRepo.insertSubmissionDetails(
                        newSubmissionDetails.copy(thumbnail = thumbnail.path)
                    )
                    savedImages.forEach { savedImage ->
                        AppLogger.d("SubmissionsViewModel", savedImage.path)

                        val palette = getColorPalette(savedImage)

                        imageRepository.insert(
                            Image(
                                date = Date(),
                                uri = savedImage.path,
                                size = savedImage.size(),
                                dimensions = "${savedImage.toImageBitmap().width}x${savedImage.toImageBitmap().height}",
                                extension = savedImage.extension,
                                palette = palette,
                                submissionId = submissionId
                            )
                        )
                        i++
                        savingProgress.value =
                            i / total
                    }
                } else {
                    newFiles.value.forEach { file ->
                        AppLogger.d("SubmissionsViewModel", file.path)
                        // Save thumbnail
                        val thumbnail = saveThumbnailFromPlatformFile(file)

                        // Save image
                        val savedImage = saveSubmissionFromPlatformFile(file)
                        val newSub = newSubmissionDetails.copy(thumbnail = thumbnail.path)
                        val submissionId = submissionRepo.insertSubmissionDetails(newSub)

                        // Get palette
                        val palette = getColorPalette(file)

                        imageRepository.insert(
                            Image(
                                imageId = 0,
                                date = Date(),
                                uri = savedImage.path,
                                size = savedImage.size(),
                                dimensions = "${savedImage.toImageBitmap().width}x${savedImage.toImageBitmap().height}",
                                extension = savedImage.extension,
                                palette = palette,
                                submissionId = submissionId
                            )
                        )
                        savingProgress.value =
                            (newFiles.value.indexOf(file) + 1).toFloat() / total
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
            submissionRepo.deleteSubmission(submission)
        }
    }

    fun deleteSelectedSubmissions() {
        viewModelScope.launch {
            val selectedSubmissions = submissions.value.submissions.filter {
                submissions.value.selectedSubmissions.contains(it.submission.submissionId)
            }
            submissionRepo.deleteSubmissions(selectedSubmissions)
            clearSelectedSubmissions()
        }
    }


    private suspend fun getColorPalette(image: PlatformFile): List<Int> {
        val palette = Palette.from(image.toImageBitmap()).generate()
        val colors = mutableListOf<Int>()
        palette.vibrantSwatch?.rgb?.let { colors.add(it) }
        palette.mutedSwatch?.rgb?.let { colors.add(it) }
        palette.dominantSwatch?.rgb?.let { colors.add(it) }
        palette.lightVibrantSwatch?.rgb?.let { colors.add(it) }
        palette.lightMutedSwatch?.rgb?.let { colors.add(it) }
        palette.darkVibrantSwatch?.rgb?.let { colors.add(it) }
        palette.darkMutedSwatch?.rgb?.let { colors.add(it) }

        return colors
    }


}


