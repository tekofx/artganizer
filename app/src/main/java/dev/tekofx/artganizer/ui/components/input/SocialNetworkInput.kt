package dev.tekofx.artganizer.ui.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.tekofx.artganizer.R
import dev.tekofx.artganizer.ui.IconResource

@Composable
fun SocialNetworkInput(
    socialNetworks: List<String>,
    onAddSocialNetwork: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        // Display existing social networks
        socialNetworks.forEach { url ->
            val iconRes = when {
                url.contains("twitter", ignoreCase = true) -> R.drawable.brand_x
                url.contains("instagram", ignoreCase = true) -> R.drawable.brand_instagram
                url.contains("t.me", ignoreCase = true) -> R.drawable.brand_telegram
                else -> R.drawable.world
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    painter = IconResource.fromDrawableResource(iconRes).asPainterResource(),
                    contentDescription = "Social Network Icon",
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = url,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        // Input field to add a new social network
        var newSocialNetwork by remember { mutableStateOf("") }
        OutlinedTextField(
            value = newSocialNetwork,
            onValueChange = { newSocialNetwork = it },
            label = { Text("Add Social Network") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Button(
            onClick = {
                if (newSocialNetwork.isNotBlank()) {
                    onAddSocialNetwork(newSocialNetwork)
                    newSocialNetwork = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add")
        }
    }
}