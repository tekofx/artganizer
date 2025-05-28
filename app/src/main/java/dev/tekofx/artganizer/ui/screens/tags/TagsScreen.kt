package dev.tekofx.artganizer.ui.screens.tags

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.input.SearchBar
import dev.tekofx.artganizer.ui.viewmodels.tags.TagsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagsScreen(
    navHostController: NavHostController,
    tagsViewModel: TagsViewModel
) {

    val tags by tagsViewModel.tags.collectAsState()

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
        Scaffold(
            bottomBar = {
                SearchBar(queryText = "")
            },
            floatingActionButton = {
                CreateFab(
                    onClick = { navHostController.navigate(NavigateDestinations.TAG_CREATION_SCREEN) },
                )
            },
        ) {
            Column {
                Text("Tags Screen Content")
            }
        }
    }
}