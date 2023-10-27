package com.example.r.nvice.playground.pokeapi.model

data class ProgressiveResult<T>(
    val progress: Float,
    val data: T?
)
