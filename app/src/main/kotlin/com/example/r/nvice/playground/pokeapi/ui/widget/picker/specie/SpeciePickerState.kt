package com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.r.nvice.playground.pokeapi.ui.widget.picker.BasePickerState


@Composable
fun rememberSpeciePickerState(
    request: SpeciePickerRequest? = null,
    callback: (SpeciePickerResult) -> Unit = {}
): SpeciePickerState {
    return remember {
        SpeciePickerState(
            request = request,
            callback = callback
        )
    }
}

class SpeciePickerState(
    request: SpeciePickerRequest? = null,
    callback: (SpeciePickerResult) -> Unit = {}
) : BasePickerState<SpeciePickerRequest, SpeciePickerResult>(
    request = request,
    callback = callback
)