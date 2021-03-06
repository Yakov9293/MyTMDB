package com.example.mytmdb.api

import com.example.mytmdb.data.DetailMovieResponse
import com.example.mytmdb.data.MoviesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/top_rated?language=ru-RU&region=RU")
    fun getTopRating(@Query("page") page: Int = 1): Deferred<Response<MoviesResponse>>

    @GET("search/movie?language=ru-RU&region=RU&include_adult=true")
    fun getMovies(@Query("page") page: Int = 1, @Query("query") query: String): Deferred<Response<MoviesResponse>>

    @GET("movie/{movie_id}?language=ru-RU")
    fun getMovieDetail(@Path("movie_id") movie_id: Int): Deferred<Response<DetailMovieResponse>>

}