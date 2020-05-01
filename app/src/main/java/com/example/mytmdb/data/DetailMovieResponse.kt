package com.example.mytmdb.data

data class DetailMovieResponse(
    val backdrop_path: String,
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val tagline: String,
    val title: String,
    val vote_average: Double
)