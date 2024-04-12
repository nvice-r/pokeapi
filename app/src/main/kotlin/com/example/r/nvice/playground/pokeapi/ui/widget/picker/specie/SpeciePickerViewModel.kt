package com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import com.example.r.nvice.playground.pokeapi.data.repository.PokemonRepository
import com.example.r.nvice.playground.pokeapi.model.pokemon.Specie
import com.example.r.nvice.playground.pokeapi.ui.widget.picker.BasePickerViewModel
import com.example.r.nvice.playground.pokeapi.util.extension.toGenerativeRegex
import javax.inject.Inject

@HiltViewModel
class SpeciePickerViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) : BasePickerViewModel<SpeciePickerRequest>() {

    var state by mutableStateOf(SpeciePickerUiState())
        private set

    override fun init(request: SpeciePickerRequest) {
        super.init(request)
        initSpecieList()
        state = state.copy(
            queryText = request.specieName
        )
    }

    override fun clear() {
        super.clear()
        state = SpeciePickerUiState()
    }

    fun onQueryTextChange(queryText: String) {
        state = state.copy(
            queryText = queryText
        )
        getSpecieList(
            queryText = queryText.lowercase()
        )
    }

    private fun initSpecieList() {
        pokemonRepository.getPokemonSpecieListFlow()
            .handleSpecieList()
            .launchIn(viewModelScope)
    }

    private fun getSpecieList(queryText: String) {
        pokemonRepository.getPokemonSpecieListFlow()
            .map { specieList ->
                specieList.filter {
                    it.name?.matches(
                        queryText.toGenerativeRegex()
                    ) ?: false
                }
            }
            .handleSpecieList()
            .launchIn(viewModelScope)
    }

    private fun Flow<List<Specie>>.handleSpecieList() = flowOn(Dispatchers.IO)
        .take(1)
        .onStart {
            state = state.copy(
                isLoading = true
            )
        }
        .onEach {
            state = state.copy(
                specieList = it
            )
        }
        .onCompletion {
            state = state.copy(
                isLoading = false
            )
        }
}