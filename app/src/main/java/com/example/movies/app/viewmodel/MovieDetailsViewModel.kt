package com.example.movies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.app.ui.util.ScreenState
import com.example.movies.app.ui.util.ScreenState.ErrorScreenState
import com.example.movies.app.ui.util.ScreenState.LoadingScreenState
import com.example.movies.app.ui.util.ScreenState.SuccessScreenState
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.usecase.GetMovieDetailUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<ScreenState<MovieDetail>>(LoadingScreenState())
    val movieDetail = _movieDetail.asStateFlow()

    fun getMovieDetail(id: Long) {
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _movieDetail.value = ErrorScreenState(throwable = throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            _movieDetail.value = LoadingScreenState()
            val movieDetail = getMovieDetailUseCase.execute(id = id)
            _movieDetail.value = SuccessScreenState(data = movieDetail)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): MovieDetailsViewModel
    }
}