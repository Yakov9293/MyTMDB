package com.example.mytmdb.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytmdb.data.MovieService
import com.example.mytmdb.data.MovieListAdapter
import com.example.mytmdb.data.SimplifiedMovie
import com.example.mytmdb.databinding.FragmentMoviesBinding
import com.example.mytmdb.viewmodel.MovieListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {
    private val adapter = MovieListAdapter()
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            binding.moviesList.layoutManager = LinearLayoutManager(activity)
        } else {
            binding.moviesList.layoutManager = GridLayoutManager(activity, 2)
        }

        binding.moviesList.adapter = adapter
        viewModel.getPopularMovies()
            .observe(viewLifecycleOwner, Observer<List<SimplifiedMovie>> { movies ->
                adapter.submitList(movies)
            })

        return binding.root
    }

    private fun getPopularMoviesToAdapter() {
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
        private val TAG = MoviesFragment::class.java.simpleName
    }

}
