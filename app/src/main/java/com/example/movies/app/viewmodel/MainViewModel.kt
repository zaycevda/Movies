package com.example.movies.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.usecase.AddMoviesUseCase
import com.example.movies.domain.usecase.GetMoviesFromDbUseCase
import com.example.movies.domain.usecase.GetMoviesUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    private val addMoviesUseCase: AddMoviesUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
    private val getMoviesFromDbUseCase: GetMoviesFromDbUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(value = true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            if (getMoviesFromDbUseCase.execute().isEmpty()) {
                val movies = try {
                    getMoviesUseCase.execute()
                } catch (e: Exception) {
                    listOf()
                }
                addMoviesUseCase.execute(movies = movies)
            }
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