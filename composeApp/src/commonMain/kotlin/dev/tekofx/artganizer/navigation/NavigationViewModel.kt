package dev.tekofx.artganizer.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.search
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NavigationViewModel(
    private val navigationManager: NavigationManager
) : ViewModel() {

    val navigationState = navigationManager.navigationState
    val routesList = navigationManager.routesList

    val showBottomAppBar = MutableStateFlow(false)
    val firstButtonIcon = MutableStateFlow(Res.drawable.search)
    val onAddButtonClick = MutableStateFlow(Unit)

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


}