package com.example.movies.domain.repository

import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieDetail

interface MoviesRepository {
    fun getMovies(): List<Movie>
    fun getMovieDetail(id: Long): MovieDetail
}