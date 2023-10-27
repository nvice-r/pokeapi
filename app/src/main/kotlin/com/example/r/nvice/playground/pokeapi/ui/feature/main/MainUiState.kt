package com.example.r.nvice.playground.pokeapi.ui.feature.main

import com.example.r.nvice.playground.pokeapi.model.pokemon.Pokemon

data class MainUiState(
    val isLoading: Boolean = false,
    val pokemon: Pokemon? = null,
    val queryText: String = "",
    val isPokedexEnabled: Boolean = false,
    val isPokemonNotFound: Boolean = false
)
