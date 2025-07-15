package dev.tekofx.artganizer.ui.components.submission.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.paw_filled
import dev.tekofx.artganizer.ui.components.SmallCard
import dev.tekofx.artganizer.ui.components.input.ButtonWithIcon
import dev.tekofx.artganizer.ui.viewmodels.submissions.SubmissionDetails

@Composable
fun CharactersSection(
    areThereCharacters: Boolean,
    submissionDetails: SubmissionDetails,
    onAddCharactersButton: () -> Unit,
    onValueChange: (SubmissionDetails) -> Unit
) {
    if (areThereCharacters) {
        Text("Characters", style = MaterialTheme.typography.headlineSmall)
        if (submissionDetails.characters.isEmpty()) {
            ButtonWithIcon(
                onClick = onAddCharactersButton,
                icon = Res.drawable.paw_filled,
                text = "Add Characters",
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                submissionDetails.characters.forEach { item ->
                    SmallCard(
                        title = item.name,
                        imagePath = item.imagePath,
                        deletable = true,
                        onClear = {
                            onValueChange(
                                submissionDetails.copy(
                                    characters = submissionDetails.characters.filterNot { it.characterId == item.characterId }
                                )
                            )
                        }
                    )
                }
                ButtonWithIcon(
                    onClick = onAddCharactersButton,
                    icon = Res.drawable.paw_filled,
                    text = "Add Characters",
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}