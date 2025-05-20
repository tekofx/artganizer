package dev.tekofx.artganizer.ui.viewmodels.characters

import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterWithSubmissions


data class CharacterUiState(
    val characterDetails: CharacterDetails = CharacterDetails(),
    val isEntryValid: Boolean = false
)

fun CharacterUiState.toCharacterWithSubmissions(): CharacterWithSubmissions =
    CharacterWithSubmissions(
        Character(
            characterId = characterDetails.id,
            name = characterDetails.name,
            imagePath = characterDetails.imagePath,
        ),
        submissions = characterDetails.submissions
    )