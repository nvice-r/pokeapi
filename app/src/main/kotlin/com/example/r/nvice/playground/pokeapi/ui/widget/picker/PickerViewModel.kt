package com.example.r.nvice.playground.pokeapi.ui.widget.picker

import androidx.lifecycle.ViewModel

interface PickerViewModel<Request> {
    var request: Request?

    fun init(request: Request)
    fun clear()
}

abstract class BasePickerViewModel<Request>: ViewModel(), PickerViewModel<Request> {
    override var request: Request? = null

    override fun init(request: Request) {
        this.request = request
    }

    override fun clear() {
        request = null
    }
}

class PickerViewModelDelegate<Request> : ViewModel(), PickerViewModel<Request> {
    override var request: Request? = null

    override fun init(request: Request) {
        this.request = request
    }

    override fun clear() {
        request = null
    }
}
