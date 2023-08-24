package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepositoryDb

class GetMoviesFromDbUseCase (private val repository: MoviesRepositoryDb) {
    suspend fun execute() = repository.getMoviesFromDb()
}