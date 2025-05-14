package dev.tekofx.artganizer.ui.utils

import dev.tekofx.artganizer.R

fun getSocialNetworkIconRes(socialNetwork: String): Int {
    return when {
        socialNetwork.contains("twitter", ignoreCase = true) -> R.drawable.brand_x
        socialNetwork.contains("instagram", ignoreCase = true) -> R.drawable.brand_instagram
        socialNetwork.contains("t.me", ignoreCase = true) -> R.drawable.brand_telegram
        else -> R.drawable.world
    }
}