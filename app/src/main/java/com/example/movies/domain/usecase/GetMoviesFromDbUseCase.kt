package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepositoryDb
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMoviesFromDbUseCase @Inject constructor(private val repository: MoviesRepositoryDb) {
    suspend fun execute() = repository.getMoviesFromDb()
}