package com.example.mytmdb.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytmdb.R
import com.example.mytmdb.data.MovieService
import com.example.mytmdb.data.MovieListAdapter
import com.example.mytmdb.databinding.FragmentMoviesBinding
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {
    private val adapter = MovieListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.moviesList.layoutManager = LinearLayoutManager(activity)
        binding.moviesList.adapter = adapter


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPopularMovies()
    }

    private fun getPopularMovies() {
        GlobalScope.launch(Dispatchers.Main) {
            val popularMoviesRequest = MovieService.tmdbApi.getPopular()
            try {
                val response = popularMoviesRequest.await()
                if (response.isSuccessful) {
                    val popularMovies = response.body()?.results
                    popularMovies?.let { adapter.submitList(it) }
                } else {
                    Toast.makeText(activity, response.errorBody().toString(), Toast.LENGTH_LONG)
                        .show()
                    Log.d(TAG, response.errorBody().toString())
                }
            } catch (e: Exception) {
                Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
                Log.d(TAG, e.toString())
            }
        }
    }

    companion object {
        const val TAG = "MoviesFragment"
    }

}
