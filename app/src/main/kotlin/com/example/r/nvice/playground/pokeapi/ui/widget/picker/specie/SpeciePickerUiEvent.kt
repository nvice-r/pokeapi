package com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie

import com.example.r.nvice.playground.pokeapi.model.pokemon.Specie

data class SpeciePickerUiEvent(
    val onNavigateBack: () -> Unit = {},
    val onSpeciePick: (Specie) -> Unit = {},
    val onQueryTextChange: (String) -> Unit = {},
)
