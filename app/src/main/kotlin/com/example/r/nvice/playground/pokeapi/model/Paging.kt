package com.example.r.nvice.playground.pokeapi.model

data class Paging<T>(
    val count: Int?,
    val results: List<T>,
    val next: String?,
    val previous: String?
)

fun Paging<*>.hasNext() = next != null
fun Paging<*>.hasPrevious() = previous != null
