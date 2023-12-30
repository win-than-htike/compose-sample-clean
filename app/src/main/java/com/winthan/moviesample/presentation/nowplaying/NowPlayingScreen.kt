package com.winthan.moviesample.presentation.nowplaying

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.winthan.domain.models.Movie
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NowPlayingScreen(
    viewModel: NowPlayingViewModel
) {

    val context = LocalContext.current

    LaunchedEffect(viewModel.errorState) {
        viewModel.errorState.collectLatest {
            when (it) {
                is NowPlayingViewModel.ErrorState.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LaunchedEffect(viewModel.toastEvent) {
        viewModel.toastEvent.collectLatest {
            when (it) {
                is NowPlayingViewModel.ToastEvent.ShowToast -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val uiState by viewModel
        .uiState
        .collectAsState()

    NowPlayingScreen(
        uiState = uiState,
        onMovieClick = {
            viewModel.onEvent(NowPlayingViewModel.UIEvent.OnMovieClick(it))
        }
    )

}

@Composable
private fun NowPlayingScreen(
    uiState: NowPlayingViewModel.UIState,
    onMovieClick: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (uiState is NowPlayingViewModel.UIState.Empty) {
            // show empty screen
        }
        if (uiState is NowPlayingViewModel.UIState.Success) {
            MoviesGrid(uiState.data) {
                onMovieClick(it)
            }
        }
    }

}

@Composable
private fun MoviesGrid(
    movies: List<Movie>,
    onMovieClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(movies) { movie ->
            val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
            AsyncImage(
                modifier = Modifier
                    .clickable {
                        onMovieClick(movie.title)
                    },
                model = imageUrl,
                contentDescription = "Movie Poster"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NowPlayingScreenPreview() {
    NowPlayingScreen(
        uiState = NowPlayingViewModel.UIState.Empty,
        onMovieClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun NowPlayingScreenLoadingPreview() {
    NowPlayingScreen(
        uiState = NowPlayingViewModel.UIState.Loading,
        onMovieClick = {}
    )
}