package com.example.movies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.app.ui.util.ScreenState
import com.example.movies.app.ui.util.ScreenState.ErrorScreenState
import com.example.movies.app.ui.util.ScreenState.LoadingScreenState
import com.example.movies.app.ui.util.ScreenState.SuccessScreenState
import com.example.movies.data.util.Order
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.GetMoviesFromDbUseCase
import com.example.movies.domain.usecase.GetMoviesUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel @AssistedInject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMoviesFromDbUseCase: GetMoviesFromDbUseCase
) : ViewModel() {

    private val _movies = MutableStateFlow<ScreenState<List<Movie>>>(LoadingScreenState())
    val movies = _movies.asStateFlow()

    fun getMovies(order: String = Order.NUM_VOTE.name, keyword: String = EMPTY) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _movies.value = ErrorScreenState(throwable = throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            _movies.value = LoadingScreenState()
            val movies = getMoviesUseCase.execute(order = order, keyword = keyword)
            _movies.value = SuccessScreenState(data = movies)
        }
    }

    fun getMoviesFromDb() {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _movies.value = ErrorScreenState(throwable = throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            _movies.value = LoadingScreenState()
            val movies = getMoviesFromDbUseCase.execute()
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