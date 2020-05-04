package com.example.mytmdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.mytmdb.R
import com.example.mytmdb.databinding.FragmentMovieBinding
import com.example.mytmdb.viewmodel.DetailMovieViewModel
import com.example.mytmdb.viewmodel.DetailMovieViewModelFactory

class MovieFragment : Fragment() {
    val args: MovieFragmentArgs by navArgs()
    private val detailMovieViewModel: DetailMovieViewModel by viewModels {
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
            setRetryCallback { detailMovieViewModel.loadDetailMovieRequest() }
        }
        return binding.root
    }

    companion object {
        private val TAG = MovieFragment::class.java.simpleName
    }
}
