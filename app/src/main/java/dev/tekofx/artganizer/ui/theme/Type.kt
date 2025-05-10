package dev.tekofx.artganizer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.tekofx.artganizer.R

val bodyFontFamily = FontFamily(
    Font(
        R.font.teko_variable_font,
        weight = FontWeight.Light,

        )
)

val displayFontFamily = FontFamily(
    Font(
        R.font.teko_variable_font,
        weight = FontWeight.Light
    )
)

// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge,
    displayMedium = baseline.displayMedium,
    displaySmall = baseline.displaySmall,
    headlineLarge = baseline.headlineLarge,
    headlineMedium = baseline.headlineMedium,
    headlineSmall = baseline.headlineSmall,
    titleLarge = baseline.titleLarge,
    titleMedium = baseline.titleMedium,
    titleSmall = baseline.titleSmall,
    bodyLarge = baseline.bodyLarge,
    bodyMedium = baseline.bodyMedium,
    bodySmall = baseline.bodySmall,
    labelLarge = baseline.labelLarge,
    labelMedium = baseline.labelMedium,
    labelSmall = baseline.labelSmall,
)
