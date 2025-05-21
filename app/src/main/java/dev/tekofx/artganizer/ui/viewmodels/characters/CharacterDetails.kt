package dev.tekofx.artganizer.ui.viewmodels.characters

import android.net.Uri
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.entities.Submission

data class CharacterDetails(
    val id: Long = 0,
    val name: String = "",
    val species: String? = null,
    val imagePath: Uri? = null,
    val submissions: List<Submission> = emptyList()
)

fun CharacterDetails.toCharacterWithSubmissions(): CharacterWithSubmissions =
    CharacterWithSubmissions(
        Character(
            characterId = id,
            name = name,
            imagePath = imagePath,
            species = species
        ),
        submissions = submissions
    )

fun CharacterWithSubmissions.toCharacterDetails(): CharacterDetails = CharacterDetails(
    id = character.characterId,
    name = character.name,
    species = character.species,
    imagePath = character.imagePath,
    submissions = submissions,
)