package dev.tekofx.artganizer.ui.components.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.ui.components.Avatar

@Composable
fun CharacterCard(
    character: Character
) {
    Card {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Avatar(
                character.imagePath,
                fallbackText = character.name,
                size = 150.dp,
                shape = MaterialTheme.shapes.medium
            )
            Text(character.name, style = MaterialTheme.typography.headlineSmall)
            Text("Arctic Fox")
        }
    }
}