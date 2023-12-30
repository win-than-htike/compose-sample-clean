package com.winthan.domain.models

data class Movie(
    val backdropPath: String,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String
)
