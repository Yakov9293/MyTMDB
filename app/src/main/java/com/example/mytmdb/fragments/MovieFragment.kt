package com.example.mytmdb.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytmdb.fragments.MovieFragmentArgs
import com.example.mytmdb.R
import com.example.mytmdb.data.MovieListAdapter
import com.example.mytmdb.databinding.FragmentMovieBinding
import com.example.mytmdb.databinding.FragmentMoviesBinding
import com.example.mytmdb.viewmodel.DetailMovieViewModel
import com.example.mytmdb.viewmodel.DetailMovieViewModelFactory
import com.example.mytmdb.viewmodel.MovieListViewModel

class MovieFragment : Fragment() {
    val args: MovieFragmentArgs by navArgs()
    private val detailMovieViewModel: DetailMovieViewModel by viewModels{
        DetailMovieViewModelFactory(args.movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMovieBinding>(
            inflater, R.layout.fragment_movie, container, false
        ).apply {
            viewModel = detailMovieViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    companion object {
        private val TAG = MovieFragment::class.java.simpleName
    }
}
