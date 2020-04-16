package com.example.mytmdb.api

import com.example.mytmdb.data.DetailMovieResponse
import com.example.mytmdb.data.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBApi {

    @GET("movie/popular?language=ru-RU")
    fun getPopular(): Deferred<Response<MoviesResponse>>

    @GET("movie/{movie_id}?language=ru-RU")
    fun getMovieDetail(@Path("movie_id") movie_id:Int): Deferred<Response<DetailMovieResponse>>

}