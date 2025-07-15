package dev.tekofx.artganizer.ui.components.artists

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import dev.tekofx.artganizer.ui.IconResource
import dev.tekofx.artganizer.ui.utils.getSocialNetworkIconRes

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
    val context = LocalContext.current

    IconButton(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, socialNetwork.toUri())
            context.startActivity(intent)
        },
        modifier = Modifier.size(50.dp)
    ) {
        Icon(
            painter = IconResource
                .fromDrawableResource(getSocialNetworkIconRes(socialNetwork))
                .asPainterResource(),
            contentDescription = "Social Network Icon",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(5.dp)
        )
    }

}