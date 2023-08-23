package com.example.movies.data.net.util

import com.example.movies.data.net.model.MovieDetailModel
import com.example.movies.data.net.model.MovieModel
import com.example.movies.data.net.model.StaffModel
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieDetail
import com.example.movies.domain.model.Staff

fun MovieModel.toMovie() =
    Movie(
        id = id,
        preview = preview,
        title = title,
        rating = rating
    )

fun MovieDetailModel.toMovieDetail(
    staff: List<Staff>,
    video: String?
) =
    MovieDetail(
        id = id,
        poster = poster,
        title = title,
        rating = rating,
        description = description,
        premiere = premiere,
        staff = staff,
        video = video
    )

fun StaffModel.toStaff() =
    Staff(
        id = id,
        image = image,
        name = name,
        character = character,
        profession = profession
    )