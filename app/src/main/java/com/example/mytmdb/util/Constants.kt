package com.example.mytmdb.util

import com.example.mytmdb.BuildConfig

object Constants {
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    const val TMDB_BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    var tmdbApiKey = BuildConfig.TMDB_API_KEY
}