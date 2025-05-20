package dev.tekofx.artganizer.ui.components.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.components.Avatar

@Composable
fun CharacterCard(
    character: Character,
) {
    Card {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Avatar(
                character.imagePath,
                fallbackText = character.name,
                shape = MaterialTheme.shapes.medium,
            )
            Text(character.name, style = MaterialTheme.typography.headlineLarge)
            Text("Arctic Fox", style = MaterialTheme.typography.headlineSmall)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    IconResource.fromDrawableResource(R.drawable.gallery_outlined)
                        .asPainterResource(), contentDescription = ""
                )
                Text("11", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}