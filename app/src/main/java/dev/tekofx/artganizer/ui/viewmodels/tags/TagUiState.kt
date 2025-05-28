package dev.tekofx.artganizer.ui.viewmodels.tags

import dev.tekofx.artganizer.entities.TagWithSubmissions


data class TagUiState(
    val tagDetails: TagDetails = TagDetails(),
    val isEntryValid: Boolean = false
)

fun TagUiState.toTagWithSubmissions(): TagWithSubmissions = TagWithSubmissions(
    tag = tagDetails.toTagWithSubmissions().tag,
    submissions = tagDetails.toTagWithSubmissions().submissions
)