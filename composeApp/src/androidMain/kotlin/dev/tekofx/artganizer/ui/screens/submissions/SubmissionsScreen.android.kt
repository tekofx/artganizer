package dev.tekofx.artganizer.ui.screens.submissions

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.filter_outlined
import dev.tekofx.artganizer.navigation.AppRoute
import dev.tekofx.artganizer.navigation.NavigationViewModel
import dev.tekofx.artganizer.ui.layout.BottomAppBarScaffold
import org.koin.compose.viewmodel.koinViewModel

@Composable
actual fun SubmissionsScreen() {
    val navigationViewModel = koinViewModel<NavigationViewModel>()
    BottomAppBarScaffold(
        textFieldState = rememberTextFieldState(),
        onFocusChanged = {},
        onAddButtonClick = { navigationViewModel.navigateTo(AppRoute.SubmissionCreation) },
        firstButtonIcon = Res.drawable.filter_outlined,
    ) {
        Text("Submissions Screen - Android")
    }
}