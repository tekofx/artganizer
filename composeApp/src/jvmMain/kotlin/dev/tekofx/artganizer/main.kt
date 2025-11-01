package dev.tekofx.artganizer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.FileKit


fun main() {
    FileKit.init(appId = "Artganizer")
    application {
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
}
