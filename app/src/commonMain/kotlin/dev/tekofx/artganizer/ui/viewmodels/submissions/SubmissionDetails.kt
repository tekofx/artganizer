package dev.tekofx.artganizer.ui.viewmodels.submissions

import dev.tekofx.artganizer.entities.Artist
import dev.tekofx.artganizer.entities.Character
import dev.tekofx.artganizer.entities.CharacterWithSubmissions
import dev.tekofx.artganizer.entities.Image
import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.SubmissionWithArtist
import dev.tekofx.artganizer.entities.Tag

data class SubmissionDetails(
    val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val thumbnail: String = "",
    val rating: Int = 0,
    val artistId: Long? = null,
    val artist: Artist? = null,
    val images: List<Image> = emptyList<Image>(),
    val characters: List<Character> = emptyList<Character>(),
    val tags: List<Tag> = emptyList<Tag>()
)

fun SubmissionDetails.toSubmissionWithArtist(): SubmissionWithArtist = SubmissionWithArtist(
    submission = Submission(
        submissionId = id,
        title = title,
        description = description,
        thumbnail = thumbnail,
        rating = rating,
        artistId = artistId
    ),
    artist = artist,
    images = images,
    characters = characters,
    tags = tags
)

fun SubmissionDetails.toSubmission(): Submission = Submission(
    submissionId = id,
    title = title,
    description = description,
    thumbnail = thumbnail,
    rating = rating,
    artistId = artistId,
)


fun List<CharacterWithSubmissions>.toListOfCharacters(): List<Character> {
    return this.map { characterWithSubmissions ->
        Character(
            characterId = characterWithSubmissions.character.characterId,
            name = characterWithSubmissions.character.name,
            species = characterWithSubmissions.character.species,
            imagePath = characterWithSubmissions.character.imagePath,
            pronouns = characterWithSubmissions.character.pronouns,
            gender = characterWithSubmissions.character.gender,
            height = characterWithSubmissions.character.height,
            description = characterWithSubmissions.character.description
        )
    }
}

fun SubmissionWithArtist.toSubmissionDetails(): SubmissionDetails = SubmissionDetails(
    id = submission.submissionId,
    title = submission.title,
    description = submission.description,
    thumbnail = submission.thumbnail,
    rating = submission.rating,
    artist = artist,
    images = images,
    characters = characters,
    tags = tags
)