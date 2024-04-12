package com.example.r.nvice.playground.pokeapi.model.pokemon

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Specie(
    val name: String?,
    val url: String? = null
)
