package com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie

import com.example.r.nvice.playground.pokeapi.model.pokemon.Specie

data class SpeciePickerUiState(
    val isLoading: Boolean = false,
    val specieList: List<Specie> = listOf(),
    val queryText: String? = null
)
