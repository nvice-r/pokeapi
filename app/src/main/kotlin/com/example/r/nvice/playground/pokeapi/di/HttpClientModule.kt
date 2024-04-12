package com.example.r.nvice.playground.pokeapi.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder =
        OkHttpClient.Builder()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        okHttpClientBuilder: OkHttpClient.Builder,
    ): OkHttpClient {
        return okHttpClientBuilder
            .retryOnConnectionFailure(true)
            .build()
    }
}