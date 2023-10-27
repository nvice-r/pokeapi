package com.example.r.nvice.playground.pokeapi.ui.widget.picker.specie

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun SpeciePickerDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    state: SpeciePickerState,
    onDismissRequest: () -> Unit = {}
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        ),
        onDismissRequest = onDismissRequest
    ) {
        Card {
            SpeciePicker(
                modifier = modifier,
                title = title,
                request = state.request,
                event = SpeciePickerUiEvent(
                    onNavigateBack = onDismissRequest,
                    onSpeciePick = {
                        state.callback(
                            SpeciePickerResult(it)
                        )
                    }
                )
            )
        }
    }
}
