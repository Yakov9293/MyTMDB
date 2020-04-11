package com.example.mytmdb.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytmdb.R
import com.example.mytmdb.data.MovieService
import com.example.mytmdb.data.MovieListAdapter
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerMovies = (movies_list as RecyclerView)
        recyclerMovies.layoutManager = LinearLayoutManager(activity)


        GlobalScope.launch(Dispatchers.Main) {
            val popularMoviesRequest = MovieService.tmdbApi.getPopular()
            try {
                val response = popularMoviesRequest.await()
                if (response.isSuccessful) {
                    val popularMovies = response.body()?.results
                    popularMovies?.let { recyclerMovies.adapter = MovieListAdapter(it) }
                } else {
                    Log.d("MainActivity ", response.errorBody().toString())
                }
            } catch (e: Exception) {
                Log.d("MainActivity ", e.toString())
            }
        }

    }
}
