package com.example.mytmdb.data

data class MoviesResponse(
    val page: Int,
    val results: List<SimplifiedMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class SimplifiedMovie(
    val backdrop_path: String,
    val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Double
)