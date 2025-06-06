package dev.tekofx.artganizer.ui.screens.artists


import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import dev.tekofx.artganizer.navigation.NavigateDestinations
import dev.tekofx.artganizer.ui.components.artists.ArtistCard
import dev.tekofx.artganizer.ui.components.buttons.CreateFab
import dev.tekofx.artganizer.ui.components.input.ThinSearchBar
import dev.tekofx.artganizer.ui.viewmodels.artists.ArtistsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
    val isSearchBarFocused = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current


    // Get screen height in dp
    val configuration = LocalConfiguration.current
    val screenHeightPx = configuration.screenHeightDp
    val screenHeightDp = with(LocalDensity.current) { screenHeightPx.dp }


    // Animate offset based on focus state
    val searchBarOffset by animateDpAsState(
        if (isSearchBarFocused.value) 20.dp else screenHeightDp - 160.dp
    )
    // Monitor keyboard visibility
    val isImeVisible = WindowInsets.isImeVisible
    LaunchedEffect(isImeVisible) {
        if (!isImeVisible) {
            focusManager.clearFocus() // Clear focus when keyboard is hidden
            isSearchBarFocused.value = false // Reset search bar focus state
        }
    }

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
                if (!isSearchBarFocused.value && artistsViewModel.state.text.isEmpty()) {
                    CreateFab(
                        onClick = { navHostController.navigate(NavigateDestinations.ARTIST_CREATION_SCREEN) },
                    )
                }
            },
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(10.dp),
                ) {
                    if (isSearchBarFocused.value) {
                        item {
                            Spacer(modifier = Modifier.height(60.dp))
                        }
                    }

                    items(artists) { artist ->
                        ArtistCard(
                            artist,
                            onClick = {
                                navHostController.navigate("${NavigateDestinations.ARTISTS_SCREEN}/${artist.artist.artistId}")
                            },
                            modifier = Modifier.animateItem()
                        )
                    }
                    if (!isSearchBarFocused.value) {
                        item {
                            Spacer(modifier = Modifier.height(40.dp))
                        }
                    }

                }
                ThinSearchBar(
                    onClear = { artistsViewModel.clearTextField() },
                    state = artistsViewModel.state,
                    onFocusChanged = { isSearchBarFocused.value = it },
                    focusManager = focusManager,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .offset(y = searchBarOffset)
                        .padding(horizontal = 10.dp)
                        .animateContentSize()
                )
            }
        }
    }
}
