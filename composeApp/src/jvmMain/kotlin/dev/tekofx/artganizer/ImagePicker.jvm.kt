package dev.tekofx.artganizer

import androidx.compose.runtime.Composable
import java.io.File
import javax.swing.JFileChooser

actual fun rememberImagePicker(onImagePicked: (String) -> Unit): ImagePicker {
    return DesktopImagePicker(onImagePicked)
}

private class DesktopImagePicker(private val onImagePicked: (String) -> Unit) : ImagePicker {
    private var showDialog = false

    @Composable
    override fun Content() {
        if (showDialog) {
            javax.swing.SwingUtilities.invokeLater {
                val fileChooser = JFileChooser()
                val result = fileChooser.showOpenDialog(null)
                if (result == JFileChooser.APPROVE_OPTION) {
                    val file: File = fileChooser.selectedFile
                    onImagePicked(file.absolutePath)
                }
                showDialog = false
            }
        }
    }

    override fun launch() {
        showDialog = true
    }
}