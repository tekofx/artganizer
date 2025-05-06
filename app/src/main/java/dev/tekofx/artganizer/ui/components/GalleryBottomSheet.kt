package dev.tekofx.artganizer.ui.components

import AutoCompleteComboBox
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GalleryBottomSheet() {
    Column(
        modifier = Modifier
            .height(300.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SearchBar(label = { Text("Search Anything") })
        AutoCompleteComboBox(items = listOf("Hola", "Adios"), onItemSelected = {})
    }
}