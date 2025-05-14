package dev.tekofx.artganizer.ui.components.artists

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource

@Composable
fun SocialNetworks(
    socialNetworks: List<String>,
) {
    LazyRow {
        items(socialNetworks) {
            SocialNetworkItem(
                socialNetwork = it,
            )
        }
    }
}

@Composable
fun SocialNetworkItem(
    socialNetwork: String,
) {
    val iconRes = when {
        socialNetwork.contains("twitter", ignoreCase = true) -> R.drawable.brand_x
        socialNetwork.contains("instagram", ignoreCase = true) -> R.drawable.brand_instagram
        socialNetwork.contains("t.me", ignoreCase = true) -> R.drawable.brand_telegram
        else -> R.drawable.world
    }

    Icon(
        painter = IconResource.fromDrawableResource(iconRes).asPainterResource(),
        contentDescription = "Social Network Icon",
        modifier = Modifier.size(50.dp)
    )

}