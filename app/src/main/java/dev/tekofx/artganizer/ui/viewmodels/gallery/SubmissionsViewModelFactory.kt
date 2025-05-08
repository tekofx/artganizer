package dev.tekofx.artganizer.ui.viewmodels.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tekofx.artganizer.repository.SubmissionRepository

@Suppress("UNCHECKED_CAST")
class SubmissionsViewModelFactory(
    private val repository: SubmissionRepository,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionsViewModel", "create called")
        return if (modelClass.isAssignableFrom(SubmissionsViewModel::class.java)) {
            SubmissionsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}