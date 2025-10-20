package dev.tekofx.artganizer.ui.screens.characters

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
expect fun CharacterDetailsScreen(
    characterId: Long,
    navHostController: NavHostController,
)