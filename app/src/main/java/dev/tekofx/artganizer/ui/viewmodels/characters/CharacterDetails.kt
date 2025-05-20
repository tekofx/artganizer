package dev.tekofx.artganizer.ui.viewmodels.characters

import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.entities.Submission

data class CharacterDetails(
    val id: Long = 0,
    val name: String = "",
    val imagePath: String? = null,
    val submissions: List<Submission> = emptyList()
)

fun CharacterDetails.toCharacterWithSubmissions(): CharacterWithSubmissions =
    CharacterWithSubmissions(
        Character(
            characterId = id,
            name = name,
            imagePath = imagePath,
        ),
        submissions = submissions
    )

fun CharacterWithSubmissions.toCharacterDetails(): CharacterDetails = CharacterDetails(
    id = character.characterId,
    name = character.name,
    imagePath = character.imagePath,
    submissions = submissions
)