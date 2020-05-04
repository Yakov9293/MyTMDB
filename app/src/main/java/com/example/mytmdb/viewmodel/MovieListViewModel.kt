package com.example.mytmdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import androidx.paging.toLiveData
import com.example.mytmdb.model.NetworkState
import com.example.mytmdb.repository.MoviesDataSourceFactory

class MovieListViewModel : ViewModel() {

    private val moviesDataSourceFactory = MoviesDataSourceFactory(this.viewModelScope)

    val topRatingMovies = moviesDataSourceFactory.toLiveData(Config(30))

    val networkState: LiveData<NetworkState> =
        switchMap(moviesDataSourceFactory.sourceLiveData) { it.networkState }

    val initialNetworkState: LiveData<NetworkState> =
        switchMap(moviesDataSourceFactory.sourceLiveData) { it.initialNetworkState }

    fun retry() = moviesDataSourceFactory.sourceLiveData.value?.retryFailedQuery()

    fun search(query: String = "") = moviesDataSourceFactory.updateQuery(query)

    companion object {
        private val TAG = MovieListViewModel::class.java.simpleName
    }

}