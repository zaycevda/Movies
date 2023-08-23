package com.example.movies.domain.repository

import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieDetail

interface MoviesRepository {
    suspend fun getMovies(keyword: String): List<Movie>
    suspend fun getMovieDetail(id: Long): MovieDetail
}