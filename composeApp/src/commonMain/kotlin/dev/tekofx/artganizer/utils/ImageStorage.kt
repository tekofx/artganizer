package dev.tekofx.artganizer.utils

import dev.tekofx.artganizer.entities.ImageInfo

interface ImageStorage {
    suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit>
    suspend fun deleteImage(name: String): Result<Unit>
    suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String?
    suspend fun getImagePath(name: String): String?
    suspend fun getImageInfo(path: String): ImageInfo?
    suspend fun getColorpalette(path: String): List<Int>
}