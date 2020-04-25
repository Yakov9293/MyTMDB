package com.example.mytmdb.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytmdb.data.DetailMovieResponse
import com.example.mytmdb.data.MovieService
import com.example.mytmdb.model.NetworkState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailMovieViewModel(private val id_movie: Int) : ViewModel() {

    private val _detailMovie = MutableLiveData<DetailMovieResponse>()
    val detailMovie: LiveData<DetailMovieResponse> = _detailMovie
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> = _networkState

    init {
        _networkState.value = NetworkState.LOADING
        loadDetailMovieRequest()
    }



    private fun loadDetailMovieRequest() {
        this.viewModelScope.launch(Dispatchers.Main) {
            val detailMovieRequest = MovieService.tmdbApi.getMovieDetail(id_movie)
            try {
                val response = detailMovieRequest.await()
                _networkState.value = NetworkState.LOADED
                if (response.isSuccessful) {
                    response.body()?.let { _detailMovie.value = it }
                } else {
                    Log.d(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }
        }
    }

    companion object {
        private val TAG = DetailMovieViewModel::class.java.simpleName
    }

}