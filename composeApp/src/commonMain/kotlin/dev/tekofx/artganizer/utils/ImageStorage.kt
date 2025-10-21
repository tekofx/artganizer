package dev.tekofx.artganizer.utils

import dev.tekofx.artganizer.entities.ImageInfo

expect interface ImageStorage {
    suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit>
    suspend fun deleteImage(name: String): Result<Unit>
    suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String?
    suspend fun getImagePath(name: String): String?
    suspend fun getImageInfo(bytes: ByteArray): ImageInfo?
}