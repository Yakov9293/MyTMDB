package com.example.mytmdb.api

import com.example.mytmdb.data.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface TMDBApi {

    @GET("movie/popular?language=ru-RU")
    fun getPopular(): Deferred<Response<MoviesResponse>>

}