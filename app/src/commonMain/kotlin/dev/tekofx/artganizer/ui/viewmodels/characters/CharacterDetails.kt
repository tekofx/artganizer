package dev.tekofx.artganizer.ui.viewmodels.characters

import android.net.Uri
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.entities.Submission

data class CharacterDetails(
    val id: Long = 0,
    val name: String = "",
    val species: String? = null,
    val gender: String? = null,
    val pronouns: String? = null,
    val height: String? = null,
    val imagePath: Uri? = null,
    val description: String? = null,
    val submissions: List<Submission> = emptyList(),
)

fun CharacterDetails.toCharacterWithSubmissions(): CharacterWithSubmissions =
    CharacterWithSubmissions(
        Character(
            characterId = id,
            name = name,
            imagePath = imagePath,
            species = species,
            gender = gender,
            pronouns = pronouns,
            height = height,
            description = description
        ),
        submissions = submissions
    )

fun CharacterWithSubmissions.toCharacterDetails(): CharacterDetails = CharacterDetails(
    id = character.characterId,
    name = character.name,
    species = character.species,
    gender = character.gender,
    pronouns = character.pronouns,
    height = character.height,
    description = character.description,
    imagePath = character.imagePath,
    submissions = submissions,
)