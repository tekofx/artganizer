package dev.tekofx.artganizer.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.graphics.scale
import androidx.core.net.toUri
import coil3.annotation.InternalCoilApi
import coil3.util.MimeTypeMap
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.UUID

actual class ImageManager(private val context: Context) {
    actual fun shareImage(image: String, text: String?) {
        val file = File(image)
        val contentUri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, contentUri)
            putExtra(Intent.EXTRA_TEXT, text)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share Image"))
    }

    @OptIn(InternalCoilApi::class)
    actual fun saveImageToInternalStorage(image: String): String {
        val extension = MimeTypeMap.getMimeTypeFromExtension(image) ?: "jpg"
        val uniqueFilename = "${UUID.randomUUID()}.$extension"
        val inputStream = context.contentResolver.openInputStream(image.toUri())
        val file = context.filesDir.resolve(uniqueFilename)
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }

    actual fun saveThumbnail(image: String): String {
        val inputStream = context.contentResolver.openInputStream(image.toUri())
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        // Calculate the new dimensions while maintaining the aspect ratio
        val aspectRatio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
        val newWidth: Int
        val newHeight: Int

        if (originalBitmap.width > originalBitmap.height) {
            newWidth = THUMBNAIL_SIZE
            newHeight = (THUMBNAIL_SIZE / aspectRatio).toInt()
        } else {
            newHeight = THUMBNAIL_SIZE
            newWidth = (THUMBNAIL_SIZE * aspectRatio).toInt()
        }

        // Resize the bitmap
        val resizedBitmap = originalBitmap.scale(newWidth, newHeight)

        // Save the resized bitmap to internal storage
        val uniqueFilename = "${UUID.randomUUID()}_thumbnail.jpg"
        val file = File(context.filesDir, uniqueFilename)
        FileOutputStream(file).use { outputStream ->
            resizedBitmap.compress(
                Bitmap.CompressFormat.JPEG,
                85,
                outputStream
            ) // Adjust quality as needed
        }

        return file.absolutePath
    }

    actual suspend fun removeImageFromInternalStorage(image: String) {
        val file = context.filesDir.resolve(image)
        if (file.exists()) {
            file.delete()
        }
    }

    @OptIn(InternalCoilApi::class)
    actual fun getImageInfo(image: String): ImageInfo? {
        val contentResolver = context.contentResolver

        try {
            // Get size in MB
            val fileDescriptor =
                contentResolver.openFileDescriptor(image.toUri(), "r") ?: return null
            val fileSizeInBytes = fileDescriptor.statSize

            // Get extension
            val mimeType = MimeTypeMap.getMimeTypeFromExtension(image)
            val fileType = mimeType?.split("/")?.lastOrNull()?.uppercase() ?: "unknown"

            // Get dimensions
            val inputStream = contentResolver.openInputStream(image.toUri()) ?: return null
            val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeStream(inputStream, null, options)
            val dimensions = Pair(options.outWidth, options.outHeight)
            fileDescriptor.close()

            return ImageInfo(fileSizeInBytes, fileType, dimensions)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }
    }
}