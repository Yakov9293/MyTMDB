package com.example.mytmdb.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mytmdb.data.SimplifiedMovie
import kotlinx.coroutines.CoroutineScope

class MoviesDataSourceFactory(private val scope: CoroutineScope, private var query: String = "") :
    DataSource.Factory<Int, SimplifiedMovie>() {

    val sourceLiveData = MutableLiveData<MoviesDataSource>()

    override fun create(): DataSource<Int, SimplifiedMovie> {
        val source = MoviesDataSource(scope, query)
        sourceLiveData.postValue(source)
        return source
    }

    fun updateQuery(query: String = "") {
        if (this.query == query) return
        this.query = query
        sourceLiveData.value?.invalidate()
    }
}