package com.example.r.nvice.playground.pokeapi.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.example.r.nvice.playground.pokeapi.model.Paging
import com.example.r.nvice.playground.pokeapi.model.pokemon.Pokemon
import com.example.r.nvice.playground.pokeapi.model.pokemon.Specie

interface PokemonService {

    @GET("pokemon/{name}")
    suspend fun getPokemonAsync(
        @Path("name") name: String
    ): Pokemon

    @GET("pokemon")
    suspend fun getPokemonSpecieListAsync(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Paging<Specie>
}