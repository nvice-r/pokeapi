package com.example.r.nvice.playground.pokeapi.model.pokemon

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pokemon(
    val name: String? = null,
    val sprites: Sprites? = null
)