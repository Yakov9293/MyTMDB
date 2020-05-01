package com.example.mytmdb.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mytmdb.data.MovieService
import com.example.mytmdb.data.SimplifiedMovie
import com.example.mytmdb.model.NetworkState
import kotlinx.coroutines.*

class PageKeyedMoviesDataSource(private val scope: CoroutineScope, private var query: String = "") :
    PageKeyedDataSource<Int, SimplifiedMovie>() {
    private val supervisorJob = SupervisorJob()
    private val networkState = MutableLiveData(NetworkState.LOADING)

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SimplifiedMovie>
    ) {
        executeQuery(1) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SimplifiedMovie>) {
        val page = params.key
        executeQuery(page) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, SimplifiedMovie>
    ) {
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()
    }

    private fun executeQuery(page: Int, callback: (List<SimplifiedMovie>) -> Unit) {
        networkState.postValue(NetworkState.LOADING)
        val movieRequest =
            if (query.isEmpty())
                MovieService.tmdbApi.getTopRating(page)
            else
                MovieService.tmdbApi.getMovies(page, query)

        scope.launch(getJobErrorHandler() + supervisorJob) {
            val response = movieRequest.await()
            if (response.isSuccessful) {
                networkState.postValue(NetworkState.LOADED)
                response.body()?.results?.let { callback(it) }
            } else {
                networkState.postValue(NetworkState.FAILED)
                Log.d(TAG, response.errorBody().toString())
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { coroutineContext, throwable ->
        networkState.postValue(NetworkState.FAILED)
        Log.d(TAG, throwable.toString())
    }

    fun getLiveNetworkState(): LiveData<NetworkState> = networkState

    companion object {
        private val TAG = PageKeyedMoviesDataSource::class.java.simpleName
    }
}