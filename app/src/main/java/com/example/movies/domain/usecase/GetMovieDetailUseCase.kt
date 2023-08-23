package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository

class GetMovieDetailUseCase(private val repository: MoviesRepository) {
    suspend fun execute(id: Long) = repository.getMovieDetail(id = id)
}