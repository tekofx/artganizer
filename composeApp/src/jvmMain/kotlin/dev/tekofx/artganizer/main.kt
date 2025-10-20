package dev.tekofx.artganizer

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.vinceglb.filekit.FileKit
import org.koin.core.context.startKoin


fun main() {
    FileKit.init(appId = "MyApplication")
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
