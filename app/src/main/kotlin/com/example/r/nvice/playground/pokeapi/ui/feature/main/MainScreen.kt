package com.example.r.nvice.playground.pokeapi.ui.feature.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ramcosta.composedestinations.annotation.Destination
import com.example.r.nvice.playground.pokeapi.R
import com.example.r.nvice.playground.pokeapi.model.pokemon.Pokemon
import com.example.r.nvice.playground.pokeapi.ui.theme.PokeapiTheme
import com.example.r.nvice.playground.pokeapi.ui.theme.Red
import com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie.SpeciePickerDialog
import com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie.SpeciePickerRequest
import com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie.rememberSpeciePickerState
import com.example.r.nvice.playground.pokeapi.util.extension.capitalize
import com.example.r.nvice.playground.pokeapi.util.extension.imgUrl

@Destination
@Composable
fun MainRoute(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel()
) {
    MainScreen(
        modifier = modifier,
        state = viewModel.state,
        event = MainUiEvent(
            onChooseClick = viewModel::onChoose,
            onChooseAgainClick = viewModel::reset,
            onNotFoundDialogDismiss = viewModel::reset,
            onQueryTextChange = viewModel::onQueryTextChange,
            onSpeciePickerDismiss = viewModel::reset
        )
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainUiState = MainUiState(),
    event: MainUiEvent = MainUiEvent()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val speciePickerState = rememberSpeciePickerState()
    val requestForSpeciePicker = {
        speciePickerState.request(
            request = SpeciePickerRequest(),
            callback = { result ->
                event.onQueryTextChange(
                    result.specie.name?.capitalize().orEmpty()
                )
            }
        )
    }
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue())
    }
    Scaffold(
        modifier = modifier.imePadding(),
        containerColor = Color.White,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(padding)
                .padding(
                    horizontal = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        top = 56.dp,
                        bottom = 36.dp
                    )
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .padding(
                            horizontal = 56.dp,
                            vertical = 16.dp
                        )
                        .heightIn(max = 256.dp),
                    painter = painterResource(
                        R.drawable.img_logo_pokeapi
                    ),
                    contentDescription = "logo"
                )
                if (state.pokemon != null) {
                    Text(
                        modifier = Modifier.padding(top = 36.dp),
                        fontSize = 18.sp,
                        text = "I choose you!!"
                    )
                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        text = state.pokemon.name?.capitalize().orEmpty()
                    )
                    AsyncImage(
                        modifier = Modifier
                            .height(300.dp)
                            .padding(top = 56.dp),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(state.pokemon.imgUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(
                            id = R.drawable.img_pokeball_placeholder
                        ),
                        contentDescription = "pokemon"
                    )
                } else {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(.6f)
                            .padding(top = 48.dp),
                        painter = painterResource(id = R.drawable.img_pokemon_choose),
                        contentDescription = "placeholder"
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        value = textFieldValue,
                        label = {
                            Text(
                                text = "I choose you...",
                                fontSize = 18.sp
                            )
                        },
                        placeholder = {
                            Text(
                                text = "ex. Pikachu",
                                color = Color.Gray,
                                fontSize = 18.sp
                            )
                        },
                        trailingIcon = {
                            if (state.isPokedexEnabled) {
                                IconButton(
                                    onClick = {
                                        requestForSpeciePicker()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.List,
                                        contentDescription = "list icon"
                                    )
                                }
                            }
                        },
                        onValueChange = { value ->
                            textFieldValue = value
                            event.onQueryTextChange(value.text)
                        },
                        textStyle = TextStyle(
                            fontSize = 18.sp
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                event.onChooseClick()
                                keyboardController?.hide()
                            }
                        ),
                        singleLine = true,
                        maxLines = 1
                    )
                    Button(
                        modifier = Modifier
                            .padding(
                                top = 8.dp
                            )
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Red
                        ),
                        onClick = event.onChooseClick
                    ) {
                        Text(
                            text = "Choose"
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (state.pokemon != null) {
                    Button(
                        modifier = Modifier
                            .padding(
                                top = 36.dp,
                                bottom = 24.dp
                            )
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Red
                        ),
                        onClick = event.onChooseAgainClick
                    ) {
                        Text(
                            text = "Choose again"
                        )
                    }
                } else {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 36.dp),
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        text = buildAnnotatedString {
                            append("Created by\t")
                            appendInlineContent(id = "logo")
                        },
                        inlineContent = mapOf(
                            "logo" to InlineTextContent(
                                placeholder = Placeholder(
                                    width = 36.sp,
                                    height = 36.sp,
                                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                                )
                            ) {
                                Image(
                                    modifier = Modifier
                                        .padding(start = 4.dp)
                                        .size(36.dp),
                                    painter = painterResource(id = R.drawable.img_logo_owner),
                                    contentDescription = "logo"
                                )
                            }
                        )
                    )
                }
            }
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier.background(
                color = Color(0x5F000000)
            ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp)
            )
        }
    }
    if (state.isPokemonNotFound) {
        AlertDialog(
            title = {
                Text(
                    text = "Hmmmm..."
                )
            },
            text = {
                Text(
                    text = buildAnnotatedString {
                        append("You probably misspelled it.\n")
                        append("Don't worry! You can use the Pokedex ")
                        appendInlineContent(id = "pokedexIcon")
                        append("\tto help you to find it easily")
                    },
                    inlineContent = mapOf(
                        "pokedexIcon" to InlineTextContent(
                            placeholder = Placeholder(
                                width = 16.sp,
                                height = 16.sp,
                                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.List,
                                contentDescription = "list icon"
                            )
                        }
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        requestForSpeciePicker()
                    }
                ) {
                    Text(
                        text = "Open Pokedex!"
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = event.onNotFoundDialogDismiss
                ) {
                    Text(
                        text = "Later"
                    )
                }
            },
            containerColor = Color.White,
            onDismissRequest = event.onNotFoundDialogDismiss
        )
    }
    speciePickerState.request?.let {
        SpeciePickerDialog(
            title = "Pokedex",
            state = speciePickerState,
            onDismissRequest = {
                speciePickerState.clear()
                event.onSpeciePickerDismiss()
            }
        )
    }
    LaunchedEffect(state.queryText) {
        textFieldValue = textFieldValue.copy(
            text = state.queryText,
            selection = TextRange(state.queryText.length)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    PokeapiTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonPreview() {
    PokeapiTheme {
        MainScreen(
            state = MainUiState(
                pokemon = Pokemon(
                    name = "Pikachu"
                )
            )
        )
    }
}
