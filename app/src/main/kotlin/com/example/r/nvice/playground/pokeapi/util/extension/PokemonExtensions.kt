package com.example.r.nvice.playground.pokeapi.util.extension

import com.example.r.nvice.playground.pokeapi.model.pokemon.Pokemon

val Pokemon.imgUrl: String?
    get() = this.sprites?.other?.officialArtwork?.frontDefault