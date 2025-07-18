package dev.tekofx.artganizer.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import artganizer.app.generated.resources.Res
import artganizer.app.generated.resources.edit
import artganizer.app.generated.resources.select_all
import artganizer.app.generated.resources.share
import artganizer.app.generated.resources.trash
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Suppress("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen() {
    Scaffold {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Settings",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center
            )
            SubmissionButtonsSettings()
            ShareOptionsSettings()
        }
    }
}

@Composable
fun ShareOptionsSettings() {
    Text(
        "Share options",
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
    )
    Text("Which info add as text when clicking share button")


    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "Title - Description - Author",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }

    SettingSwitch(
        label = "Title",
        checked = true,
        onCheckedChange = { /* Handle switch state change */ }
    )
    SettingSwitch(
        label = "Description",
        checked = true,
        onCheckedChange = { /* Handle switch state change */ }
    )
    SettingSwitch(
        label = "Author",
        checked = true,
        onCheckedChange = { /* Handle switch state change */ }
    )

    TextField(
        value = " - ",
        label = { Text("Separator") },
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )

}

@Composable
fun SubmissionButtonsSettings() {
    Text(text = "Submission Buttons", style = MaterialTheme.typography.titleLarge)
    Text("Which buttons to show in the submission details screen")
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            ButtonPreview(Res.drawable.edit)
            ButtonPreview(Res.drawable.trash)
            ButtonPreview(Res.drawable.share)
            ButtonPreview(Res.drawable.select_all)
        }
    }

    SettingSwitch(
        label = "Show Details",
        checked = true,
        onCheckedChange = { /* Handle switch state change */ }
    )
    SettingSwitch(
        label = "Delete",
        checked = true,
        onCheckedChange = { /* Handle switch state change */ }
    )
    SettingSwitch(
        label = "Edit",
        checked = true,
        onCheckedChange = { /* Handle switch state change */ }
    )
    SettingSwitch(
        label = "Share",
        checked = true,
        onCheckedChange = { /* Handle switch state change */ }
    )
}

@Composable
fun SettingSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(label, style = MaterialTheme.typography.bodyLarge)
        Switch(
            checked = checked,
            onCheckedChange = { onCheckedChange },
        )
    }
}

@Composable
fun ButtonPreview(
    icon: DrawableResource
) {
    Surface(
        Modifier
            .padding(8.dp),
        shape = CircleShape,
        color = Color.Transparent,
        border = BorderStroke(color = MaterialTheme.colorScheme.onPrimary, width = 1.dp)
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painterResource(icon),
                modifier = Modifier.size(30.dp),
                contentDescription = "Close",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}