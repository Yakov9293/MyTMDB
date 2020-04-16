package com.example.mytmdb.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytmdb.data.MovieListAdapter
import com.example.mytmdb.databinding.FragmentMoviesBinding
import com.example.mytmdb.viewmodel.MovieListViewModel

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

        viewModel.popularMovies
            .observe(viewLifecycleOwner, Observer { movies ->
                listAdapter.submitList(movies)
            })

        return binding.root
    }

    companion object {
        private val TAG = MoviesFragment::class.java.simpleName
    }

}
