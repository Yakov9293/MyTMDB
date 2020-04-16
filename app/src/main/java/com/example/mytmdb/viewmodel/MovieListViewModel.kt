package com.example.mytmdb.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytmdb.data.MovieService
import com.example.mytmdb.data.SimplifiedMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieListViewModel : ViewModel() {

    init {
        loadPopularMovies()
    }

    private val _popularMovies = MutableLiveData<List<SimplifiedMovie>>()
    val popularMovies: LiveData<List<SimplifiedMovie>> = _popularMovies

    private fun loadPopularMovies() {
        this.viewModelScope.launch(Dispatchers.Main) {
            val popularMoviesRequest = MovieService.tmdbApi.getPopular()
            try {
                val response = popularMoviesRequest.await()
                if (response.isSuccessful) {
                    response.body()?.results?.let { _popularMovies.value = it }
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