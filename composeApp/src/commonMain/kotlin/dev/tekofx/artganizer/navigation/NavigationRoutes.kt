package dev.tekofx.artganizer.navigation

import kotlinx.serialization.Serializable

@Serializable
object ArtistsListRoute

@Serializable
data class ArtistDetailsRoute(val id:Long)

@Serializable
object ArtistCreationRoute

@Serializable
object CharactersListRoute

@Serializable
data class CharacterDetailsRoute(val id:Long)

@Serializable
object CharacterCreationRoute

@Serializable
object SubmissionsListRoute

@Serializable
data class SubmissionDetailsRoute(val id:Long)

@Serializable
object SubmissionCreationRoute

@Serializable
object TagsListRoute

@Serializable
data class TagDetailsRoute(val id:Long)

@Serializable
object TagCreationRoute