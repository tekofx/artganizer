package dev.tekofx.artganizer.utils

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
import androidx.core.graphics.scale
import androidx.palette.graphics.Palette
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.util.Locale
import java.util.UUID

fun saveImageToInternalStorage(context: Context, uri: Uri): Uri {
    val mimeType = context.contentResolver.getType(uri)
    val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"
    val uniqueFilename = "${UUID.randomUUID()}.$extension"
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = context.filesDir.resolve(uniqueFilename)
    inputStream?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return Uri.fromFile(file)
}

fun saveThumbnail(context: Context, uri: Uri): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)
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

    return Uri.fromFile(file)
}

fun getPaletteFromUri(context: Context, uri: Uri): List<Int> {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return emptyList()
    val bitmap = BitmapFactory.decodeStream(inputStream)
    inputStream.close()

    val palette = Palette.from(bitmap).generate()
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

fun removeImageFromInternalStorage(context: Context, uri: Uri) {
    val file = context.filesDir.resolve(uri.lastPathSegment ?: return)
    if (file.exists()) {
        file.delete()
    }
}

fun removeImagesFromInternalStorage(context: Context, uriStrings: List<Uri>) {
    uriStrings.forEach { uri ->
        removeImageFromInternalStorage(context, uri)
    }
}


fun getPathFromUri(context: Context, uri: Uri): String? {
    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    return cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        it.moveToFirst()
        val fileName = it.getString(nameIndex)
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = context.filesDir.resolve(fileName)
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        file.absolutePath
    }
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

fun getImageInfo(context: Context, uri: Uri): ImageInfo? {
    val contentResolver = context.contentResolver

    try {
        // Get size in MB
        val fileDescriptor = contentResolver.openFileDescriptor(uri, "r") ?: return null
        val fileSizeInBytes = fileDescriptor.statSize

        // Get extension
        val extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString())
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        val fileType = mimeType?.split("/")?.lastOrNull()?.uppercase() ?: "unknown"

        // Get dimensions
        val inputStream = contentResolver.openInputStream(uri) ?: return null
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

data class ImageInfo(
    val sizeInBytes: Long,
    val extension: String,
    val dimensions: Pair<Int, Int>
)
