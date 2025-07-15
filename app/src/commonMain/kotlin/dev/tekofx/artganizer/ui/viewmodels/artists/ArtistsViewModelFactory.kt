package dev.tekofx.artganizer.ui.viewmodels.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.tekofx.artganizer.repository.ArtistRepository

@Suppress("UNCHECKED_CAST")
class ArtistsViewModelFactory(
    private val repository: ArtistRepository,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ArtistsViewModel::class.java)) {
            ArtistsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}