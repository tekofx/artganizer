package dev.tekofx.artganizer.utils

import coil3.annotation.InternalCoilApi
import coil3.util.MimeTypeMap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

actual class ImageManager {
    actual fun shareImage(image: String, text: String?) {


    }

    actual fun saveImageToInternalStorage(image: String): String {
        TODO("Not yet implemented")

    }

    actual fun saveThumbnail(image: String): String {
        TODO("Not yet implemented")
    }

    actual suspend fun removeImageFromInternalStorage(image: String) {
        withContext(
            Dispatchers.IO
        ) {
            val file = File(image)
            if (file.exists()) {
                file.delete()
            }
        }
    }

    @OptIn(InternalCoilApi::class)
    actual fun getImageInfo(image: String): ImageInfo? {
        val mimeType = MimeTypeMap.getMimeTypeFromExtension(image)
        val fileType = mimeType?.split("/")?.lastOrNull()?.uppercase() ?: "unknown"

        //TODO: Get Width and Height
        val dimensions = Pair(100, 100)

        // Get size in bytes
        val file = File(image)
        if (!file.exists()) return null
        val sizeInBytes = file.length()


        return ImageInfo(
            sizeInBytes = sizeInBytes,
            extension = fileType,
            dimensions = dimensions
        )
    }
}