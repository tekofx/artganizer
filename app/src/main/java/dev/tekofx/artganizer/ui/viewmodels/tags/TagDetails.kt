package dev.tekofx.artganizer.ui.viewmodels.tags

import dev.tekofx.artganizer.entities.Submission
import dev.tekofx.artganizer.entities.Tag
import dev.tekofx.artganizer.entities.TagWithSubmissions

data class TagDetails(
    val id: Long = 0,
    val name: String = "",
    val submissions: List<Submission> = emptyList()
)

fun TagDetails.toTagWithSubmissions(): TagWithSubmissions = TagWithSubmissions(
    Tag(
        tagId = id,
        name = name
    ),
    submissions = emptyList()
)


fun TagWithSubmissions.toTagDetails(): TagDetails = TagDetails(
    id = tag.tagId,
    name = tag.name,
    submissions = submissions
)

fun TagWithSubmissions.toTag(): Tag = Tag(
    tagId = tag.tagId,
    name = tag.name
)

fun List<TagWithSubmissions>.toListOfTags(): List<Tag> = this.map { it.toTag() }