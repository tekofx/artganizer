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
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository
import dev.tekofx.artganizer.utils.getImageInfo
import dev.tekofx.artganizer.utils.getPaletteFromUri
import dev.tekofx.artganizer.utils.removeImagesFromInternalStorage
import dev.tekofx.artganizer.utils.saveImageToInternalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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

    var newSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    var currentSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    val currentImage = MutableStateFlow(0)

    var editingSubmissionDetails by mutableStateOf(SubmissionDetails())
        private set

    var saveImagesOption by mutableStateOf(SaveImagesOptions.EMPTY)
        private set

    var uris = listOf<Uri>()
        private set

    // Data
    val submissions = MutableStateFlow<List<SubmissionWithArtist>>(emptyList())

    // Ui State
    val showPopup = MutableStateFlow(false)
    val showEditSubmission = MutableStateFlow(false)

    fun setUris(uris: List<Uri>) {
        this.uris = uris
    }

    fun setCurrentImage(value: Int) {
        Log.d("setCurrentImage", value.toString())
        currentImage.value = value
    }


    fun getSubmissionWithArtist(id: Int) {
        viewModelScope.launch {
            val submission = submissionRepo.getSubmissionWithArtist(id)
            if (submission == null) {
                Log.d("SubmissionsViewModel", "Submission with id $id not found")
                return@launch
            }
            Log.d("GetSubmissionWithArtist", submission.toString())
            currentSubmissionDetails = submission.toSubmissionDetails()
            uris = submission.images.map { it.uri }
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

    fun deleteSubmission(context: Context, submission: SubmissionWithArtist) {
        viewModelScope.launch {
            submissionRepo.deleteSubmission(submission.submission)
            removeImagesFromInternalStorage(context, submission.images.map { it.uri })
        }
    }

    suspend fun saveSubmission(context: Context) {

        if (saveImagesOption == SaveImagesOptions.SINGLE_SUBMISSION) {
            var imagePaths = mutableListOf<Uri>()
            uris.forEach { uri ->
                val imagePath =
                    saveImageToInternalStorage(
                        context,
                        uri
                    )
                imagePaths.add(imagePath)
            }


            val submission = submissionRepo.insertSubmission(
                Submission(
                    id = newSubmissionDetails.id,
                    title = newSubmissionDetails.title,
                    description = newSubmissionDetails.description,
                    imagePath = imagePaths[0],
                    rating = newSubmissionDetails.rating,
                    artistId = newSubmissionDetails.artistId
                )
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
                        submissionId = submission.toInt()
                    )
                )
            }
        } else {
            uris.forEach { uri ->
                val imagePath =
                    saveImageToInternalStorage(
                        context,
                        uri
                    )

                val imageInfo = getImageInfo(context, imagePath)
                val palette = getPaletteFromUri(context, imagePath)
                val submission = submissionRepo.insertSubmission(

                    Submission(
                        id = newSubmissionDetails.id,
                        title = newSubmissionDetails.title,
                        description = newSubmissionDetails.description,
                        imagePath = imagePath,
                        rating = newSubmissionDetails.rating,
                        artistId = newSubmissionDetails.artistId
                    )
                )
                Log.d("saveSubmission", submission.toString())

                imageRepository.insert(
                    Image(
                        id = 0,
                        date = Date(),
                        uri = imagePath,
                        size = imageInfo?.sizeInBytes ?: 0L,
                        dimensions = "${imageInfo?.dimensions?.first}x${imageInfo?.dimensions?.second}",
                        extension = imageInfo?.extension ?: "",
                        palette = palette,
                        submissionId = submission.toInt()
                    )
                )
            }
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
        submissionRepo.updateSubmissionWithArtist(submission.submission)
        editingSubmissionDetails = SubmissionDetails()
        currentSubmissionDetails = submission.toSubmissionDetails()
    }


    init {
        // Collect the flow and update _submissions
        viewModelScope.launch {
            submissionRepo.getAllSubmissions().collect { submissionsList ->
                Log.d("SubmissionsViewModel", submissionsList.toString())
                submissions.value = submissionsList
            }
        }
    }


}


