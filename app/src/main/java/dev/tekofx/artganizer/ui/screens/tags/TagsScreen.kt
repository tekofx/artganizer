package dev.tekofx.artganizer.ui.screens.tags

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.layout.AnimatedThinSearchBarScaffold
import dev.tekofx.artganizer.ui.components.tags.TagCard
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(
    navHostController: NavHostController,
    tagsViewModel: TagsViewModel
) {

    val tags by tagsViewModel.tags.collectAsState()
    val alignment by tagsViewModel.alignment.collectAsState()
    val fabVisible by tagsViewModel.fabVisible.collectAsState()
    val searchBarVisible by tagsViewModel.searchBarVisible.collectAsState()

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column {
                Text("Sheet")
            }
        },
    ) {
        AnimatedThinSearchBarScaffold(
            alignment = alignment,
            searchBarVisible = searchBarVisible,
            onClear = { tagsViewModel.clearTextField() },
            textFieldState = tagsViewModel.textFieldState,
            onFocusChanged = { tagsViewModel.setIsSearchBarFocused(it) },
            fabVisible = fabVisible,
            onFabClick = { navHostController.navigate(NavigateDestinations.TAG_CREATION_SCREEN) },

            ) {
            LazyColumn(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(tags) { tag ->
                    TagCard(
                        tag = tag,
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { navHostController.navigate("${NavigateDestinations.TAGS_SCREEN}/${tag.tag.tagId}") }
                    )
                }
            }


        }
    }
}