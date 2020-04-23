package com.example.mytmdb.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mytmdb.data.SimplifiedMovie
import kotlinx.coroutines.CoroutineScope

class PageKeyedMoviesDataSourceFactory(private val scope: CoroutineScope) :
    DataSource.Factory<Int, SimplifiedMovie>() {
    override fun create(): DataSource<Int, SimplifiedMovie> {
        val source = PageKeyedMoviesDataSource(scope)
        return source
    }
}