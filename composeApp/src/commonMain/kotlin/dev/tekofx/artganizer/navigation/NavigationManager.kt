package dev.tekofx.artganizer.navigation

import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationManager {
    private val _navigationState = MutableStateFlow<NavigationState>(NavigationState.Idle)
    val navigationState = _navigationState.asStateFlow()

    private val _canNavigateBack = MutableStateFlow(false)
    val canNavigateBack = _canNavigateBack.asStateFlow()

    private val _currentRoute = MutableStateFlow<NavBackStackEntry?>(null)
    val currentRoute = _currentRoute.asStateFlow()


    fun navigateTo(destination: AppRoute) {
        _navigationState.value = NavigationState.Navigate(destination)
    }

    fun navigateBack() {
        println("NavigationManager: navigateBack() called")
        _navigationState.value = NavigationState.NavigateBack
    }

    fun clearNavigation() {
        _navigationState.value = NavigationState.Idle
    }

    fun updateBackStackState(canGoBack: Boolean) {
        _canNavigateBack.value = canGoBack
    }

    fun updateCurrentRoute(route: NavBackStackEntry?) {
        _currentRoute.value = route
    }
}

sealed class NavigationState {
    data object Idle : NavigationState()
    data class Navigate(val destination: AppRoute) : NavigationState()
    data object NavigateBack : NavigationState()
}