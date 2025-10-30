package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import artganizer.composeapp.generated.resources.Res
import artganizer.composeapp.generated.resources.add
import artganizer.composeapp.generated.resources.gallery_outlined
import artganizer.composeapp.generated.resources.palette_outlined
import artganizer.composeapp.generated.resources.paw_outlined
import artganizer.composeapp.generated.resources.tag_outlined
import org.jetbrains.compose.resources.painterResource

@Composable
fun DropdownAdd() {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(painterResource(Res.drawable.add), contentDescription = "More options")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Submission") },
                leadingIcon = {
                    Icon(
                        painterResource(Res.drawable.gallery_outlined),
                        contentDescription = "Submission"
                    )
                },
                onClick = { /* Handle click */ }
            )
            DropdownMenuItem(
                text = { Text("Artist") },
                leadingIcon = {
                    Icon(
                        painterResource(Res.drawable.palette_outlined),
                        contentDescription = "Artist"
                    )
                },
                onClick = { /* Handle click */ }
            )
            DropdownMenuItem(
                text = { Text("Character") },
                leadingIcon = {
                    Icon(
                        painterResource(Res.drawable.paw_outlined),
                        contentDescription = "Character"
                    )
                },
                onClick = { /* Handle click */ }
            )
            DropdownMenuItem(
                text = { Text("Tag") },
                leadingIcon = {
                    Icon(
                        painterResource(Res.drawable.tag_outlined),
                        contentDescription = "Tag"
                    )
                },
                onClick = { /* Handle click */ }
            )
        }
    }
}