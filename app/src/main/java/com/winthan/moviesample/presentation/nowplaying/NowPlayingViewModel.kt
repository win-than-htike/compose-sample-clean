package com.winthan.moviesample.presentation.nowplaying

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.winthan.domain.Resource
import com.winthan.domain.models.Movie
import com.winthan.domain.repositories.MovieRepository
import com.winthan.domain.usecases.NowPlayingMovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NowPlayingViewModel @Inject constructor(
    private val nowPlayingMovieUseCases: NowPlayingMovieUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _errorState = MutableSharedFlow<ErrorState>()
    val errorState = _errorState.asSharedFlow()

    private val _toastEvent = MutableSharedFlow<ToastEvent>()
    val toastEvent = _toastEvent.asSharedFlow()

    init {
        getNowPlayingMovies()
    }

    fun onEvent(event: UIEvent) {
        when (event) {
            is UIEvent.OnMovieClick -> {
                viewModelScope.launch {
                    _toastEvent.emit(ToastEvent.ShowToast(event.title))
                }
            }
        }
    }

    private fun getNowPlayingMovies() {
        viewModelScope.launch {
            _uiState.emit(UIState.Loading)
            when(val result = nowPlayingMovieUseCases()) {
                is Resource.Success -> {
                    _uiState.emit(UIState.Success(result.data))
                }
                is Resource.Error -> {
                    _errorState.emit(ErrorState.Error(result.error))
                }
            }
        }
    }

    sealed class NavigationEvent {
        data class NavigateToDetail(val movie: Movie) : NavigationEvent()
    }

    sealed class ToastEvent {
        data class ShowToast(val message: String) : ToastEvent()
    }

    sealed class UIEvent {
        data class OnMovieClick(val title: String) : UIEvent()
    }

    sealed class ErrorState {
        data class Error(val message: String) : ErrorState()
    }

    sealed class UIState {
        object Idle : UIState()
        object Loading : UIState()
        object Empty : UIState()
        data class Success(
            val data: List<Movie>,
            val isShowDialog: Boolean = false
        ) : UIState()
    }

}