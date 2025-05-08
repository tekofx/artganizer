package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun SubmissionCreationScreen() {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Title") },
            placeholder = { Text("Enter title") },
            maxLines = 1,
            singleLine = true,
        )

        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Description") },
            placeholder = { Text("Enter description") },
            maxLines = 1,
            singleLine = false,
        )

    }
}