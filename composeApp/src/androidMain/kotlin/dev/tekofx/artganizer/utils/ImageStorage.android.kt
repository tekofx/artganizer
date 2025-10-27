package dev.tekofx.artganizer.utils

// androidMain/kotlin/ImageStorage.android.kt
import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.net.toUri
import com.kmpalette.palette.graphics.Palette
import dev.tekofx.artganizer.entities.ImageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException


@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class AndroidImageStorage(private val context: Context) : ImageStorage {

    private val imagesDir: File
        get() = File(context.filesDir, "images").apply { if (!exists()) mkdirs() }

    override suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                File(imagesDir, name).writeBytes(bytes)
            }
        }

    override suspend fun deleteImage(name: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val file = File(imagesDir, name)
                if (file.exists()) file.delete() else throw FileNotFoundException(name) as Throwable
            }
        }.map { } // Convert success value (Boolean) to Unit

    override suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String? =
        withContext(Dispatchers.IO) {
            val file = File(imagesDir, outputName)
            file.writeBytes(bytes)
            file.absolutePath
        }

    override suspend fun getImagePath(name: String): String? =
        withContext(Dispatchers.IO) {
            File(imagesDir, name).takeIf { it.exists() }?.absolutePath
        }

    override suspend fun getImageInfo(path: String): ImageInfo? {
        val contentResolver = context.contentResolver
        val uri = path.toUri()
        val stream = contentResolver.openInputStream(uri)
        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        BitmapFactory.decodeStream(stream, null, options)
        stream?.close()

        val mimeType = contentResolver.getType(uri) ?: "image/unknown"
        val fileSize = try {
            contentResolver.openAssetFileDescriptor(uri, "r")?.length ?: -1
        } catch (e: Exception) {
            -1
        }
        return ImageInfo(
            dimensions = Pair(options.outWidth, options.outHeight),
            sizeInBytes = fileSize,
            extension = mimeType
        )
    }

    override suspend fun getColorpalette(path: String): List<Int> {
        val inputStream =
            context.contentResolver.openInputStream(path.toUri()) ?: return emptyList()
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        val palette = Palette.from(bitmap.asImageBitmap()).generate()
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
}