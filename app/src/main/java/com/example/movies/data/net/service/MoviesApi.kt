package com.example.movies.data.net.service

import com.example.movies.BuildConfig
import com.example.movies.data.net.model.MovieDetailModel
import com.example.movies.data.net.model.MoviesResponse
import com.example.movies.data.net.model.StaffModel
import com.example.movies.data.net.model.VideoResponse
import com.example.movies.data.util.Order
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @Headers(
        "$ACCEPT: $JSON",
        "$X_API_KEY: $API_KEY"
    )
    @GET(MOVIES)
    suspend fun getMovies(
        @Query(ORDER) order: String = Order.NUM_VOTE.name,
        @Query(KEYWORD) keyword: String = EMPTY
    ): MoviesResponse

    @Headers(
        "$ACCEPT: $JSON",
        "$X_API_KEY: $API_KEY"
    )
    @GET(MOVIE_DETAIL)
    suspend fun getMovieDetail(@Path(ID) id: Long): MovieDetailModel

    @Headers(
        "$ACCEPT: $JSON",
        "$X_API_KEY: $API_KEY"
    )
    @GET(STAFF)
    suspend fun getStaff(@Query(FILM_ID) id: Long): List<StaffModel>

    @Headers(
        "$ACCEPT: $JSON",
        "$X_API_KEY: $API_KEY"
    )
    @GET(VIDEOS)
    suspend fun getVideos(@Path(ID) id: Long): VideoResponse

    private companion object {
        private const val ACCEPT = "accept"
        private const val API_KEY = "34b148dc-d82d-4c75-9c96-56f6cdcc9418"
        private const val EMPTY = ""
        private const val FILM_ID = "filmId"
        private const val ID = "id"
        private const val JSON = "application/json"
        private const val KEYWORD = "keyword"
        private const val MOVIES = "/api/v2.2/films"
        private const val MOVIE_DETAIL = "/api/v2.2/films/{id}"
        private const val ORDER = "order"
        private const val STAFF = "/api/v1/staff"
        private const val VIDEOS = "/api/v2.2/films/{id}/videos"
        private const val X_API_KEY = "X-API-KEY"
    }
}