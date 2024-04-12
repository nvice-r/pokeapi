package com.example.r.nvice.playground.pokeapi.ui.feature.splash

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
class SplashViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    var state by mutableStateOf(SplashUiState())
        private set

    init {
        fetchSpecieList()
    }

    private fun fetchSpecieList() {
        pokemonRepository.fetchPokemonSpecieListFlow()
            .flowOn(Dispatchers.IO)
            .onStart {
                state = state.copy(
                    isLoading = true
                )
            }
            .onEach { result ->
                state = state.copy(
                    progress = result.progress
                )
            }
            .catch {}
            .onCompletion {
                state = state.copy(
                    isLoading = false,
                    isCompleted = true
                )
            }
            .launchIn(viewModelScope)
    }
}