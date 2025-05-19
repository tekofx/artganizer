package dev.tekofx.artganizer.ui.viewmodels.submissions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tekofx.artganizer.repository.ImageRepository
import dev.tekofx.artganizer.repository.SubmissionRepository

@Suppress("UNCHECKED_CAST")
class SubmissionsViewModelFactory(
    private val submissionRepository: SubmissionRepository,
    private val imageRepository: ImageRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModel", "create called")
        return if (modelClass.isAssignableFrom(SubmissionsViewModel::class.java)) {
            SubmissionsViewModel(this.submissionRepository, this.imageRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}