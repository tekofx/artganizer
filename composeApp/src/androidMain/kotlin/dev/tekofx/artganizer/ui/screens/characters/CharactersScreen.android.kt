package dev.tekofx.artganizer.ui.screens.characters

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import dev.tekofx.artganizer.navigation.AppRoute
import dev.tekofx.artganizer.navigation.NavigationViewModel
import dev.tekofx.artganizer.ui.layout.BottomAppBarScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun CharactersScreen() {
    val navigationViewModel = koinViewModel<NavigationViewModel>()
    BottomAppBarScaffold(
        textFieldState = rememberTextFieldState(),
        onFocusChanged = {},
        onAddButtonClick = { navigationViewModel.navigateTo(AppRoute.CharacterCreation) },
    ) {
        Text("Characters Screen - Android")
    }
}
