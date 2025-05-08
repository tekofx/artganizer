package dev.tekofx.artganizer.ui.viewmodels.gallery


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.tekofx.artganizer.repository.GalleryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class GalleryViewModel(private val repository: GalleryRepository) : ViewModel() {

    // Data
    private val _submissions = MutableStateFlow<List<String>>(emptyList())
    val submissions = _submissions.asStateFlow()

    // Loaders
    val isLoading = MutableStateFlow(false)


    // Inputs
    private val _queryText = MutableStateFlow("")
    val queryText = _queryText.asStateFlow()
    private val _filtersApplied = MutableStateFlow(false)
    var filtersApplied = _filtersApplied.asStateFlow()

    // Error
    val errorMessage = MutableLiveData<String>()


}

