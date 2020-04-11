package com.example.mytmdb.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytmdb.data.MovieService
import com.example.mytmdb.data.SimplifiedMovie
import com.example.mytmdb.fragments.MoviesFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {

    init {
        loadPopularMovies()
    }

    private val popularMovies = MutableLiveData<List<SimplifiedMovie>>()

    fun getPopularMovies():LiveData<List<SimplifiedMovie>> {
        return popularMovies
    }

    private fun loadPopularMovies() {
        GlobalScope.launch(Dispatchers.Main) {
            val popularMoviesRequest = MovieService.tmdbApi.getPopular()
            try {
                val response = popularMoviesRequest.await()
                if (response.isSuccessful) {
                    response.body()?.results?.let { popularMovies.value = it }
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    companion object {
        private val TAG = MovieListViewModel::class.java.simpleName
    }

}