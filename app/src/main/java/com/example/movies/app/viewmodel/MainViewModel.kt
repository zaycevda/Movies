package com.example.movies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.usecase.AddMoviesUseCase
import com.example.movies.domain.usecase.GetMoviesUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val addMoviesUseCase: AddMoviesUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(value = true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            val movies = getMoviesUseCase.execute()
            addMoviesUseCase.execute(movies)
            delay(timeMillis = DELAY)
            _isLoading.value = false
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): MainViewModel
    }

    private companion object {
        private const val DELAY = 2000L // 2 seconds
    }
}