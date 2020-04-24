package com.example.mytmdb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import androidx.paging.toLiveData
import com.example.mytmdb.repository.PageKeyedMoviesDataSourceFactory

class MovieListViewModel : ViewModel() {

    private val moviesDataSourceFactory = PageKeyedMoviesDataSourceFactory(this.viewModelScope)

    val popularMovies = moviesDataSourceFactory.toLiveData(Config(30))

    fun search(query: String = "") = moviesDataSourceFactory.updateQuery(query)

    companion object {
        private val TAG = MovieListViewModel::class.java.simpleName
    }

}