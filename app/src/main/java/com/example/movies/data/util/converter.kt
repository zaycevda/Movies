package com.example.movies.data.util

import com.example.movies.data.db.entity.MovieEntity
import com.example.movies.data.net.model.MovieDetailModel
import com.example.movies.data.net.model.MovieModel
import com.example.movies.data.net.model.StaffModel
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.model.Actor

fun MovieModel.toMovie() =
    Movie(
        id = id,
        preview = preview,
        title = title,
        rating = rating
    )

fun MovieDetailModel.toMovieDetail(
    actors: List<Actor>,
    video: String?
) =
    MovieDetail(
        id = id,
        poster = poster,
        title = title,
        rating = rating,
        description = description,
        premiere = premiere,
        actors = actors,
        video = video
    )

fun StaffModel.toStaff() =
    Actor(
        id = id,
        image = image,
        name = name,
        character = character
    )

fun Movie.toMovieEntity() =
    MovieEntity(
        id = id,
        preview = preview,
        title = title,
        rating = rating
    )

fun MovieEntity.toMovie() =
    Movie(
        id = id,
        preview = preview,
        title = title,
        rating = rating
    )