package dev.tekofx.artganizer.navigation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val navigationManager: NavigationManager
) : ViewModel() {

    val navigationState = navigationManager.navigationState

    fun navigateTo(destination: String) {
        viewModelScope.launch {
            navigationManager.navigateTo(destination)
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            navigationManager.navigateBack()
        }
    }

    fun clearNavigation() {
        viewModelScope.launch {
            navigationManager.clearNavigation()
        }
    }

    fun updateBackStackState(canGoBack: Boolean) {
        viewModelScope.launch {
            navigationManager.updateBackStackState(canGoBack)
        }
    }
}