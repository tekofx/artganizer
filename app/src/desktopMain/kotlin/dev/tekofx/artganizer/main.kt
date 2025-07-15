package dev.tekofx.artganizer


import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Artganizer",
    ) {
        App(sharedText = null)
    }
}