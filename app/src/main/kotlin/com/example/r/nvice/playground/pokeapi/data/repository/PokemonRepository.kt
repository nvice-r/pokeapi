package com.example.r.nvice.playground.pokeapi.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import com.example.r.nvice.playground.pokeapi.model.pokemon.Pokemon
import com.example.r.nvice.playground.pokeapi.model.ProgressiveResult
import com.example.r.nvice.playground.pokeapi.model.pokemon.Specie
import com.example.r.nvice.playground.pokeapi.model.hasNext
import com.example.r.nvice.playground.pokeapi.model.hasPrevious
import com.example.r.nvice.playground.pokeapi.network.PokemonService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val api: PokemonService
) {
    private val specieList = mutableListOf<Specie>()
    private var specieOffset = 0

    fun getPokemonByNameFlow(name: String): Flow<Pokemon> {
        return flow {
            emit(
                api.getPokemonAsync(
                    name = name
                )
            )
        }
    }

    fun getPokemonSpecieListFlow(): Flow<List<Specie>> {
        return flowOf(specieList)
    }

    fun hasPokemonSpecieListFetched(): Boolean {
        return specieList.isNotEmpty()
    }

    fun fetchPokemonSpecieListFlow(): Flow<ProgressiveResult<List<Specie>>> {
        val limit = 50
        var hasNext = true
        var progress = 0f
        return (0..Int.MAX_VALUE)
            .asFlow()
            .takeWhile { hasNext }
            .map {
                api.getPokemonSpecieListAsync(
                    offset = specieOffset,
                    limit = limit
                )
            }.onEach { paging ->
                if (!paging.hasNext()) {
                    hasNext = false
                    specieOffset += paging.results.size
                } else {
                    specieOffset += limit
                }
            }.map { paging ->
                val count = paging.count?.toFloat() ?: error("unexpected progress")
                val data = paging.results.also { result ->
                    if (!paging.hasPrevious() && specieList.isNotEmpty()) {
                        specieList.clear()
                    }
                    specieList += result
                }
                progress += data.size / count
                ProgressiveResult(
                    progress = progress,
                    data = data
                )
            }
    }
}