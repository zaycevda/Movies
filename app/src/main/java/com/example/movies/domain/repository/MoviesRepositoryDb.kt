package com.example.movies.domain.repository

import com.example.movies.domain.model.Movie

interface MoviesRepositoryDb {
    suspend fun addMovies(movies: List<Movie>)
    suspend fun getMoviesFromDb(): List<Movie>
}