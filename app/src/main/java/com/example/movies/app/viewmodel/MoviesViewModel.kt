package com.example.movies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.app.ui.ScreenState
import com.example.movies.app.ui.ScreenState.ErrorScreenState
import com.example.movies.app.ui.ScreenState.LoadingScreenState
import com.example.movies.app.ui.ScreenState.SuccessScreenState
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.GetMoviesUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel @AssistedInject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<ScreenState<List<Movie>>>(LoadingScreenState())
    val movies = _movies.asStateFlow()

    fun getMovies(keyword: String = EMPTY) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _movies.value = ErrorScreenState(throwable = throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            _movies.value = LoadingScreenState()
            val movies = getMoviesUseCase.execute(keyword = keyword)
            _movies.value = SuccessScreenState(data = movies)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): MoviesViewModel
    }

    private companion object {
        private const val EMPTY = ""
    }
}