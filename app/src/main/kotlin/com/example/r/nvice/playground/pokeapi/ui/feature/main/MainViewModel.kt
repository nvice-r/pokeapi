package com.example.r.nvice.playground.pokeapi.ui.feature.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import com.example.r.nvice.playground.pokeapi.data.repository.PokemonRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val defaultState = MainUiState(
        isPokedexEnabled = pokemonRepository.hasPokemonSpecieListFetched()
    )

    var state by mutableStateOf(defaultState)
        private set

    fun onQueryTextChange(queryText: String) {
        state = state.copy(
            queryText = queryText,
            isPokemonNotFound = false
        )
    }

    fun onChoose() {
        val queryText = state.queryText.trim()
        if (state.queryText != queryText) {
            state = state.copy(
                queryText = queryText
            )
        }
        if (queryText.isNotEmpty()) {
            pokemonRepository.getPokemonByNameFlow(
                name = queryText.lowercase()
            ).flowOn(Dispatchers.IO)
                .onStart {
                    state = state.copy(
                        isLoading = true
                    )
                }
                .onEach { pokemon ->
                    state = state.copy(
                        pokemon = pokemon
                    )
                }
                .catch {
                    state = state.copy(
                        isPokemonNotFound = true
                    )
                }
                .onCompletion {
                    state = state.copy(
                        isLoading = false
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    fun reset(
        keepQueryText: Boolean = true
    ) {
        state = defaultState.copy(
            queryText = if (keepQueryText) state.queryText else ""
        )
    }
}