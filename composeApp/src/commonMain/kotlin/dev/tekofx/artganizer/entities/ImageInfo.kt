package dev.tekofx.artganizer.entities

data class ImageInfo(
    val sizeInBytes: Long,
    val extension: String,
    val dimensions: Pair<Int, Int>
)