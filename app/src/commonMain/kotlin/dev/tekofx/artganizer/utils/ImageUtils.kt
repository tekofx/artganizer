package dev.tekofx.artganizer.utils

import com.kmpalette.loader.PathLoader
import com.kmpalette.palette.graphics.Palette
import okio.Path.Companion.toPath
import java.util.Locale


enum class MimeType {
    PDF,
    TEXT,
    IMAGE,
}


expect class ImageManager {
    fun shareImage(image: String, text: String? = null)
    fun saveImageToInternalStorage(image: String): String
    fun saveThumbnail(image: String): String
    suspend fun removeImageFromInternalStorage(image: String)
    fun getImageInfo(image: String): ImageInfo?
}


suspend fun getPaletteFromUri(uri: String): List<Int> {

    val palette = Palette.from(PathLoader.load(uri.toPath())).generate()
    val colors = mutableListOf<Int>()

    palette.vibrantSwatch?.rgb?.let { colors.add(it) }
    palette.mutedSwatch?.rgb?.let { colors.add(it) }
    palette.dominantSwatch?.rgb?.let { colors.add(it) }
    palette.lightVibrantSwatch?.rgb?.let { colors.add(it) }
    palette.lightMutedSwatch?.rgb?.let { colors.add(it) }
    palette.darkVibrantSwatch?.rgb?.let { colors.add(it) }
    palette.darkMutedSwatch?.rgb?.let { colors.add(it) }

    return colors
}


fun formatFileSize(sizeInBytes: Long): String {
    return when {
        sizeInBytes >= 1024 * 1024 -> {
            val sizeInMb = sizeInBytes.toDouble() / (1024 * 1024)
            String.format(Locale.getDefault(), "%.2f MB", sizeInMb)
        }

        sizeInBytes >= 1024 -> {
            val sizeInKb = sizeInBytes.toDouble() / 1024
            String.format(Locale.getDefault(), "%.2f KB", sizeInKb)
        }

        else -> "$sizeInBytes Bytes"
    }
}


data class ImageInfo(
    val sizeInBytes: Long,
    val extension: String,
    val dimensions: Pair<Int, Int>
)
