package com.example.mytmdb.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.mytmdb.data.MovieService
import com.example.mytmdb.data.SimplifiedMovie
import com.example.mytmdb.model.NetworkState
import kotlinx.coroutines.*

class MoviesDataSource(private val scope: CoroutineScope, private var query: String = "") :
    PageKeyedDataSource<Int, SimplifiedMovie>() {
    private val supervisorJob = SupervisorJob()

    val networkState = MutableLiveData<NetworkState>()
    val initialNetworkState = MutableLiveData<NetworkState>()

    private var retry: (() -> Any)? = null


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, SimplifiedMovie>
    ) {
        retry = { loadInitial(params, callback) }
        initialNetworkState.postValue(NetworkState.LOADING)
        executeQuery(1, initialNetworkState) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SimplifiedMovie>) {
        retry = { loadAfter(params, callback) }
        val page = params.key
        executeQuery(page, networkState) {
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

    private fun executeQuery(
        page: Int,
        netwotkStateHandler: MutableLiveData<NetworkState>,
        callback: (List<SimplifiedMovie>) -> Unit
    ) {

        changeNetworkState(netwotkStateHandler, NetworkState.LOADING)

        val movieRequest =
            if (query.isEmpty())
                MovieService.tmdbApi.getTopRating(page)
            else
                MovieService.tmdbApi.getMovies(page, query)

        scope.launch(getJobErrorHandler(netwotkStateHandler) + supervisorJob) {
            val response = movieRequest.await()
            if (response.isSuccessful) {
                retry = null
                changeNetworkState(netwotkStateHandler, NetworkState.LOADED)
                response.body()?.results?.let { callback(it) }
            } else {
                changeNetworkState(netwotkStateHandler, NetworkState.FAILED)
                Log.d(TAG, response.errorBody().toString())
            }
        }
    }

    private fun getJobErrorHandler(netwotkStateHandler: MutableLiveData<NetworkState>) =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            changeNetworkState(netwotkStateHandler, NetworkState.FAILED)
            Log.d(TAG, throwable.toString())
        }

    private fun changeNetworkState(
        networkStateHandler: MutableLiveData<NetworkState>,
        state: NetworkState
    ) {
        if (networkStateHandler == initialNetworkState)
            networkState.postValue(null)
        networkStateHandler.postValue(state)
    }

    fun retryFailedQuery() {
        val prevQuery = retry
        retry = null
        prevQuery?.invoke()
    }

    companion object {
        private val TAG = MoviesDataSource::class.java.simpleName
    }
}