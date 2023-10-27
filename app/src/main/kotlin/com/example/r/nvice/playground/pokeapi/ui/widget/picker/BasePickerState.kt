package com.example.r.nvice.playground.pokeapi.ui.widget.picker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

abstract class BasePickerState<Request, Result>(
    request: Request?,
    callback: (Result) -> Unit
) {
    var request by mutableStateOf(request)
        private set
    var callback by mutableStateOf(callback)
        private set

    fun request(
        request: Request,
        callback: (Result) -> Unit
    ) {
        this.request = request
        this.callback = callback
    }

    fun clear() {
        this.request = null
        this.callback = {}
    }
}