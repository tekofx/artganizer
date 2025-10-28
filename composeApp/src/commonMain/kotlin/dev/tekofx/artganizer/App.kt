package dev.tekofx.artganizer

import androidx.compose.runtime.Composable
import dev.tekofx.artganizer.navigation.Navigation
import dev.tekofx.artganizer.ui.theme.AppTheme
import org.koin.compose.KoinApplication

@Composable
fun App() {
    KoinApplication(
        application = {
            modules()
        }
    ) {
        AppTheme() {
            Navigation()
        }
    }
}
