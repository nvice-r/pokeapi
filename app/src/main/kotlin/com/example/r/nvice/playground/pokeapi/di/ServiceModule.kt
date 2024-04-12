package com.example.r.nvice.playground.pokeapi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import com.example.r.nvice.playground.pokeapi.network.PokemonService

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    fun providePokemonService(
        retrofit: Retrofit
    ): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }
}