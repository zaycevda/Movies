package com.example.movies.data.net.repository

import com.example.movies.data.net.service.MoviesApi
import com.example.movies.data.net.util.toMovie
import com.example.movies.data.net.util.toMovieDetail
import com.example.movies.data.net.util.toStaff
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.repository.MoviesRepository

class MoviesRepositoryImpl(private val api: MoviesApi) : MoviesRepository {
    override suspend fun getMovies(): List<Movie> {
        val moviesResponse = api.getMovies()
        val movieModels = moviesResponse.movies
        return movieModels.map { movieModel -> movieModel.toMovie() }
    }

    override suspend fun getMovieDetail(id: Long): MovieDetail {
        val movieDetailModel = api.getMovieDetail(id = id)
        val staffModels = api.getStaff(id = id)
        val videosResponse = api.getVideos(id = id)

        val staff = staffModels.map { staffModel -> staffModel.toStaff() }

        val video =
            if (videosResponse.count != 0) {
                videosResponse.videos.find { it.site == YOUTUBE }?.url ?:
                videosResponse.videos.find { it.site == KINOPOISK_WIDGET }?.url
            } else null

        return movieDetailModel.toMovieDetail(
            staff = staff,
            video = video
        )
    }

    private companion object {
        private const val YOUTUBE = "YOUTUBE"
        private const val KINOPOISK_WIDGET = "KINOPOISK_WIDGET"
    }
}