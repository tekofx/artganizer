package dev.tekofx.artganizer.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.webkit.MimeTypeMap
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


