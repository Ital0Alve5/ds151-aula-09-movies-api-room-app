package com.example.moviesapp.utils

import com.example.moviesapp.model.Movie
import com.example.moviesapp.data.local.FavoriteMovieEntity

fun Movie.toFavoriteEntity() = FavoriteMovieEntity(
    id, title, cast, director, synopsis, posterUrl
)

fun FavoriteMovieEntity.toMovie() = Movie(
    id, title, cast, director, synopsis, posterUrl
)

fun Movie.toEntity(): FavoriteMovieEntity = FavoriteMovieEntity(
    id,
    title,
    cast,
    director,
    synopsis,
    posterUrl
)
