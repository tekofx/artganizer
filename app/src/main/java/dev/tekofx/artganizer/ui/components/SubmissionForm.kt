package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionDetails
import dev.tekofx.artganizer.ui.viewmodels.gallery.SubmissionUiState

@Composable
fun SubmissionsForm(
    submissionUiState: SubmissionUiState,
    onItemValueChange: (SubmissionDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        // ...
    ) {
        SubmissionFormFields(
            submissionDetails = submissionUiState.submissionDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = submissionUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Save")
        }
    }
}

@Composable
fun SubmissionFormFields(
    submissionDetails: SubmissionDetails,
    modifier: Modifier = Modifier,
    onValueChange: (SubmissionDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = submissionDetails.title,
            onValueChange = { onValueChange(submissionDetails.copy(title = it)) },
            label = { Text("Title") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = submissionDetails.description,
            onValueChange = { onValueChange(submissionDetails.copy(description = it)) },
            label = { Text("Description") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = submissionDetails.imagePath,
            onValueChange = { onValueChange(submissionDetails.copy(imagePath = it)) },
            label = { Text("ImagePath") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = "Image Path: ${submissionDetails.imagePath}",
            )
        }
    }
}