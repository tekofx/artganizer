package dev.tekofx.artganizer.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    label: @Composable (() -> Unit) = { Text("Search") },
) {

    val value = ""

    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "") },
        trailingIcon = { Icon(Icons.Filled.Clear, contentDescription = "") },

        singleLine = true,
        value = value,
        onValueChange = { },
        shape = RoundedCornerShape(40.dp),
        label = { label() },
    )


}


@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(label = { Icon(Icons.Filled.Search, contentDescription = "") })
}
