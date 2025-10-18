package dev.tekofx.artganizer.utils

expect interface ImageStorage {
    suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit>
    suspend fun deleteImage(name: String): Result<Unit>
    suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String?
    suspend fun getImagePath(name: String): String?
}