package dev.tekofx.artganizer.ui.components.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.ui.components.Avatar
import dev.tekofx.artganizer.ui.components.SubmissionCount

@Composable
fun CharacterCard(
    character: CharacterWithSubmissions,
    onClick: (Long) -> Unit
) {
    Card(
        onClick = { onClick(character.character.characterId) }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Avatar(
                character.character.imagePath,
                fallbackText = character.character.name,
                shape = MaterialTheme.shapes.large,
            )
            Text(character.character.name, style = MaterialTheme.typography.headlineLarge)

            character.character.species?.let {
                Text(
                    character.character.species,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Light
                )
            }
            SubmissionCount(character.submissions.size)
        }
    }
}