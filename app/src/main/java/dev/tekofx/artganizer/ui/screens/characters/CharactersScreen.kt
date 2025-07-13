package dev.tekofx.artganizer.ui.screens.characters

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import dev.tekofx.artganizer.getActivityViewModel
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.characters.CharacterCard
import dev.tekofx.artganizer.ui.components.layout.AnimatedThinSearchBarScaffold
import dev.tekofx.artganizer.ui.viewmodels.characters.CharactersViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    navHostController: NavHostController,
    charactersViewModel: CharactersViewModel = getActivityViewModel<CharactersViewModel>(),
) {

    val characters = charactersViewModel.characters.collectAsState()
    val alignment by charactersViewModel.alignment.collectAsState()
    val fabVisible by charactersViewModel.fabVisible.collectAsState()
    val searchBarVisible by charactersViewModel.searchBarVisible.collectAsState()
    val listState by charactersViewModel.gridState.collectAsState()


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
        }
    ) {


        AnimatedThinSearchBarScaffold(
            alignment = alignment,
            searchBarVisible = searchBarVisible,
            textFieldState = charactersViewModel.textFieldState,
            onFocusChanged = { charactersViewModel.setIsSearchBarFocused(it) },
            onFabClick = { navHostController.navigate(NavigateDestinations.CHARACTER_CREATION_SCREEN) },
            fabVisible = fabVisible
        ) {
            LazyVerticalGrid(
                modifier = Modifier.padding(10.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                state = listState
            ) {
                items(characters.value) { character ->
                    CharacterCard(
                        modifier = Modifier.animateItem(),
                        character = character,
                        onClick = {
                            navHostController.navigate("${NavigateDestinations.CHARACTERS_SCREEN}/${character.character.characterId}")
                        },
                    )
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .height(50.dp)

                    )
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .height(50.dp)
                    )
                }
            }
        }
    }

}