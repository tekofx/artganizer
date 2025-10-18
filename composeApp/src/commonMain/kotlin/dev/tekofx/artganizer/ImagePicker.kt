package dev.tekofx.artganizer

import androidx.compose.runtime.Composable

expect fun rememberImagePicker(onImagePicked: (String) -> Unit): ImagePicker

interface ImagePicker {
    @Composable
    fun Content()
    fun launch()
}