package com.example.movies.domain.usecase

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MoviesRepositoryDb

class AddMoviesUseCase(private val repository: MoviesRepositoryDb) {
    suspend fun execute(movies: List<Movie>) {
        repository.addMovies(movies = movies)
    }
}