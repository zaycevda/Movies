package com.example.movies.data.db.repository

import com.example.movies.data.db.db.RoomDb
import com.example.movies.data.util.toMovie
import com.example.movies.data.util.toMovieEntity
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MoviesRepositoryDb

class MoviesRepositoryDbImpl(db: RoomDb) : MoviesRepositoryDb {

    private val dao = db.movieDao()

    override suspend fun getMoviesFromDb(): List<Movie> {
        val movieEntities = dao.getMovies()
        return movieEntities.map { movieEntity -> movieEntity.toMovie() }
    }

    override suspend fun addMovies(movies: List<Movie>) {
        val movieEntities = movies.map { movie -> movie.toMovieEntity() }
        dao.addMovies(movieEntities)
    }
}