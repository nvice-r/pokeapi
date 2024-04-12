package com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.r.nvice.playground.pokeapi.model.pokemon.Specie
import com.example.r.nvice.playground.pokeapi.ui.theme.PokeapiTheme
import com.example.r.nvice.playground.pokeapi.util.extension.capitalize

@Composable
fun SpeciePicker(
    modifier: Modifier = Modifier,
    title: String? = null,
    request: SpeciePickerRequest? = null,
    event: SpeciePickerUiEvent = SpeciePickerUiEvent(),
    viewModel: SpeciePickerViewModel = hiltViewModel()
) {
    SpeciePickerScreen(
        modifier = modifier,
        title = request?.pickerTitle ?: title,
        event = event.copy(
            onQueryTextChange = viewModel::onQueryTextChange
        ),
        state = viewModel.state
    )
    DisposableEffect(request) {
        request?.let(viewModel::init)
        onDispose {
            viewModel.clear()
        }
    }
}

@Composable
fun SpeciePickerScreen(
    modifier: Modifier = Modifier,
    title: String? = null,
    event: SpeciePickerUiEvent = SpeciePickerUiEvent(),
    state: SpeciePickerUiState = SpeciePickerUiState()
) {
    val specieListState = rememberLazyListState()
    Scaffold(
        modifier = modifier.imePadding(),
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title ?:  "Pokemon Specie Picker")
                },
                navigationIcon = {
                    IconButton(
                        onClick = event.onNavigateBack
                    ) {
                        Icon(
                            Icons.Rounded.ArrowBack,
                            contentDescription = "arrow back icon",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(),
                query = state.queryText.orEmpty(),
                onQueryChange = event.onQueryTextChange,
                onSearch = event.onQueryTextChange,
                active = true,
                onActiveChange = {},
                placeholder = {
                    Text(
                        text = "ex. Pikachu",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Search,
                        contentDescription = "search icon"
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White
                )
            ) {
                LazyColumn(
                    state = specieListState
                ) {
                    items(
                        items = state.specieList,
                        key = {
                            it.name.orEmpty()
                        }
                    ) { specie ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Transparent
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 0.dp
                            ),
                            shape = RoundedCornerShape(0),
                            onClick = {
                                with(event) {
                                    onSpeciePick(specie)
                                    onNavigateBack()
                                }
                            }
                        ) {
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = specie.name?.capitalize().orEmpty()
                            )
                        }
                    }
                }
            }
            LaunchedEffect(state.specieList) {
                if (specieListState.firstVisibleItemIndex != 0) {
                    specieListState.scrollToItem(0)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSpeciePickerScreen() {
    PokeapiTheme {
        SpeciePickerScreen(
            state = SpeciePickerUiState(
                specieList = listOf(
                    Specie("Pikachu"),
                    Specie("Charmander"),
                    Specie("Ditto")
                )
            )
        )
    }
}
