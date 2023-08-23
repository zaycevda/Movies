package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository

class GetMoviesUseCase(private val repository: MoviesRepository) {
    suspend fun execute() = repository.getMovies()
}