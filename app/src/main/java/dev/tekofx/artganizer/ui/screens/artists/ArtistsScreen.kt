package dev.tekofx.artganizer.ui.screens.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.artists.ArtistCard
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.input.ThinSearchBar
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    navHostController: NavHostController, artistsViewModel: ArtistsViewModel
) {

    val artists by artistsViewModel.artists.collectAsState()

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
        Scaffold(
            floatingActionButton = {
                CreateFab(
                    onClick = { navHostController.navigate(NavigateDestinations.ARTIST_CREATION_SCREEN) },
                )
            },
            bottomBar = {
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .padding(horizontal = 10.dp),

                    ) {
                    ThinSearchBar(
                        onClear = { artistsViewModel.onSearchTextChanged("") },
                        state = artistsViewModel.state
                    )
                }
            }
        ) { innerPaddingValues ->
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .padding(bottom = innerPaddingValues.calculateBottomPadding())
            ) {
                items(artists) { artist ->
                    ArtistCard(
                        artist,
                        onClick = {
                            navHostController.navigate("${NavigateDestinations.ARTISTS_SCREEN}/${artist.artist.artistId}")
                        },
                        //modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }

}