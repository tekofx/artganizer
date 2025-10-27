package dev.tekofx.artganizer.utils

import androidx.compose.ui.graphics.toComposeImageBitmap
import com.kmpalette.palette.graphics.Palette
import dev.tekofx.artganizer.entities.ImageInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO


@Suppress("ACTUAL_WITHOUT_EXPECT")
actual class DesktopImageStorage : ImageStorage {
    private val storageDir = File(System.getProperty("user.home"), ".yourapp/images")
        .apply { if (!exists()) mkdirs() }

    override suspend fun saveImage(bytes: ByteArray, name: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                File(storageDir, name).writeBytes(bytes)
            }
        }

    override suspend fun deleteImage(name: String): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                val file = File(storageDir, name)
                if (file.exists()) file.delete() else throw FileNotFoundException(name) as Throwable
            }
        }.map { }

    override suspend fun saveThumbnail(bytes: ByteArray, outputName: String): String? =
        withContext(Dispatchers.IO) {
            val file = File(storageDir, outputName)
            file.writeBytes(bytes)
            file.absolutePath
        }

    override suspend fun getImagePath(name: String): String? =
        withContext(Dispatchers.IO) {
            File(storageDir, name).takeIf { it.exists() }?.absolutePath
        }

    override suspend fun getImageInfo(path: String): ImageInfo? {
        val file = Paths.get(path).toFile()
        val fileSize = Files.size(file.toPath())
        val image: BufferedImage = ImageIO.read(file)
        val mimeType = Files.probeContentType(file.toPath()) ?: "image/unknown"

        return ImageInfo(
            dimensions = Pair(image.width, image.height),
            sizeInBytes = fileSize,
            extension = mimeType
        )
    }

    override suspend fun getColorpalette(path: String): List<Int> {

        val file = Paths.get(path).toFile()
        val fileSize = Files.size(file.toPath())
        val image: BufferedImage = ImageIO.read(file)

        val palette = Palette.from(image.toComposeImageBitmap()).generate()
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