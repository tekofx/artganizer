package dev.tekofx.artganizer.ui.viewmodels.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tekofx.artganizer.repository.TagRepository

@Suppress("UNCHECKED_CAST")
class TagViewModelFactory(
    private val repository: TagRepository,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TagsViewModel::class.java)) {
            TagsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}