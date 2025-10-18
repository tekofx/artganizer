package dev.tekofx.artganizer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin


fun main() = application {
    initKoin {
        modules()

    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "Artganizer",
    ) {
        App()
    }
}