package dev.tekofx.artganizer.navigation

import dev.tekofx.artganizer.utils.FIRST_ROUTE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationManager {
    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.Idle)
    val navigationState = _navigationState.asStateFlow()

    private val _canNavigateBack = MutableStateFlow(false)
    val canNavigateBack = _canNavigateBack.asStateFlow()


    private val _routesList = MutableStateFlow(mutableListOf<AppRoute>(FIRST_ROUTE))
    val routesList = _routesList.asStateFlow()


    fun navigateTo(destination: AppRoute) {
        _navigationState.value = NavigationState.Navigate(destination)
        if (_routesList.value.last() != destination) {
            _routesList.value = _routesList.value.toMutableList().apply { add(destination) }
        }
    }

    fun navigateBack() {
        println("NavigationManager: navigateBack() called")
        _navigationState.value = NavigationState.NavigateBack
        _routesList.value.removeLast()
    }

    fun clearNavigation() {
        _navigationState.value = NavigationState.Idle
    }

    fun updateBackStackState(canGoBack: Boolean) {
        _canNavigateBack.value = canGoBack
    }

}

sealed class NavigationState {
    data object Idle : NavigationState()
    data class Navigate(val destination: AppRoute) : NavigationState()
    data object NavigateBack : NavigationState()
}