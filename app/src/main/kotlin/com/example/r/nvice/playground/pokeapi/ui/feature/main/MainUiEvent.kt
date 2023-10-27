package com.example.r.nvice.playground.pokeapi.ui.feature.main

data class MainUiEvent(
    val onChooseAgainClick: () -> Unit = {},
    val onChooseClick: () -> Unit = {},
    val onNotFoundDialogDismiss: () -> Unit = {},
    val onQueryTextChange: (String) -> Unit = {},
    val onSpeciePickerDismiss: () -> Unit = {}
)
