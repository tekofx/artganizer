package dev.tekofx.artganizer.ui.viewmodels.gallery


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tekofx.artganizer.repository.GalleryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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

    fun getSubmissions() {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val submissions = repository.getImages()
                _submissions.value = submissions
            } catch (e: Exception) {
                errorMessage.postValue("Error getting images: ${e.message}")
            } finally {
                isLoading.value = false
            }
        }
    }


}

