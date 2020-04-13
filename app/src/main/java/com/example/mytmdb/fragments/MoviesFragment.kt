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
import com.example.mytmdb.databinding.FragmentMoviesBindingImpl
import com.example.mytmdb.viewmodel.MovieListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MoviesFragment : Fragment() {
    private val listAdapter = MovieListAdapter()
    private val viewModel: MovieListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        binding.moviesList.apply {
            layoutManager =
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    LinearLayoutManager(activity)
                } else {
                    GridLayoutManager(activity, 2)
                }
            adapter = listAdapter
        }

        viewModel.getPopularMovies()
            .observe(viewLifecycleOwner, Observer<List<SimplifiedMovie>> { movies ->
                listAdapter.submitList(movies)
            })

        return binding.root
    }

    companion object {
        private val TAG = MoviesFragment::class.java.simpleName
    }

}
