package com.example.r.nvice.playground.pokeapi.ui.feature.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import com.example.r.nvice.playground.pokeapi.ui.feature.destinations.MainRouteDestination
import com.example.r.nvice.playground.pokeapi.ui.theme.PokeapiTheme

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashRoute(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    event: SplashUiEvent = SplashUiEvent(),
    viewModel: SplashViewModel = hiltViewModel()
) {
    val state = viewModel.state
    SplashScreen(
        modifier = modifier,
        event = event.copy(
            onNavigateToMain = {
                navigator.navigate(MainRouteDestination)
            }
        ),
        state = state
    )
}

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    event: SplashUiEvent = SplashUiEvent(),
    state: SplashUiState = SplashUiState()
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .animateContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = state.progress != null) {
            state.progress?.let { progress ->
                val progressAnimation by animateFloatAsState(
                    targetValue = progress,
                    animationSpec = tween(durationMillis = 100),
                    label = "progress animation"
                )
                LinearProgressIndicator(
                    modifier = Modifier
                        .height(8.dp)
                        .clip(
                            RoundedCornerShape(24.dp)
                        ),
                    progress = progressAnimation
                )
            }
        }
    }
    LaunchedEffect(state.isCompleted) {
        if (state.isCompleted) {
            delay(500)
            event.onNavigateToMain()
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    PokeapiTheme {
        SplashScreen(
            state = SplashUiState(
                progress = .5f
            )
        )
    }
}