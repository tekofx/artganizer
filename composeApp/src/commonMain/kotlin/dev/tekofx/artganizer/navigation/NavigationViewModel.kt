package dev.tekofx.artganizer.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val navigationManager: NavigationManager
) : ViewModel() {

    val navigationState = navigationManager.navigationState
    val currentDestination = navigationManager.currentRoute

    fun navigateTo(destination: AppRoute) {
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

    fun updateCurrentRoute(route: NavBackStackEntry?) {
        viewModelScope.launch {
            navigationManager.updateCurrentRoute(route)
        }
    }
}