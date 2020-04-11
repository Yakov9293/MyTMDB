package com.example.mytmdb.util

import com.example.mytmdb.BuildConfig

object Constants{
    const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
    var tmdbApiKey = BuildConfig.TMDB_API_KEY
}